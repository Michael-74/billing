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
        ctx.disableDefaultConstraintViolation();

        if(false) {
            System.out.println("2222");
            return true;
        }
        Optional<Tv> tvR = tvRepository.findByName("Тест11");
        if (tvR
                .map(p -> p.getId())
                .filter(id -> !id.equals(tv.getId())).isPresent()) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "{value.negative}")
                    .addPropertyNode("name").addConstraintViolation();
            System.out.println("GGGG");
            return false;
        }
        System.out.println("11111");
        return true;
    }
}
