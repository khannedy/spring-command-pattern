package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.validation.CartMustExists;
import com.idspring.commandpattern.validation.ProductMustExists;
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

        Cart cart = cartRepository.findById(value).block();
        return cart != null;
    }
}
