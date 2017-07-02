package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.validation.CartMustExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class CartMustExistsValidator implements ConstraintValidator<CartMustExists, String> {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void initialize(CartMustExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return cartRepository.existsById(value).block();
    }
}
