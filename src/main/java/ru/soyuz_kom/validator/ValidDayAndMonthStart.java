package ru.soyuz_kom.validator;

import ru.soyuz_kom.validation.DayAndMonthStartTaskConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DayAndMonthStartTaskConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDayAndMonthStart {

    String message() default "начало: день и месяц не заполнены";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
