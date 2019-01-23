package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.validator.UniqueLoginClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueLoginClientConstraintValidator implements ConstraintValidator<UniqueLoginClient, Client> {

    @Autowired
    private ClientRepository clientRepository;
    private String fields;

    @Override
    public void initialize(UniqueLoginClient uniqueFieldClient) {
        fields = uniqueFieldClient.value();
    }

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext ctx) {
        Optional<Client> dataClient;

        try {
            dataClient = clientRepository.findByLogin(client.getLogin());
        } catch (NullPointerException ex){
            return true;
        }
        if (dataClient
                .map(p -> p.getId())
                .filter(id -> !id.equals(client.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("login").addConstraintViolation();
            return false;
        }

        return true;
    }
}
