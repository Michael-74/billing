package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"v1/user","v1/user/"})
    public Iterable<User> index() {
        //List<User> users = userRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        System.out.println(users);

        return users;
    }

    @GetMapping({"v1/user/{id}"})
    public Optional<User> show(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        return user;
    }
}
