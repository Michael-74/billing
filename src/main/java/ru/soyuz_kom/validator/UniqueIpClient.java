package ru.soyuz_kom.validator;

import ru.soyuz_kom.validation.UniqueIpClientConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueIpClientConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIpClient {

    String message() default "Значение существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
