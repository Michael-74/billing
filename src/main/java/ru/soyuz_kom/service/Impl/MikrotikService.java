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
import java.util.List;

@Service
public class MikrotikService {

    @Autowired
    MikrotikRepository mikrotikRepository;

    private List<MikrotikProvider> items = new ArrayList();

    public void load() {

        List<Mikrotik> mikrotikRepositoryAll = mikrotikRepository.findAll();

        for(Mikrotik mikrotik: mikrotikRepositoryAll) {
            MikrotikProvider mikrotikProvider = new MikrotikProvider();
            mikrotikProvider.connect(mikrotik.getHost(), mikrotik.getLogin(), mikrotik.getPassword());

            this.addItems(mikrotikProvider);
        }
        System.out.println("Load MikrotikProvider");
    }

    public void disconect() {
        for(MikrotikProvider item: this.items) {
            item.disconect();
        }
    }

    public void addItems(MikrotikProvider item) {
        this.items.add(item);
    }

    public void deleteItems() {
        this.disconect();
        this.items = new ArrayList();
    }

    public void getItems() {
        System.out.println("getItems: " + this.items);
    }

    public List<Object> addAccount(String ip, String list, String comment) {
        List<Object> accounts = new ArrayList<>();

        for(MikrotikProvider item: this.items) {
            accounts.add(item.create(ip, list, comment));
        }

        return accounts;
    }

    public List<Object> getAccounts() {
        List<Object> obj = new ArrayList<>();

        for(MikrotikProvider item: this.items) {
            obj.add(item.getAll());
        }
        return obj;
    }
}
