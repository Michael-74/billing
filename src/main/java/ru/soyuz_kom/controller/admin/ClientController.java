package ru.soyuz_kom.controller.admin;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

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
        Iterable<Client> clients = clientRepository.findAllByOrderByIdDesc();

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
    @CacheEvict(value="schedule", allEntries=true)
    public Iterable<Client> search(@RequestBody HashMap<String, Object> preset) {


        String string = "";

        for(Map.Entry<String, Object> entry : preset.entrySet()) {
            if(entry.getValue() == null || entry.getValue() == "") {
                continue;
            }

            switch (entry.getKey()) {
                case "price_over_month":
                    ArrayList data = (ArrayList) entry.getValue();
                    if(data.get(0) != null && data.get(0) != "") {
                        //string += entry.getKey() + ">" + data.get(0) + ";";
                    }
                    if(data.get(1) != null && data.get(1) != "") {
                        //string += entry.getKey() + "<" + data.get(1) + ";";
                    }
                    break;
                case "internet":
                case "tv":
                case "rent":
                    List arr = (List) entry.getValue();
                    if (arr.size() != 0) {
                        String listString = arr.toString();
                        String result = listString.substring(1, listString.length()-1);
                        //string += entry.getKey() + "=in=(" + result + ");";
                    }
                    break;
                case "fio":
                case "address":
                case "phone":
                case "email":
                case "login":
                case "contract":
                case "ip":
                    string += entry.getKey() + "==" + entry.getValue() + "*;";
                    break;
                default:
                    //string += entry.getKey() + "==" + entry.getValue() + "*;";
            }
        }
        Iterable<Client> clients;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            System.out.println("string search: " + newString);

            //string = preset.entrySet().stream().filter(item -> !item.equals("")).toString();

            Node rootNode = new RSQLParser().parse(newString);
            Specification<Client> spec = rootNode.accept(new CustomRsqlVisitor<Client>());

            clients = clientRepository.findAll(spec);
        } else {
            clients = clientRepository.findAllByOrderByIdDesc();
        }

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
