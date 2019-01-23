package ru.soyuz_kom.validator;

import ru.soyuz_kom.validation.UniqueNameTvConstraintValidator;
import ru.soyuz_kom.validation.UniqueNameInternetConstraintValidator;
import ru.soyuz_kom.validation.UniqueNameRentConstraintValidator;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = {UniqueNameTvConstraintValidator.class, UniqueNameRentConstraintValidator.class, UniqueNameInternetConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {

    String message() default "Значение существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
