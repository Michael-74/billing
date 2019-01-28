package ru.soyuz_kom.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.view.Views;
import ru.soyuz_kom.helper.CriteriaHelper;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;
import ru.soyuz_kom.service.Impl.ClientServiceImpl;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class ClientController extends AdminController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImpl clientService;

    //@JsonView(Views.ClientsAndServicesIdName.class)
    @GetMapping(value = {"v1/client","v1/client/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map index() {
        return clientService.getClientsAndListsAllServices();
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
    @PostMapping(value = {"v1/client/create"})
    @Transactional
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


        String string = "";

        for(Map.Entry<String, Object> entry : preset.entrySet()) {
            if(entry.getValue() == null || entry.getValue() == "") {
                continue;
            }

            switch (entry.getKey()) {
                case "priceOverMonth":
                case "balance":
                case "discount":
                case "createdAt":
                    string += CriteriaHelper.parseAndBuildLessAndGreatThan(entry.getKey(), entry.getValue());
                    break;
                case "internet":
                case "tvs":
                case "rents":
                    string += CriteriaHelper.parseAndBuildIn(entry.getKey(), entry.getValue());
                    break;
                case "loyalty":
                case "typeDiscount":
                    string += CriteriaHelper.parseAndBuildEqual(entry.getKey(), entry.getValue());
                    break;
                case "isPromisedPay":
                case "isStatus":
                    string += CriteriaHelper.parseAndBuildEqualBool(entry.getKey(), entry.getValue());
                    break;
                case "fio":
                case "address":
                case "phone":
                case "email":
                case "login":
                case "contract":
                case "ip":
                    string += CriteriaHelper.parseAndBuildEqualMore(entry.getKey(), entry.getValue());
                    break;
            }
        }
        Iterable<Client> clients;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            Node rootNode = new RSQLParser().parse(newString);
            Specification<Client> spec = rootNode.accept(new CustomRsqlVisitor<Client>());
            clients = clientRepository.findAll(spec);

            return new HashSet<Client>((Collection<? extends Client>) clients);
        } else {
            return clientRepository.findAll();
        }
    }


    //@MessageMapping("/addCashClient")
    //@SendTo("/client/addCashClient")
    @PostMapping(value = {"v1/client/{client}/add-cash"})
    @Transactional
    public ResponseEntity addCash(@PathVariable Client client, @RequestBody HashMap<String, Object> data) {

        System.out.println("addCash client " + client);
        System.out.println("addCash cash " + data.get("cash"));
        String value = (String) data.get("cash");
        BigDecimal money = new BigDecimal(value.replaceAll(",", "."));
        clientService.addCash(client, money);

        // Имитируем запрос websocket
        this.template.convertAndSend("/client/addCashClient", money);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/deleteClient")
    @SendTo("/client/deleteClient")
    public Integer delete(Integer clientId) {
        System.out.println("delete client " + clientId);
        clientRepository.deleteById(clientId);

        return clientId;
    }
}
