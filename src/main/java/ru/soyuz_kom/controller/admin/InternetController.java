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
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.Valid;
import java.util.*;

@RestController
public class InternetController extends AdminController {

    @Autowired
    private InternetRepository internetRepository;

    @GetMapping({"v1/internet","v1/internet/"})
    public Iterable<Internet> index() {
        Iterable<Internet> internets = internetRepository.findAll();

        return internets;
    }

    @GetMapping({"v1/internet/{id}"})
    public Optional<Internet> show(@PathVariable Integer id) {
        Optional<Internet> internet = internetRepository.findById(id);

        return internet;
    }

    @PostMapping(value = {"v1/internet/store"})
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

    @PostMapping({"v1/internet/search"})
    @ResponseBody
    @CacheEvict(value="schedule", allEntries=true)
    public Iterable<Internet> search(@RequestBody HashMap<String, Object> preset) {


        String string = "";

        for(Map.Entry<String, Object> entry : preset.entrySet()) {
            if(entry.getValue() == null || entry.getValue() == "") {
                continue;
            }

            switch (entry.getKey()) {
                case "speed":
                    ArrayList data = (ArrayList) entry.getValue();
                    if(data.get(0) != null && data.get(0) != "") {
                        //string += entry.getKey() + ">" + data.get(0) + ";";
                    }
                    if(data.get(1) != null && data.get(1) != "") {
                        //string += entry.getKey() + "<" + data.get(1) + ";";
                    }
                    break;
                case "price":
                    ArrayList price = (ArrayList) entry.getValue();
                    if(price.get(0) != null && price.get(0) != "") {
                        //string += entry.getKey() + ">" + data.get(0) + ";";
                    }
                    if(price.get(1) != null && price.get(1) != "") {
                        //string += entry.getKey() + "<" + data.get(1) + ";";
                    }
                    break;
                case "name":
                    string += entry.getKey() + "==" + entry.getValue() + "*;";
                    break;
                default:
                    //string += entry.getKey() + "==" + entry.getValue() + "*;";
            }
        }
        Iterable<Internet> internet;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            System.out.println("string search Internet: " + newString);

            Node rootNode = new RSQLParser().parse(newString);
            Specification<Internet> spec = rootNode.accept(new CustomRsqlVisitor<Internet>());

            internet = internetRepository.findAll(spec);
        } else {
            internet = internetRepository.findAllByOrderByIdDesc();
        }

        return internet;
    }

    @DeleteMapping({"v1/internet/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete internet " + id);
        internetRepository.deleteById(id);

        return true;
    }
}
