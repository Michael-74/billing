package ru.soyuz_kom.entity.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.MikrotikData;
import ru.soyuz_kom.service.Impl.ClientServiceImpl;
import ru.soyuz_kom.service.Impl.MikrotikService;
import ru.soyuz_kom.service.Impl.SmotreshkaService;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import java.util.Set;

@Component
public class ClientListener {


    private static MikrotikService mikrotikService;
    private static SmotreshkaService smotreshkaService;

    @Autowired
    public void setMyService (MikrotikService mikrotikService, SmotreshkaService smotreshkaService) {
        this.mikrotikService = mikrotikService;
        this.smotreshkaService = smotreshkaService;
    }

    /* TODO: перенес в clientService
    @PostRemove
    public void postRemove(Client client) {
        if(client.getMikrotikDatas().size() != 0) {
            mikrotikService.deleteAccount(client.getMikrotikDatas());
        }
        if(client.getSmotreshkaDatas().size() != 0) {
            smotreshkaService.deleteAccount(client.getSmotreshkaDatas());
        }
    }
    */
}
