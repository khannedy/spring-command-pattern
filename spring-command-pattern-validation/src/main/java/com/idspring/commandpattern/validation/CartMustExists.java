package com.idspring.commandpattern.validation;

import com.idspring.commandpattern.validation.validator.CartMustExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Target({TYPE, ANNOTATION_TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {CartMustExistsValidator.class})
@Documented
public @interface CartMustExists {

    String message() default "CartMustExists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] path() default {};

}
