package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soyuz_kom.dto.ClientDTO;
import ru.soyuz_kom.dto.InternetDTO;
import ru.soyuz_kom.dto.RentDTO;
import ru.soyuz_kom.dto.TvDTO;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.repository.TvRepository;
import ru.soyuz_kom.service.ClientService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InternetRepository internetRepository;

    @Autowired
    private TvRepository tvRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    SmotreshkaService smotreshkaService;

    @Autowired
    MikrotikService mikrotikService;

    @Override
    public Map getClientsAndListsAllServices() {

        //Iterable<ClientDTO> clients = clientRepository.findAllByOrderByIdDesc();

        ClientDTO clientDTO = new ClientDTO();
        InternetDTO internetDTO = new InternetDTO();
        TvDTO tvDTO = new TvDTO();
        RentDTO rentDTO = new RentDTO();

        List<ClientDTO> getAllClients = clientDTO.setClientDTOList(clientRepository.findAll());
        List<InternetDTO> getAllInternetDTOList = internetDTO.setIternetDTOList(internetRepository.findAll());
        List<TvDTO> getAllTvDTOList = tvDTO.setTvDTOList(tvRepository.findAll());
        List<RentDTO> getAllRentDTOList = rentDTO.setRentDTOList(rentRepository.findAll());

        Map<String, List> map = new HashMap<String, List>();

        map.put("clients", getAllClients);
        map.put("internets", getAllInternetDTOList);
        map.put("tvs", getAllTvDTOList);
        map.put("rents", getAllRentDTOList);

        System.out.println("Map: " + map);

        return map;
    }

    /**
     * Пополнение счета
     * @param client
     * @param cash
     * @return
     */
    @Transactional
    public Client addCash(Client client, BigDecimal cash) {

        cash = cash.add(client.getBalance());
        client.setBalance(cash);

        // Имитируем запрос websocket
        this.template.convertAndSend("/client/addCashClient", client);

        return clientRepository.save(client);
    }

    @Transactional
    public Client addClient(Client client) {
        Client clientCreated = clientRepository.save(client);

        Set<Tv> tvs = clientCreated.getTvs();
        Internet internet = clientCreated.getInternet();

        smotreshkaService.load();
        if(clientCreated.getEmail() != null) {
            List<Integer> smotreshkaIds = null;

            if(tvs.size() != 0) {
                for(Tv tv: tvs) {
                    if(tv.getSmotreshkaId() != null) {
                        smotreshkaIds.add(tv.getSmotreshkaId());
                    }
                }
            }
            smotreshkaService.addAccount(clientCreated.getLogin(), clientCreated.getEmail(), null, smotreshkaIds);
        }

        mikrotikService.load();
        if(internet != null) {
            mikrotikService.addAccount(clientCreated.getIp(), internet.getSpeed(), clientCreated.getLogin());
        }

        return clientCreated;
    }
}
