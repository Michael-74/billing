package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.dto.ClientDTO;
import ru.soyuz_kom.dto.InternetDTO;
import ru.soyuz_kom.dto.RentDTO;
import ru.soyuz_kom.dto.TvDTO;
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
        TvDTO tvDTO = new TvDTO();
        RentDTO rentDTO = new RentDTO();

        List<ClientDTO> getAllClients = clientDTO.setClientDTOList(clientRepository.findAll());
        List<InternetDTO> getAllInternetDTOList = internetDTO.setIternetDTOList(internetRepository.findAll());
        List<TvDTO> getAllTvDTOList = tvDTO.setTvDTOList(tvRepository.findAll());
        List<RentDTO> getAllRentDTOList = rentDTO.setRentDTOList(rentRepository.findAll());

        Map<String, List> map = new HashMap<String, List>();

        map.put("clients", getAllClients);
        map.put("internets", getAllInternetDTOList);
        map.put("tvs", getAllTvDTOList);
        map.put("rents", getAllRentDTOList);

        System.out.println("Map: " + map);

        return map;
    }
}
