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
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.*;
import java.util.*;

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
    public ResponseEntity store(@RequestBody HashMap<String, Object> map) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ObjectMapper mapper = new ObjectMapper();
        Rent rent = mapper.convertValue(map.get("rent"), Rent.class);
        //String tasks = (String) map.get("tasks");

        List tasks = (List) Arrays.asList(map.get("tasks"));
        //Set s = mapper.convertValue(map.get("tasks"), Set.class);

        System.out.println("ss: " + tasks);

        Set<ConstraintViolation<Object>> violations = validator.validate(rent);
        HashMap error = new HashMap<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                error.put(violation.getPropertyPath(), violation.getMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Rent addRent = rentRepository.save(rent);
        //addRent.setTasks(tasks);
        //addRent.getTasks().add(tasks);

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
