package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.ClientDTO;
import ru.soyuz_kom.dto.InternetDTO;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.repository.TvRepository;
import ru.soyuz_kom.service.ClientService;

import java.util.HashMap;
import java.util.List;
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

        //Iterable<ClientDTO> clients = clientRepository.findAllByOrderByIdDesc();

        ClientDTO clientDTO = new ClientDTO();
        InternetDTO internetDTO = new InternetDTO();
        List<ClientDTO> getAllClients = clientDTO.getAllDTOList(clientRepository.findAll());

        List<InternetDTO> getAllInternetDTOList = internetDTO.getAllInternetDTOList(internetRepository.findAll());

        //List<Internet> internets = internetRepository.findAll();
        //Iterable<Tv> tvs = tvRepository.findAll();
        //Iterable<Rent> rents = rentRepository.findAll();

        Map<String, List> map = new HashMap<String, List>();

        map.put("clients", getAllClients);
        map.put("internets", getAllInternetDTOList);
        //map.put("tvs", tvs);
        //map.put("rents", rents);

        System.out.println("Map: " + map);

        return map;
    }
}
