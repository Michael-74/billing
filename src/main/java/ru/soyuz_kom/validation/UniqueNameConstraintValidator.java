package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Tv;
import ru.soyuz_kom.repository.TvRepository;
import ru.soyuz_kom.validator.UniqueName;

import javax.validation.*;
import java.util.Optional;

public class UniqueNameConstraintValidator implements ConstraintValidator<UniqueName, Tv> {

    @Autowired
    private TvRepository tvRepository;

    @Override
    public void initialize(UniqueName uniqueName) {

    }

    @Override
    public boolean isValid(Tv tv, ConstraintValidatorContext ctx) {

        Optional<Tv> tvR;
        try {
            tvR = tvRepository.findByName(tv.getName());
        } catch (NullPointerException ex){
            return true;
        }
        if (tvR
                .map(p -> p.getId())
                .filter(id -> !id.equals(tv.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Значение существует")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
