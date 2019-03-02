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

        /**
         * TODO Как быть с абонентом если статус его false
         */

        try {
            this.addAccountMikrotik(clientCreated);
        } catch(Exception ex) {
            System.out.println("error mikrotik: " + ex);
        }

        try {
            //this.addAccountSmotreshka(clientCreated);
        } catch(Exception ex) {
            System.out.println("error smotreshka: " + ex);
        }

        return clientCreated;
    }

    @Transactional
    public boolean deleteClient(Integer clientId) {
        try {
            clientRepository.deleteById(clientId);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public void addAccountMikrotik(Client client) {
        Internet internet = client.getInternet();

        if (internet != null) {
            if (internet.getIsStatus()) {
                Map<Integer, String> mikrotikIds = mikrotikService.addAccount(client.getIp(), internet.getSpeed(), client.getLogin());
                Set<MikrotikData> listMikrotikData = new HashSet();
                for(Map.Entry<Integer, String> mikrotikId: mikrotikIds.entrySet()) {

                    MikrotikData mikrotikData = new MikrotikData();
                    mikrotikData.setClientId(client.getId());
                    mikrotikData.setMikrotikId(mikrotikId.getValue());
                    mikrotikData.setMikrotikSettingId(mikrotikId.getKey());

                    listMikrotikData.add(mikrotikData);
                }

                try{
                    client.setMikrotikDatas(listMikrotikData);
                    clientRepository.save(client);
                } catch (Exception e) {
                    System.out.println("Ex: " + e);
                }

            }
        }
    }

    public void addAccountSmotreshka(Client client) {

        Set<Tv> tvs = client.getTvs();
        smotreshkaService.load();
        if(client.getEmail() != null && !client.getEmail().equals("")) {
            List<Integer> smotreshkaIds = null;

            if(tvs.size() != 0) {
                for(Tv tv: tvs) {

                    // Проверяем есть ли подписка и включенный статус тарифа
                    if(tv.getSmotreshkaId() != null && tv.getIsStatus()) {
                        smotreshkaIds.add(tv.getSmotreshkaId());
                    }
                }
            }
            smotreshkaService.addAccount(client.getLogin(), client.getEmail(), null, smotreshkaIds);
        }
    }
}
