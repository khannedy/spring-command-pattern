package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.validation.ProductMustExists;
import com.idspring.commandpattern.validation.ProductMustExistsInCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class ProductMustExistsInCartValidator implements ConstraintValidator<ProductMustExistsInCart, ProductMustExistsInCart.ProductInCart> {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void initialize(ProductMustExistsInCart constraintAnnotation) {

    }

    @Override
    public boolean isValid(ProductMustExistsInCart.ProductInCart value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Cart cart = cartRepository.findById(value.getCartId()).block();
        if (cart == null) {
            return true;
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return false;
        }

        return cart.getItems().stream()
                .anyMatch(cartItem -> cartItem.getId().equals(value.getProductId()));
    }
}
