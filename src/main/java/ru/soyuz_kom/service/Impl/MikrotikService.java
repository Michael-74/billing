package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.ClientMikrotikUpdateDTO;
import ru.soyuz_kom.entity.Mikrotik;
import ru.soyuz_kom.entity.MikrotikData;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.helper.RestTemplateHelper;
import ru.soyuz_kom.provider.MikrotikProvider;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.MikrotikRepository;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.*;

@Service
public class MikrotikService {

    private Map<Integer, MikrotikProvider> mikrotikProviders = new HashMap<Integer, MikrotikProvider>();

    @Autowired
    private MikrotikRepository mikrotikRepository;

    public void load() {
        try {
            System.out.println("Load MikrotikProvider1");
            for (Mikrotik mikrotik : this.mikrotikRepository.findAll()) {
                MikrotikProvider mikrotikProvider = new MikrotikProvider();
                mikrotikProvider.connect(mikrotik.getHost(), mikrotik.getLogin(), mikrotik.getPassword());

                this.addItems(mikrotik.getId(), mikrotikProvider);
            }
            System.out.println("Load MikrotikProvider2");
        } catch (Exception ex) {
            System.out.println("Load not MikrotikProvider3: " + this.mikrotikRepository);
            throw new NullPointerException();
        }
    }

    public void addItems(Integer id, MikrotikProvider item) {
        this.mikrotikProviders.put(id, item);
    }

    public void deleteItems() {
        for(Map.Entry<Integer, MikrotikProvider> entry: this.mikrotikProviders.entrySet()) {
            entry.getValue().disconect();
            this.mikrotikProviders.remove(entry.getKey());
        }
    }

    public void getItems() {
        System.out.println("getItems: " + this.mikrotikProviders);
    }

    public Map<Integer, String> addAccount(String ip, String list, String comment) {
        this.load();
        Map<Integer, String> accounts = new HashMap<>();

        for(Map.Entry<Integer, MikrotikProvider> entry: this.mikrotikProviders.entrySet()) {
            String mikrotikId = null;
            try {
                mikrotikId = entry.getValue().create(ip, list, comment);
            } catch(Exception ex) {
                System.out.println("error addAccount: " + ex);
            }
            accounts.put(entry.getKey(), mikrotikId);
        }
        this.deleteItems();

        return accounts;
    }

    public void updateAccount(Set<ClientMikrotikUpdateDTO> clientMikrotikUpdateDTO) {
        this.load();

        for (ClientMikrotikUpdateDTO clientMikrotik : clientMikrotikUpdateDTO) {
            try {
                this.mikrotikProviders.get(clientMikrotik.getMikrotikSettingId()).update(clientMikrotik.getNumber(), clientMikrotik.getIp(), clientMikrotik.getList(), clientMikrotik.getComment());
            } catch (Exception ex) {
                System.out.println("error update: " + ex);
            }
        }
        this.deleteItems();
    }

    /**
     * Удаление клиента из микротика
     * @param mikrotikDatas
     */
    public void deleteAccount(Set<MikrotikData> mikrotikDatas) {
        this.load();
        for (MikrotikData mikrotikData : mikrotikDatas) {
            try {
                this.mikrotikProviders.get(mikrotikData.getMikrotikSettingId()).delete(mikrotikData.getMikrotikId());
            } catch (Exception ex) {
                System.out.println("error delete: " + ex);
            }
        }
        this.deleteItems();
    }

    public List<Object> getAccounts() {
        List<Object> obj = new ArrayList<>();

        for(Map.Entry<Integer, MikrotikProvider> entry: this.mikrotikProviders.entrySet()) {
            obj.add(entry.getValue().getAll());
        }
        return obj;
    }
}
