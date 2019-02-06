package ru.soyuz_kom.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.soyuz_kom.dto.MessageDTO;
import ru.soyuz_kom.service.Impl.MessageMailServiceImpl;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class MessageController extends AdminController {

    @Autowired
    MessageMailServiceImpl messageMailService;

    @PostMapping(value = {"v1/mail/send"})
    @ResponseBody
    public ResponseEntity store(@Valid @RequestBody MessageDTO message, Errors errors) {
        System.out.println("v1/mail/send " + message.message);
        HashMap error = new HashMap<>();

        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (org.springframework.validation.FieldError fieldError: fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /**
         * Отправка на Email
         */
        try {
            if(message.isEmail()) {
                messageMailService.send(message.emails, message.message);
            }
        } catch(Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
