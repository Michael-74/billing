package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.repository.UserRepository;
import ru.soyuz_kom.validation.ClientStore;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ClientController extends AdminController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"v1/client","v1/client/"})
    public Iterable<Client> index() {
        Iterable<Client> clients = clientRepository.findAll();

        return clients;
    }

    @GetMapping({"v1/client/{id}"})
    public Optional<Client> show(@PathVariable Integer id) {
        Optional<Client> client = clientRepository.findById(id);

        return client;
    }

    /*
    @PostMapping({"v1/client/create"})
    @ResponseBody
    public ResponseEntity store(@Validated @RequestBody Client client, Errors errors) {
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    */
    @PostMapping({"v1/client/create"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Client client, Errors errors) {
        System.out.println("v1/client/create");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Client addClient = clientRepository.save(client);

        // Имитируем запрос websocket
        this.template.convertAndSend("/client/changeClient", addClient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* TODO: заменили имитацией запроса выше. Отключено на время разбора валидации сокетов
    @MessageMapping("/changeClient")
    @SendTo("/client/changeClient")
    public Client change(Client client) {
        System.out.println("websocket change");
        return clientRepository.save(client);
    }
    */


    @PostMapping({"v1/client/search"})
    @ResponseBody
    public Iterable<Client> search(@RequestBody HashMap<String, Object> preset) {

        System.out.println("client search: " + preset.get("price_over_month"));

        Iterable<Client> clients = clientRepository.findAll();

        return clients;
    }

    @MessageMapping("/deleteClient")
    @SendTo("/client/deleteClient")
    public Integer delete(Integer clientId) {
        System.out.println("delete client " + clientId);
        clientRepository.deleteById(clientId);

        return clientId;
    }
}
