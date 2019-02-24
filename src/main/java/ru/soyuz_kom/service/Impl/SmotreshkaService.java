package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.smotreshka.*;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.helper.RestTemplateHelper;
import ru.soyuz_kom.provider.ProviderSmotreshka;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmotreshkaService {
    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Autowired
    SmotreshkaRepository smotreshkaRepository;

    private List<SmotreshkaProvider> items = new ArrayList();

    public void load() {

        List<Smotreshka> smRepo = smotreshkaRepository.findAll();

        for(Smotreshka smotreshka: smRepo) {
            SmotreshkaProvider smotreshkaProvider = new SmotreshkaProvider(restTemplateHelper);
            smotreshkaProvider.init(smotreshka.getUrl(), smotreshka.getLogin(), smotreshka.getPassword());

            this.addItems(smotreshkaProvider);
        }
        System.out.println("Load SmotreshkaService");
    }

    public void addItems(SmotreshkaProvider item) {
        this.items.add(item);
    }

    public void getItems() {
        System.out.println("getItems: " + this.items);
    }

    public List<Object> addAccount(String username, String email, String password, List purchases) {
        List<Object> obj = new ArrayList<>();

        for(SmotreshkaProvider item: this.items) {
            obj.add(item.addAccount(username, email, password, purchases));
        }
        return obj;
    }

    public List<Object> getAccounts() {
        List<Object> obj = new ArrayList<>();

        for(SmotreshkaProvider item: this.items) {
            obj.add(item.getAccounts());
        }
        return obj;
    }
}
