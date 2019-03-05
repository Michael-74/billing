package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.smotreshka.*;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.helper.RestTemplateHelper;
import ru.soyuz_kom.provider.MikrotikProvider;
import ru.soyuz_kom.provider.ProviderSmotreshka;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmotreshkaService {

    @Autowired
    RestTemplateHelper restTemplateHelper;

    @Autowired
    SmotreshkaRepository smotreshkaRepository;

    private Map<Integer, SmotreshkaProvider> smotreshkaProviders = new HashMap<Integer, SmotreshkaProvider>();

    public void load() {

        for(Smotreshka smotreshka: smotreshkaRepository.findAll()) {
            SmotreshkaProvider smotreshkaProvider = new SmotreshkaProvider(restTemplateHelper);
            smotreshkaProvider.init(smotreshka.getUrl(), smotreshka.getLogin(), smotreshka.getPassword());

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

    public Map<Integer, Object> addAccount(String username, String email, String password, List subscriptions) {
        this.load();
        Map<Integer, Object> accounts = new HashMap<>();

        for(Map.Entry<Integer, SmotreshkaProvider> entry: this.smotreshkaProviders.entrySet()) {
            Object smotreshkaId = null;
            try {
                smotreshkaId = entry.getValue().addAccount(username, email, password, subscriptions);
            } catch(Exception ex) {
                System.out.println("error addAccount: " + ex);
            }
            accounts.put(entry.getKey(), smotreshkaId);
        }
        this.deleteItems();

        return accounts;
    }
}
