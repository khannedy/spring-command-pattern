package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.GetCartDetailRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.exception.CommandValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class GetCartDetailCommandImplTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GetCartDetailCommandImpl command;

    private void createCart(String id) {
        Cart cart = Cart.builder()
                .id(id)
                .build();

        cartRepository.save(cart).block();
    }

    @Test
    public void testSuccessGetCartDetail() throws Exception {
        String cartId = "cart-id";
        createCart(cartId);

        GetCartDetailRequest request = GetCartDetailRequest.builder()
                .cartId(cartId)
                .build();

        Cart result = command.execute(request).block();
        assertEquals(cartId, result.getId());
    }

    @Test
    public void testFailedGetCartDetail() throws Exception {
        String cartId = "not-exists";
        GetCartDetailRequest request = GetCartDetailRequest.builder()
                .cartId(cartId)
                .build();

        try {
            command.execute(request).block();
            fail("It should throw exception");
        } catch (CommandValidationException ex) {
            ex.getConstraintViolations().forEach(constraintViolation ->
                    assertEquals("CartMustExists", constraintViolation.getMessage())
            );
        }
    }
}