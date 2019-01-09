package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Package;
import ru.soyuz_kom.repository.PackageRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class PackageController extends AdminController {

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping({"v1/package/"})
    @ResponseBody
    public Iterable<Package> index() {
        Iterable<Package> pack = packageRepository.findAll();

        return pack;
    }

    @GetMapping({"v1/package/{id}"})
    public Optional<Package> show(@PathVariable Integer id) {
        Optional<Package> pack = packageRepository.findById(id);

        return pack;
    }

    @PostMapping({"v1/package/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Package pack, Errors errors) {
        System.out.println("v1/task/store: " + pack);
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Package Addpack = packageRepository.save(pack);

        return new ResponseEntity<Package>(Addpack, HttpStatus.OK);
    }

    @DeleteMapping({"v1/package/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete pack " + id);
        packageRepository.deleteById(id);

        return true;
    }
}
