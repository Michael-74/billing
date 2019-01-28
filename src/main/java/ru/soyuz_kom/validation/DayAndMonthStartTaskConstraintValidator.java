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

        // Разрешаем все значения null (весь период действует)
        if(task.getDayStart() == null && task.getMonthStart() == null && task.getDayEnd() == null && task.getMonthEnd() == null){
            return true;
        }

        // разрешаем если только заполнены день и месяц какого-то одного интервала
        if((task.getDayStart() != null && task.getMonthStart() != null) || (task.getDayEnd() != null && task.getMonthEnd() != null)) {
            return true;
        }
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(
                "день и месяц не заполнены")
                .addPropertyNode("dayStart").addConstraintViolation();
        return false;
    }
}
