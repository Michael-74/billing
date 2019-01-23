package ru.soyuz_kom.validator;

import ru.soyuz_kom.validation.UniqueContractClientConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueContractClientConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueContractClient {

    String message() default "Значение существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
