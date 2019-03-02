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
import java.util.*;


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

        /**
         * TODO добавить сервисы и как быть с абонентом если статус его false
         */

        try {
            smotreshkaService.load();
            if(clientCreated.getEmail() != null && !clientCreated.getEmail().equals("")) {
                List<Integer> smotreshkaIds = null;

                if(tvs.size() != 0) {
                    for(Tv tv: tvs) {

                        // Проверяем есть ли подписка и включенный статус тарифа
                        if(tv.getSmotreshkaId() != null && tv.getIsStatus()) {
                            smotreshkaIds.add(tv.getSmotreshkaId());
                        }
                    }
                }
                smotreshkaService.addAccount(clientCreated.getLogin(), clientCreated.getEmail(), null, smotreshkaIds);
            }
        } catch(Exception ex) {
            System.out.println("error smotreshka: " + ex);
        }

        try {
            mikrotikService.load();
            if (internet != null) {
                if (internet.getIsStatus()) {
                    Map<Integer, String> mikrotikIds = mikrotikService.addAccount(clientCreated.getIp(), internet.getSpeed(), clientCreated.getLogin());
                    Set<MikrotikData> listMikrotikData = new HashSet();
                    for(Map.Entry<Integer, String> mikrotikId: mikrotikIds.entrySet()) {

                        MikrotikData mikrotikData = new MikrotikData();
                        mikrotikData.setClientId(clientCreated.getId());
                        mikrotikData.setMikrotikId(mikrotikId.getValue());
                        mikrotikData.setMikrotikSettingId(mikrotikId.getKey());

                        listMikrotikData.add(mikrotikData);
                    }

                    try{
                        clientCreated.setMikrotikDatas(listMikrotikData);
                        clientRepository.save(clientCreated);
                    } catch (Exception e) {
                        System.out.println("Ex: " + e);
                    }

                }
            }

        } catch(Exception ex) {
            System.out.println("error mikrotik: " + ex);
        } finally {
            mikrotikService.deleteItems();
            System.out.println("finaly mikrotik");
        }

        return clientCreated;
    }
}
