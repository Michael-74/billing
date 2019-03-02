package ru.soyuz_kom.entity.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.MikrotikData;
import ru.soyuz_kom.service.Impl.MikrotikService;

import javax.persistence.PostRemove;
import java.util.Set;

@Component
public class ClientListener {


    private static MikrotikService mikrotikService;

    @Autowired
    public void setMyService (MikrotikService mikrotikService) {
        this.mikrotikService = mikrotikService;
    }

    @PostRemove
    public void postRemove(Client client) {
        if(client.getMikrotikDatas().size() != 0) {
            mikrotikService.deleteAccount(client.getMikrotikDatas());
        }
    }
}
