package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Mikrotik;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.helper.RestTemplateHelper;
import ru.soyuz_kom.provider.MikrotikProvider;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.MikrotikRepository;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MikrotikService {

    private Map<Integer, MikrotikProvider> items = new HashMap<Integer, MikrotikProvider>();

    @Autowired
    MikrotikRepository mikrotikRepository;

    public void load() {
        for (Mikrotik mikrotik : mikrotikRepository.findAll()) {
            MikrotikProvider mikrotikProvider = new MikrotikProvider();
            mikrotikProvider.connect(mikrotik.getHost(), mikrotik.getLogin(), mikrotik.getPassword());

            this.addItems(mikrotik.getId(), mikrotikProvider);
        }
        System.out.println("Load MikrotikProvider");
    }

    public void addItems(Integer id, MikrotikProvider item) {
        this.items.put(id, item);
    }

    public void deleteItems() {
        for(Map.Entry<Integer, MikrotikProvider> entry: this.items.entrySet()) {
            entry.getValue().disconect();
            this.items.remove(entry.getKey());
        }
    }

    public void getItems() {
        System.out.println("getItems: " + this.items);
    }

    public List<String> addAccount(String ip, String list, String comment) {
        List<String> accounts = new ArrayList<>();

        for(Map.Entry<Integer, MikrotikProvider> entry: this.items.entrySet()) {
            accounts.add(entry.getValue().create(ip, list, comment));
        }

        return accounts;
    }

    public List<Object> getAccounts() {
        List<Object> obj = new ArrayList<>();

        for(Map.Entry<Integer, MikrotikProvider> entry: this.items.entrySet()) {
            obj.add(entry.getValue().getAll());
        }
        return obj;
    }
}
