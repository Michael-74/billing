package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soyuz_kom.dto.*;
import ru.soyuz_kom.dto.mikrotik.ClientMikrotikUpdateDTO;
import ru.soyuz_kom.dto.smotreshka.ClientSmotreshkaUpdateDTO;
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
                    client.setMikrotikDatas(listMikrotikData);
                }
            }
        } catch(Exception ex) {
            System.out.println("error mikrotik addClient: " + ex);
        }

        try {
            if (client.getTvs().size() != 0 && client.getIsStatus()) {
                Set<SmotreshkaData> listSmotreshka = smotreshkaService.createSmotreshkaData(client);
                client.setSmotreshkaDatas(listSmotreshka);
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

        /**
         * Обновляем данные микротика
         */
        if(clientNew.getIsStatus() && (clientNew.getInternet() != null) && clientNew.getInternet().getIsStatus()) {

            if (!clientOld.get().getIp().equals(clientNew.getIp()) || !clientOld.get().getLogin().equals(clientNew.getLogin()) || !clientOld.get().getInternet().getId().equals(clientNew.getInternet().getId())) {

                // Проверяем чтобы записи были.
                // Есть такая ситуация когда абоненту включили статус и нужно создать записи в микротике
                if(clientOld.get().getMikrotikDatas().size() != 0) {
                    Set<ClientMikrotikUpdateDTO> clientMikrotiks = mikrotikService.buildMikrotikData(clientNew, clientOld.get().getMikrotikDatas());
                    clientNew.setMikrotikDatas(clientOld.get().getMikrotikDatas());
                    mikrotikService.updateAccount(clientMikrotiks);
                } else { // Создаем записи для абонента в микротике
                    Set<MikrotikData> mikrotikDatas = mikrotikService.createMikrotikData(clientNew);
                    clientNew.setMikrotikDatas(mikrotikDatas);
                }
            } else {
                clientNew.setMikrotikDatas(clientOld.get().getMikrotikDatas());
            }
        } else {
            // Удаляем все записи в микротике
            if(clientOld.get().getMikrotikDatas().size() != 0) {
                mikrotikService.deleteAccount(clientOld.get().getMikrotikDatas());
                clientNew.removeMikrotikDatas(clientNew.getMikrotikDatas());
            }
        }

        /**
         * Обновляем данные смотрешки
         */



        if(clientNew.getIsStatus() && (clientNew.getTvs().size() != 0)) {

            // проверям подписки
            List<Integer> newPurchases = new ArrayList();
            List<Integer> deletePurchases = new ArrayList();

            // Если есть старые подписки
            if(clientOld.get().getTvs().size() != 0) {

                // Ищем совпадение по старым подпискам и удаляем отмененные
                for(Tv tvOld: clientOld.get().getTvs()) {
                    for(Tv tvNew: clientNew.getTvs()) {
                        if(!tvOld.getSmotreshkaId().equals(tvNew.getSmotreshkaId())) { // на удаление
                            deletePurchases.add(tvOld.getSmotreshkaId());
                        }
                    }
                }

                // Ищем новые подписки
                for(Tv tvNew: clientNew.getTvs()) {
                    for(Tv tvOld: clientOld.get().getTvs()) {
                        if(!tvNew.getSmotreshkaId().equals(tvOld.getSmotreshkaId())) {
                            newPurchases.add(tvOld.getSmotreshkaId());
                        }
                    }
                }
            } else { //создаем новые подписки, т.к. старый список пуст
                for(Tv tvNew: clientNew.getTvs()) {
                    System.out.println("add new: " + tvNew.getSmotreshkaId());
                    newPurchases.add(tvNew.getSmotreshkaId());
                }
            }

            if(newPurchases.size() != 0) {
                System.out.println("newPurchases.size: " + newPurchases.size());
                smotreshkaService.setSubscriptions(clientOld.get().getSmotreshkaDatas(), newPurchases, true);
            }
            if(deletePurchases.size() != 0) {
                System.out.println("deletePurchases.size: " + deletePurchases.size());
                smotreshkaService.setSubscriptions(clientOld.get().getSmotreshkaDatas(), deletePurchases, false);
            }

            // Если логин или email были изменены
            // Т.к. мы не можем изменить Логин или email, то оставляем это дело на администратора
            // И в нашем случае мы просто создаем новое соединение
            if (!clientOld.get().getLogin().equals(clientNew.getLogin()) || !clientOld.get().getEmail().equals(clientNew.getEmail())) {

            }


        } else {
            if(clientOld.get().getSmotreshkaDatas().size() != 0) {
                smotreshkaService.deleteAllSubscriptionsOfAccount(clientOld.get().getSmotreshkaDatas());
            }
        }

        if(clientOld.get().getSmotreshkaDatas().size() != 0) {
            clientNew.setSmotreshkaDatas(clientOld.get().getSmotreshkaDatas());
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
