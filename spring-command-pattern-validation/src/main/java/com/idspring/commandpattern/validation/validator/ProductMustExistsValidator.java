package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.repository.ProductRepository;
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
public class ProductMustExistsValidator implements ConstraintValidator<ProductMustExists, String> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void initialize(ProductMustExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Product product = productRepository.findById(value).block();
        return product != null;
    }
}
