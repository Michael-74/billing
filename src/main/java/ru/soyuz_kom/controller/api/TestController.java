package ru.soyuz_kom.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends ApiController {

    @GetMapping(value = {"v1/test","v1/test/"}, produces = "application/json")
    public String index() {
        return "test";
    }
}
