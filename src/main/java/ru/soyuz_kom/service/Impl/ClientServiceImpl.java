package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soyuz_kom.dto.*;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.*;
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

    /**
     * Производим добавление абонента
     * @param client
     * @return
     */
    @Transactional
    public Client addClient(Client client) {
        Client clientCreated = clientRepository.save(client);

        try {
            if (client.getInternet() != null && client.getInternet().getIsStatus() && client.getIsStatus()) {
                if (client.getInternet().getIsStatus()) {
                    Set<MikrotikData> listMikrotikData = mikrotikService.createMikrotikData(client);

                    try{
                        client.setMikrotikDatas(listMikrotikData);
                    } catch (Exception e) {
                        System.out.println("error addClient setMikrotikDatas: " + e);
                    }

                }
            }
        } catch(Exception ex) {
            System.out.println("error mikrotik addClient: " + ex);
        }

        try {
            if (client.getTvs().size() != 0 && client.getIsStatus()) {

                Set<SmotreshkaData> listSmotreshka = smotreshkaService.createSmotreshkaData(client);

                try{
                    client.setSmotreshkaDatas(listSmotreshka);
                } catch (Exception e) {
                    System.out.println("error addClient setSmotreshkaDatas: " + e);
                }
            }
        } catch(Exception ex) {
            System.out.println("error smotreshka: " + ex);
        }

        clientRepository.save(client);

        return clientCreated;
    }

    @Transactional
    public Client updateClient(Client clientNew) {
        Optional<Client> clientOld = clientRepository.findById(clientNew.getId());

        if(clientNew.getIsStatus() && (clientNew.getInternet() != null) && clientNew.getInternet().getIsStatus()) {

            // Сравниваем IP, тариф интернета и логин
            if (!clientOld.get().getIp().equals(clientNew.getIp()) || (clientOld.get().getInternet() != clientNew.getInternet()) || !clientOld.get().getLogin().equals(clientNew.getLogin())) {
                Set<ClientMikrotikUpdateDTO> clientMikrotiks;
                clientMikrotiks = mikrotikService.buildMikrotikData(clientNew, clientOld.get().getMikrotikDatas());

                // Проверяем чтобы записи были.
                // Есть такая ситуация когда абоненту включили статус и нужно создать записи в микротике
                if(clientOld.get().getMikrotikDatas().size() != 0) {
                    clientNew.setMikrotikDatas(clientOld.get().getMikrotikDatas());
                    mikrotikService.updateAccount(clientMikrotiks);
                } else { // Создаем записи для абонента в микротике
                    Set<MikrotikData> mikrotikDatas = mikrotikService.createMikrotikData(clientNew);
                    clientNew.setMikrotikDatas(mikrotikDatas);
                }
            }
        } else {

            // Удаляем все записи в микротике
            if(clientOld.get().getMikrotikDatas().size() != 0) {
                mikrotikService.deleteAccount(clientOld.get().getMikrotikDatas());
                clientNew.removeMikrotikDatas(clientNew.getMikrotikDatas());
            }
        }

        return clientRepository.save(clientNew);
    }

    @Transactional
    public boolean deleteClient(Integer clientId) {
        try {
            clientRepository.deleteById(clientId);
            System.out.println("deleteClient true");
            return true;
        } catch(Exception ex) {
            System.out.println("deleteClient error: " + ex);
            return false;
        }
    }

    /*
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
    */
}
