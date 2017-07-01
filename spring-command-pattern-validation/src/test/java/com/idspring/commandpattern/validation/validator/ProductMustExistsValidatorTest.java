package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.validation.ProductMustExists;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMustExistsValidatorTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Validator validator;

    private void createProduct(String id) {
        Product product = Product.builder()
                .id(id)
                .name("Name")
                .stock(1000)
                .price(1000L)
                .build();

        productRepository.save(product).block();
    }

    @Test
    public void testValid() throws Exception {
        String productId = "sample-product-id";
        createProduct(productId);

        FooData data = FooData.builder()
                .productId(productId)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testNullMustValid() throws Exception {
        FooData data = FooData.builder()
                .productId(null)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testInvalid() throws Exception {
        String productId = "not-exists";
        FooData data = FooData.builder()
                .productId(productId)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(1, constraintViolations.size());

        constraintViolations.forEach(fooDataConstraintViolation -> {
            assertEquals("ProductMustExists", fooDataConstraintViolation.getMessage());
        });
    }

    @Data
    @Builder
    static class FooData {

        @ProductMustExists
        private String productId;

    }
}