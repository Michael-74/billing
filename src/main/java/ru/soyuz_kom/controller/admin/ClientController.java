package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.UserRepository;

import java.util.Optional;

@RestController
public class ClientController extends AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"v1/client","v1/client/"})
    public Iterable<User> index() {
        //List<User> users = userRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        System.out.println(users);

        return users;
    }

    @GetMapping({"v1/client/{id}"})
    public Optional<User> show(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        return user;
    }

    @PostMapping({"v1/client/create"})
    @ResponseBody
    public Object store(@RequestBody Object obj) {
        System.out.println("CREATE: ");
        System.out.println(obj);
        return true;
    }
}
