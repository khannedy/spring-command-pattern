package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.validation.ProductQuantityMustEnough;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class ProductQuantityMustEnoughValidator implements
        ConstraintValidator<ProductQuantityMustEnough, ProductQuantityMustEnough.ProductQuantity> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void initialize(ProductQuantityMustEnough constraintAnnotation) {

    }

    @Override
    public boolean isValid(ProductQuantityMustEnough.ProductQuantity value, ConstraintValidatorContext context) {
        if (value == null || value.getProductId() == null || value.getQuantity() == null) {
            return true;
        }

        Product product = productRepository.findById(value.getProductId()).block();

        if (isProductNotExists(product)) {
            return true;
        }

        return isStockEnough(product, value.getQuantity());
    }

    private boolean isProductNotExists(Product product) {
        return product == null;
    }

    private boolean isStockEnough(Product product, Integer quantity) {
        return product.getStock() >= quantity;
    }
}
