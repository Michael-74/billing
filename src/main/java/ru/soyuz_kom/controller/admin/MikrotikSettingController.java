package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Mikrotik;
import ru.soyuz_kom.repository.MikrotikRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class MikrotikSettingController extends AdminController {

    @Autowired
    private MikrotikRepository mikrotikRepository;

    @GetMapping({"v1/mikrotik","v1/mikrotik/"})
    public Iterable<Mikrotik> index() {
        return mikrotikRepository.findAll();
    }

    @GetMapping({"v1/mikrotik/{id}"})
    public Optional<Mikrotik> show(@PathVariable Integer id) {
        return mikrotikRepository.findById(id);
    }

    @PostMapping(value = {"v1/mikrotik/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Mikrotik mikrotik, Errors errors) {
        System.out.println("v1/mikrotik/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Mikrotik addMikrotik = mikrotikRepository.save(mikrotik);
        return new ResponseEntity<>(addMikrotik, HttpStatus.OK);
    }

    @DeleteMapping({"v1/mikrotik/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete mikrotik " + id);
        mikrotikRepository.deleteById(id);

        return true;
    }
}
