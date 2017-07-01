package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.CreateNewCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.exception.CommandValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateNewCartCommandImplTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CreateNewCartCommandImpl command;

    private void createCart(String id) {
        Cart cart = Cart.builder()
                .id(id)
                .build();

        cartRepository.save(cart).block();
    }

    @Test
    public void testSuccessCreateCart() throws Exception {
        String cartId = "not-exists-cart-id";
        CreateNewCartRequest request = CreateNewCartRequest.builder()
                .cartId(cartId)
                .build();

        Cart cart = command.execute(request).block();
        Cart dbCart = cartRepository.findById(cartId).block();

        assertEquals(dbCart, cart);
    }

    @Test
    public void testFailedCartAlreadyExists() throws Throwable {
        String cartId = "exists-cart";
        createCart(cartId);

        CreateNewCartRequest request = CreateNewCartRequest.builder()
                .cartId(cartId)
                .build();

        try {
            command.execute(request).block();
            fail("It should be throw exception");
        } catch (CommandValidationException e) {
            e.getConstraintViolations().forEach(constraintViolation ->
                    assertEquals("CartMustNotExists", constraintViolation.getMessage())
            );
        }
    }
}