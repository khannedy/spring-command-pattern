package com.idspring.commandpattern.validation.validator;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.validation.CartMustExists;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class CartMustExistsValidatorTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private Validator validator;

    private void createCart(String id) {
        Cart cart = Cart.builder()
                .id(id)
                .items(Collections.emptyList())
                .build();

        cartRepository.save(cart).block();
    }

    @Test
    public void testValid() throws Exception {
        String cartId = "sample-cart-id";
        createCart(cartId);

        FooData data = FooData.builder()
                .cartId(cartId)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testNullMustValid() throws Exception {
        FooData data = FooData.builder()
                .cartId(null)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testInvalid() throws Exception {
        String cartId = "non-exists";
        FooData data = FooData.builder()
                .cartId(cartId)
                .build();

        Set<ConstraintViolation<FooData>> constraintViolations = validator.validate(data);
        assertEquals(1, constraintViolations.size());

        constraintViolations.forEach(fooDataConstraintViolation -> {
            assertEquals("CartMustExists", fooDataConstraintViolation.getMessage());
        });
    }

    @Data
    @Builder
    static class FooData {

        @CartMustExists
        private String cartId;
    }
}