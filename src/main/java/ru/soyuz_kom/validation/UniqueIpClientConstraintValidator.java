package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.validator.UniqueIpClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueIpClientConstraintValidator implements ConstraintValidator<UniqueIpClient, Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(UniqueIpClient uniqueFieldClient) {

    }

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext ctx) {
        Optional<Client> dataClient;

        try {
            dataClient = clientRepository.findByIp(client.getIp());
        } catch (NullPointerException ex){
            return true;
        }
        if (dataClient
                .map(p -> p.getId())
                .filter(id -> !id.equals(client.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("ip").addConstraintViolation();
            return false;
        }

        return true;
    }
}
