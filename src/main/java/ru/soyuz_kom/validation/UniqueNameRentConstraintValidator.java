package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Rent;
import ru.soyuz_kom.repository.RentRepository;
import ru.soyuz_kom.validator.UniqueName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueNameRentConstraintValidator implements ConstraintValidator<UniqueName, Rent> {

    @Autowired
    private RentRepository rentRepository;

    @Override
    public void initialize(UniqueName uniqueName) {

    }

    @Override
    public boolean isValid(Rent rent, ConstraintValidatorContext ctx) {

        Optional<Rent> tvR;
        try {
            tvR = rentRepository.findByName(rent.getName());
        } catch (NullPointerException ex){
            return true;
        }
        if (tvR
                .map(p -> p.getId())
                .filter(id -> !id.equals(rent.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
