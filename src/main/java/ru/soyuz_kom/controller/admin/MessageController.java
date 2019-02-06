package ru.soyuz_kom.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.dto.MessageDTO;

import java.util.HashMap;
import java.util.List;

@RestController
public class MessageController extends AdminController {

    @PostMapping(value = {"v1/mail/send"})
    @ResponseBody
    public ResponseEntity store(@RequestBody MessageDTO message, Errors errors) {
        System.out.println("v1/mail/send" + message);
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }


        //Mikrotik addMikrotik = mikrotikRepository.save(mikrotik);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
