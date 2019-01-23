package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.validator.UniqueContractClient;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueContractClientConstraintValidator implements ConstraintValidator<UniqueContractClient, Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(UniqueContractClient uniqueFieldClient) {

    }

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext ctx) {
        Optional<Client> dataClient;

        try {
            dataClient = clientRepository.findByContract(client.getContract());
        } catch (NullPointerException ex){
            return true;
        }
        if (dataClient
                .map(p -> p.getId())
                .filter(id -> !id.equals(client.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("contract").addConstraintViolation();
            return false;
        }

        return true;
    }
}
