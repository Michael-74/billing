package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class SmotreshkaSettingController extends AdminController {

    @Autowired
    private SmotreshkaRepository smotreshkaRepository;

    @GetMapping({"v1/smotreshka","v1/smotreshka/"})
    public Iterable<Smotreshka> index() {
        Iterable<Smotreshka> smotreshkas = smotreshkaRepository.findAll();

        return smotreshkas;
    }

    @GetMapping({"v1/smotreshka/{id}"})
    public Optional<Smotreshka> show(@PathVariable Integer id) {
        Optional<Smotreshka> smotreshka = smotreshkaRepository.findById(id);

        return smotreshka;
    }

    @PostMapping(value = {"v1/smotreshka/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Smotreshka smotreshka, Errors errors) {
        System.out.println("v1/smotreshka/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Smotreshka addSmotreshka = smotreshkaRepository.save(smotreshka);
        return new ResponseEntity<>(addSmotreshka, HttpStatus.OK);
    }

    @DeleteMapping({"v1/smotreshka/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete smotreshka " + id);
        smotreshkaRepository.deleteById(id);

        return true;
    }
}
