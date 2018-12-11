package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.entity.Preset;
import ru.soyuz_kom.repository.PresetRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class PresetController extends AdminController {

    @Autowired
    private PresetRepository presetRepository;

    @PostMapping({"v1/preset/"})
    @ResponseBody
    public Iterable<Preset> index(@RequestBody String url) {
        Iterable<Preset> preset = presetRepository.findByUrl(url);

        return preset;
    }

    @GetMapping({"v1/preset/{id}"})
    public Optional<Preset> show(@PathVariable Integer id) {
        Optional<Preset> preset = presetRepository.findById(id);

        return preset;
    }

    @PostMapping({"v1/preset/store"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody Preset preset, Errors errors) {
        System.out.println("v1/preset/store");
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Preset addPreset = presetRepository.save(preset);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping({"v1/preset/delete"})
    @ResponseBody
    public Integer delete(Integer presetId) {
        System.out.println("delete preset " + presetId);
        presetRepository.deleteById(presetId);

        return presetId;
    }
}
