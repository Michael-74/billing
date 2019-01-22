package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.Rent;
import ru.soyuz_kom.repository.InternetRepository;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.validator.UniqueName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueNameInternetConstraintValidator implements ConstraintValidator<UniqueName, Internet> {

    @Autowired
    private InternetRepository internetRepository;

    @Override
    public void initialize(UniqueName uniqueName) {

    }

    @Override
    public boolean isValid(Internet internet, ConstraintValidatorContext ctx) {

        Optional<Internet> tvR;
        try {
            tvR = internetRepository.findByName(internet.getName());
        } catch (NullPointerException ex){
            return true;
        }
        if (tvR
                .map(p -> p.getId())
                .filter(id -> !id.equals(internet.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
