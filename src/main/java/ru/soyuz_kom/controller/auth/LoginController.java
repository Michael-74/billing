package ru.soyuz_kom.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.UserRepository;


@RestController
@RequestMapping("/auth/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping({"v1/login","v1/login/"})
    @ResponseBody
    public Object index(@RequestBody Object obj) {
        //Iterable<User> users = userRepository.findAll();
        System.out.println(obj);

        return obj;
    }
}
