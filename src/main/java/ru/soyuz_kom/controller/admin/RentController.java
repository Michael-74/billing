package ru.soyuz_kom.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Rent;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.helper.CriteriaHelper;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.repository.TaskRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.*;
import java.util.*;

@RestController
public class RentController extends AdminController {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping({"v1/rent","v1/rent/"})
    public Iterable<Rent> index() {
        Iterable<Rent> rents = rentRepository.findAll();

        return rents;
    }

    @GetMapping({"v1/rent/{id}"})
    public Optional<Rent> show(@PathVariable Integer id) {
        Optional<Rent> rent = rentRepository.findById(id);

        return rent;
    }

    @PostMapping(value = {"v1/rent/store"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Rent rent, Errors errors) {

        System.out.println("rent: " + rent.getTasks());
        HashMap error = new HashMap<>();
        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Rent addRent = rentRepository.save(rent);

        return new ResponseEntity<>(addRent, HttpStatus.OK);
    }

    @PostMapping({"v1/rent/search"})
    @ResponseBody
    public Iterable<Rent> search(@RequestBody HashMap<String, Object> preset) {


        String string = "";

        for(Map.Entry<String, Object> entry : preset.entrySet()) {
            if(entry.getValue() == null || entry.getValue() == "") {
                continue;
            }

            switch (entry.getKey()) {
                case "name":
                    string += CriteriaHelper.parseAndBuildEqualMore(entry.getKey(), entry.getValue());
                    break;
                case "createdAt":
                    string += CriteriaHelper.parseAndBuildLessAndGreatThan(entry.getKey(), entry.getValue());
                    break;
                case "isStatus":
                    string += CriteriaHelper.parseAndBuildEqualBool(entry.getKey(), entry.getValue());
                    break;
            }
        }
        Iterable<Rent> rent;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            System.out.println("string search rent: " + newString);

            Node rootNode = new RSQLParser().parse(newString);
            Specification<Rent> spec = rootNode.accept(new CustomRsqlVisitor<Rent>());

            rent = rentRepository.findAll(spec);
        } else {
            rent = rentRepository.findAll();
        }

        return rent;
    }

    @DeleteMapping({"v1/rent/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete rent " + id);
        rentRepository.deleteById(id);

        return true;
    }
}
