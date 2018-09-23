package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.UserRepository;
import ru.soyuz_kom.service.UserSeviceImpl;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"","/"})
    public String index(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        model.put("users", users);

        return "view/admin/user/index";
    }
}
