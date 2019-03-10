package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.smotreshka.*;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.helper.RestTemplateHelper;
import ru.soyuz_kom.provider.MikrotikProvider;
import ru.soyuz_kom.provider.ProviderSmotreshka;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.*;

@Service
public class SmotreshkaService {

    @Autowired
    RestTemplateHelper restTemplateHelper;

    @Autowired
    SmotreshkaRepository smotreshkaRepository;

    private Map<Integer, SmotreshkaProvider> smotreshkaProviders = new HashMap<Integer, SmotreshkaProvider>();

    public void load(Client client) {

        for(Smotreshka smotreshka: smotreshkaRepository.findAll()) {
            SmotreshkaProvider smotreshkaProvider = new SmotreshkaProvider(restTemplateHelper);
            smotreshkaProvider.init(client, smotreshka.getUrl(), smotreshka.getLogin(), smotreshka.getPassword());

            this.addItems(smotreshka.getId(), smotreshkaProvider);
        }
        System.out.println("Load SmotreshkaService");
    }

    public void addItems(Integer id, SmotreshkaProvider item) {
        this.smotreshkaProviders.put(id, item);
    }

    public void deleteItems() {
        for(Map.Entry<Integer, SmotreshkaProvider> entry: this.smotreshkaProviders.entrySet()) {
            this.smotreshkaProviders.remove(entry.getKey());
        }
    }

    public void getItems() {
        System.out.println("getItems: " + this.smotreshkaProviders);
    }

    public Map<Integer, String> addAccount(Client client, String username, String email, String password, List subscriptions) {
        this.load(client);
        Map<Integer, String> accounts = new HashMap<>();

        for(Map.Entry<Integer, SmotreshkaProvider> entry: this.smotreshkaProviders.entrySet()) {
            try {
                AccountNewResponseDTO smotreshkaNewDTO = entry.getValue().addAccount(username, email, password, subscriptions);
                if(smotreshkaNewDTO != null) {
                    accounts.put(entry.getKey(), smotreshkaNewDTO.getId());
                }
            } catch(Exception ex) {
                System.out.println("error addAccountSmotreshka: " + ex);
            }
        }
        this.deleteItems();

        return accounts;
    }

    public Set<SmotreshkaData> createSmotreshkaData(Client client) {
        List<Integer> subTv = new ArrayList<>();
        for(Tv tv: client.getTvs()) {
            subTv.add(tv.getSmotreshkaId());
        }

        Map<Integer, String> smotreshkaIds = this.addAccount(client, client.getLogin(), client.getEmail(), null, subTv);

        Set<SmotreshkaData> listSmotreshkaData = new HashSet();
        for(Map.Entry<Integer, String> mikrotikId: smotreshkaIds.entrySet()) {

            SmotreshkaData smotreshkaData = new SmotreshkaData();
            smotreshkaData.setClientId(client);
            smotreshkaData.setSmotreshkaId(mikrotikId.getValue());
            smotreshkaData.setSmotreshkaSettingId(mikrotikId.getKey());

            listSmotreshkaData.add(smotreshkaData);
        }

        return listSmotreshkaData;
    }

    public void deleteAccount(Client client, Set<SmotreshkaData> smotreshkaDatas) {
        this.load(client);
        for (SmotreshkaData smotreshkaData : smotreshkaDatas) {
            try {
                System.out.println("delete smotreshka deleteAccount");
                this.smotreshkaProviders.get(smotreshkaData.getSmotreshkaSettingId()).deleteAccountById(smotreshkaData.getSmotreshkaId());
            } catch (Exception ex) {
                System.out.println("error delete smotreshka: " + ex);
            }
        }
        this.deleteItems();
    }

    public void deleteAllSubscriptionsOfAccount(Client client, Set<SmotreshkaData> smotreshkaDatas) {
        this.load(client);
        for (SmotreshkaData smotreshkaData : smotreshkaDatas) {
            try {
                System.out.println("delete all sub smotreshka deleteAccount");
                this.smotreshkaProviders.get(smotreshkaData.getSmotreshkaSettingId()).deleteAllSubscriptionsOfAccount(smotreshkaData.getSmotreshkaId());
            } catch (Exception ex) {
                System.out.println("error delete all sub smotreshka: " + ex);
            }
        }
        this.deleteItems();
    }

    public void setSubscriptions(Client client, Set<SmotreshkaData> smotreshkaDatas, List<Integer> subscriptions, boolean isFlag) {
        this.load(client);
        System.out.println("setSubscriptions: ");
        for (SmotreshkaData smotreshkaData : smotreshkaDatas) {
            for (Integer subscription : subscriptions) {
                System.out.println("set purchases: " + subscription + " flag: " + isFlag);
                this.smotreshkaProviders.get(smotreshkaData.getSmotreshkaSettingId()).setSubscriptionOfAccount(smotreshkaData.getSmotreshkaId(), subscription, isFlag);
            }
        }
        this.deleteItems();
    }

    /*
    public Set<ClientSmotreshkaUpdateDTO> buildSmotreshkaData(Client client, Set<SmotreshkaData> smotreshkaDatas) {
        Set<ClientSmotreshkaUpdateDTO> clientSmotreshkas = new HashSet();

        for(SmotreshkaData smotreshkaData: smotreshkaDatas) {
            ClientSmotreshkaUpdateDTO clientSmotreshkaUpdateDTO = new ClientSmotreshkaUpdateDTO();
            clientSmotreshkaUpdateDTO.setSmotreshkaSettingId(smotreshkaData.getSmotreshkaSettingId());
            clientSmotreshkaUpdateDTO.setNumber(smotreshkaData.getMikrotikId());

            clientSmotreshkaUpdateDTO.setLogin(client.getIp());
            clientSmotreshkaUpdateDTO.setEmail(client.getInternet().getSpeed());
            clientSmotreshkaUpdateDTO.setPassword(client.getLogin());
            clientSmotreshkaUpdateDTO.setPurchases(client.get);

            clientSmotreshkas.add(clientSmotreshkaUpdateDTO);
        }

        return clientMikrotiks;
    }
    */
}
