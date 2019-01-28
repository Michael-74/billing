package ru.soyuz_kom.service;

import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.User;

import java.math.BigDecimal;
import java.util.Map;

public interface ClientService {

    Map getClientsAndListsAllServices();

    Client addCash(Client client, BigDecimal cash);
}
