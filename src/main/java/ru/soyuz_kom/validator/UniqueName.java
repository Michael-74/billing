package ru.soyuz_kom.validator;

import ru.soyuz_kom.validation.UniqueNameConstraintValidator;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueNameConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {

    String message() default "Значение существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
