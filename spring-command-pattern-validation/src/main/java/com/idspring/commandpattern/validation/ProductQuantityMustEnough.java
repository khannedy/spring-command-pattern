package com.idspring.commandpattern.validation;

import com.idspring.commandpattern.validation.validator.ProductQuantityMustEnoughValidator;
import com.idspring.commandpattern.validation.validator.ProductQuantityUpdateMustEnoughValidator;

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
@Constraint(validatedBy = {
        ProductQuantityMustEnoughValidator.class,
        ProductQuantityUpdateMustEnoughValidator.class
})
@Documented
public @interface ProductQuantityMustEnough {

    String message() default "ProductQuantityMustEnough";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] path() default {};

    interface ProductQuantity {

        String getProductId();

        Integer getQuantity();

    }

    interface ProductQuantityUpdate {

        String getCartId();

        String getProductId();

        Integer getQuantity();

    }

}
