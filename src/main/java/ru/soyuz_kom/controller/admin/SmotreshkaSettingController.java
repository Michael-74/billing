package ru.soyuz_kom.controller.admin;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.helper.CriteriaHelper;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SmotreshkaSettingController extends AdminController {

    @Autowired
    private InternetRepository internetRepository;

    @GetMapping({"v1/smotreshka","v1/smotreshka/"})
    public Iterable<Internet> index() {
        Iterable<Internet> internets = internetRepository.findAll();

        return internets;
    }

    @GetMapping({"v1/smotreshka/{id}"})
    public Optional<Internet> show(@PathVariable Integer id) {
        Optional<Internet> internet = internetRepository.findById(id);

        return internet;
    }

    @PostMapping(value = {"v1/smotreshka/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Internet internet, Errors errors) {
        System.out.println("v1/internet/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Internet addInternet = internetRepository.save(internet);
        return new ResponseEntity<>(addInternet, HttpStatus.OK);
    }

    @DeleteMapping({"v1/smotreshka/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete smotreshka " + id);
        internetRepository.deleteById(id);

        return true;
    }
}
