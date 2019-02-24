package ru.soyuz_kom.controller.admin;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import ru.soyuz_kom.helper.CriteriaHelper;

import ru.soyuz_kom.provider.MikrotikProvider;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;
import ru.soyuz_kom.service.Impl.ClientServiceImpl;
import ru.soyuz_kom.service.Impl.SmotreshkaService;

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

    @Autowired
    MikrotikProvider mikrotikProvider;

    @Autowired
    SmotreshkaService smotreshkaService;


    @Autowired
    RabbitTemplate rabbitTemplate;

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
    @CacheEvict(value="schedule", allEntries=true)
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

        return new ResponseEntity<>(addClient, HttpStatus.OK);
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
    public Iterable<Client> search(@RequestBody HashMap<String, Object> preset) throws InterruptedException {

        for(int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("task", "Hello from RabbitMQ = " + i);
        }
        Thread.sleep(2000);
        for(int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("log", "log from RabbitMQ = " + i);
        }

        /* ------------------------------ */
        // Смотрешка

        List pur = new ArrayList();
        pur.add(102);

        //smotreshkaService.getItems();
        //List<Object> obj = smotreshkaService.getAccounts();
        //System.out.println("sys" + obj);
        //Object obj = smotreshkaService.addAccount("michael74", "michael74.ru@mail.ru", "123123", pur);
        //AccountListDTO ss = smt.getAccounts();

        //AccountDTO ss = smt.getAccountById("5bea68dc70c0ef0d0d0fc7b1");
        //AccountDTO ss = smt.setAccountInfo("5bea68dc70c0ef0d0d0fc7b1", null, null, "Барышников Станислав Владимирович", "1");
        //AccountPasswordStatusDTO ss = smt.setAccountPassword("5bea68dc70c0ef0d0d0fc7b1", "123");
        //AccountDeleteDTO ss = smt.deleteAccountById("5bea68dc70c0ef0d0d0fc7b1");
        //List<AccountSubscriptionsDTO> ss = smt.getSubscriptionsOfAccount("5bea743b70c0ef0d0d0fc7bd");
        //SubscriptionDTO ss = smt.setSubscriptionOfAccount("5bea743b70c0ef0d0d0fc7bd", "102", false);
        //AccountDeleteDTO ss = smt.deleteAllSubscriptionsOfAccount("5bea743b70c0ef0d0d0fc7bd");
        //System.out.println("s" +  ss);

        // Клиентский код
        //SmotreshkaService sm = new SmotreshkaService();
        //smotreshkaService.load();
        //smotreshkaService.sys();

        /* ------------------------------ */

        /* ------------------------------ */
        // Микротик
            /*
            mikrotikProvider.connect("62.192.60.157", "admin", "njgjh");

            if(mikrotikProvider.isConnect()){

                List<Map<String, String>> search = mikrotikProvider.search("address", "127.0.1.1");

                List<Map<String, String>> test = mikrotikProvider.create("127.0.1.1", "test10", "test10");
                System.out.println("exec: " + test);

                List<Map<String, String>> all = mikrotikProvider.getAll();
                System.out.println("all: " + all);
            }
            */



        /* ------------------------------ */

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

    @PostMapping(value = {"v1/client/{client}/add-cash"})
    @Transactional
    public ResponseEntity addCash(@PathVariable Client client, @RequestBody HashMap<String, Object> data) {

        try {
            String value = (String) data.get("cash");
            BigDecimal money = new BigDecimal(value.replaceAll(",", "."));
            Client addedCashClient = clientService.addCash(client, money);

            return new ResponseEntity<>(addedCashClient, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @MessageMapping("/deleteClient")
    @SendTo("/client/deleteClient")
    @Transactional
    public Integer delete(Integer clientId) {
        System.out.println("delete client " + clientId);
        clientRepository.deleteById(clientId);

        return clientId;
    }
}
