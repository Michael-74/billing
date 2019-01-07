package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.repository.TvRepository;
import ru.soyuz_kom.service.ClientService;

import java.util.HashMap;
import java.util.Map;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InternetRepository internetRepository;

    @Autowired
    private TvRepository tvRepository;

    @Autowired
    private RentRepository rentRepository;

    @Override
    public Map getClientsAndListsAllServices() {

        Iterable<Client> clients = clientRepository.findAllByOrderByIdDesc();
        Iterable<Internet> internets = internetRepository.findAll();
        Iterable<Tv> tvs = tvRepository.findAll();
        Iterable<Rent> rents = rentRepository.findAll();

        Map<String, Iterable> map = new HashMap<String, Iterable>();

        map.put("clients", clients);
        map.put("internets", internets);
        map.put("tvs", tvs);
        map.put("rents", rents);

        System.out.println("Map: " + map);

        return map;
    }
}
