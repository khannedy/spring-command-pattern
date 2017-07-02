package com.idspring.commandpattern.validation;

import com.idspring.commandpattern.validation.validator.ProductMustExistsInCartValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Target({TYPE, ANNOTATION_TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {ProductMustExistsInCartValidator.class})
@Documented
public @interface ProductMustExistsInCart {

    String message() default "ProductMustExistsInCart";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    interface ProductInCart {

        String getCartId();

        String getProductId();

    }

}
