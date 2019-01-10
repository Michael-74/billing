package ru.soyuz_kom.controller.admin;

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
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RentController extends AdminController {

    @Autowired
    private RentRepository rentRepository;

    @GetMapping({"v1/rent","v1/rent/"})
    public Iterable<Rent> index() {
        Iterable<Rent> rents = rentRepository.findAllByOrderByIdDesc();

        return rents;
    }

    @GetMapping({"v1/rent/{id}"})
    public Optional<Rent> show(@PathVariable Integer id) {
        Optional<Rent> rent = rentRepository.findById(id);

        return rent;
    }

    @PostMapping(value = {"v1/rent/store"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody("rent") Rent rent, @RequestParam("tasks") String tasks, Errors errors) {
        System.out.println("v1/rent/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Rent addRent = rentRepository.save(rent);
        System.out.println("tasks data: " + tasks);
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
                    string += entry.getKey() + "==" + entry.getValue() + "*;";
                    break;
                default:
                    //string += entry.getKey() + "==" + entry.getValue() + "*;";
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
            rent = rentRepository.findAllByOrderByIdDesc();
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