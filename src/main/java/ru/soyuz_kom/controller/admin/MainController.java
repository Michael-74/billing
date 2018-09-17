package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.profiles.active}")
    String profile;

    @GetMapping({"","/"})
    public String index(Model model) {

        model.addAttribute("isDevMode", "dev".equals(profile));
        return "view/admin/main/index";
    }
}
