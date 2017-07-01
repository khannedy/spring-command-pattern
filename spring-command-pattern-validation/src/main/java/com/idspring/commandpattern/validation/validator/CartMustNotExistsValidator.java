package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.validation.CartMustNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class CartMustNotExistsValidator implements ConstraintValidator<CartMustNotExists, String> {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void initialize(CartMustNotExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return cartRepository.existsById(value).block() == Boolean.FALSE;
    }
}
