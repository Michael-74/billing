package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Sms;
import ru.soyuz_kom.repository.SmsRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class SmsSettingController extends AdminController {

    @Autowired
    private SmsRepository smsRepository;

    @GetMapping({"v1/sms","v1/sms/"})
    public Iterable<Sms> index() {
        return smsRepository.findAll();
    }

    @GetMapping({"v1/sms/{id}"})
    public Optional<Sms> show(@PathVariable Integer id) {
        return smsRepository.findById(id);
    }

    @PostMapping(value = {"v1/sms/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Sms sms, Errors errors) {
        System.out.println("v1/sms/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Sms addSms = smsRepository.save(sms);
        return new ResponseEntity<>(addSms, HttpStatus.OK);
    }

    @DeleteMapping({"v1/sms/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete sms " + id);
        smsRepository.deleteById(id);

        return true;
    }
}
