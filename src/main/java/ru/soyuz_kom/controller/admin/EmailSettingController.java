package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Email;
import ru.soyuz_kom.entity.Sms;
import ru.soyuz_kom.repository.EmailRepository;
import ru.soyuz_kom.repository.SmsRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class EmailSettingController extends AdminController {

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping({"v1/email","v1/email/"})
    public Iterable<Email> index() {
        return emailRepository.findAll();
    }

    @GetMapping({"v1/email/{id}"})
    public Optional<Email> show(@PathVariable Integer id) {
        return emailRepository.findById(id);
    }

    @PostMapping(value = {"v1/email/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Email email, Errors errors) {
        System.out.println("v1/email/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Email addEmail = emailRepository.save(email);
        return new ResponseEntity<>(addEmail, HttpStatus.OK);
    }

    @DeleteMapping({"v1/email/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete email " + id);
        emailRepository.deleteById(id);

        return true;
    }
}
