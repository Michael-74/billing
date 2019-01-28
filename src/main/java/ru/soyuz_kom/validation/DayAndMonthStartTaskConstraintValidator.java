package ru.soyuz_kom.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.soyuz_kom.entity.Task;
import ru.soyuz_kom.repository.ClientRepository;
import ru.soyuz_kom.validator.ValidDayAndMonthStart;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DayAndMonthStartTaskConstraintValidator implements ConstraintValidator<ValidDayAndMonthStart, Task> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ValidDayAndMonthStart validDayAndMonthStart) {

    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext ctx) {

        if(task.getDayStart() == null && task.getMonthStart() == null) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "начало: день и месяц не заполнены")
                    .addPropertyNode("day_start").addConstraintViolation();
            return false;
        }

        return true;
    }
}
