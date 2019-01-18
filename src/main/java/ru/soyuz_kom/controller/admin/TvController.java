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
import ru.soyuz_kom.entity.Tv;
import ru.soyuz_kom.helper.CriteriaHelper;
import ru.soyuz_kom.repository.TvRepository;
import ru.soyuz_kom.rsql.CustomRsqlVisitor;

import javax.validation.Valid;
import java.util.*;

@RestController
public class TvController extends AdminController {

    @Autowired
    private TvRepository tvRepository;

    @GetMapping({"v1/tv","v1/tv/"})
    public Iterable<Tv> index() {
        Iterable<Tv> tvs = tvRepository.findAll();

        return tvs;
    }

    @GetMapping({"v1/tv/{id}"})
    public Optional<Tv> show(@PathVariable Integer id) {
        Optional<Tv> tv = tvRepository.findById(id);

        return tv;
    }

    @PostMapping(value = {"v1/tv/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Tv tv, Errors errors) {
        System.out.println("v1/tv/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Tv addTv = tvRepository.save(tv);
        return new ResponseEntity<>(addTv, HttpStatus.OK);
    }

    @PostMapping({"v1/tv/search"})
    @ResponseBody
    @CacheEvict(value="schedule", allEntries=true)
    public Iterable<Tv> search(@RequestBody HashMap<String, Object> preset) {


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
        Iterable<Tv> tv;
        if(string.length() != 0) {
            String newString = string.substring(0, string.length() - 1);
            System.out.println("string search Tv: " + newString);

            Node rootNode = new RSQLParser().parse(newString);
            Specification<Tv> spec = rootNode.accept(new CustomRsqlVisitor<Tv>());

            tv = tvRepository.findAll(spec);
        } else {
            tv = tvRepository.findAllByOrderByIdDesc();
        }

        return tv;
    }

    @DeleteMapping({"v1/tv/delete/{id}"})
    @ResponseBody
    public Boolean delete(@PathVariable Integer id) {
        System.out.println("delete tv " + id);
        tvRepository.deleteById(id);

        return true;
    }
}
