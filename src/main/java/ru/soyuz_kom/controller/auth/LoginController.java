package ru.soyuz_kom.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.repository.UserRepository;


@RestController
@RequestMapping("/auth/")
public class LoginController {

    @PostMapping({"v1/login","v1/login/"})
    @ResponseBody
    public String index() {
        String test = "test";

        return test;
    }
}
