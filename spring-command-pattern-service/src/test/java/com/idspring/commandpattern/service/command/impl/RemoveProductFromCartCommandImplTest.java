package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.model.service.RemoveProductFromCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.service.exception.CommandValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 02/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class RemoveProductFromCartCommandImplTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RemoveProductFromCartCommandImpl command;

    private Cart createCart(String cartId) {
        Cart cart = Cart.builder()
                .id(cartId)
                .build();

        return cartRepository.save(cart).block();
    }

    private Cart createCartWithItem(String cartId, String productId) {
        Cart cart = Cart.builder()
                .id(cartId)
                .items(Collections.singletonList(
                        CartItem.builder()
                                .id(productId)
                                .name("Item Name")
                                .price(1000L)
                                .quantity(1)
                                .build()
                ))
                .build();

        return cartRepository.save(cart).block();
    }

    private Product createProduct(String productId) {
        Product product = Product.builder()
                .id(productId)
                .price(1000L)
                .stock(10)
                .name("Item Name")
                .build();

        return productRepository.save(product).block();
    }

    @Test
    public void testCartNotExists() throws Exception {
        String cartId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        RemoveProductFromCartRequest request = RemoveProductFromCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();

        try {
            command.execute(request).block();
            fail("it should thrown exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("CartMustExists", "ProductMustExists"));
        }
    }

    @Test
    public void testProductNotExists() throws Exception {
        String cartId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        createCart(cartId);

        RemoveProductFromCartRequest request = RemoveProductFromCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();

        try {
            command.execute(request).block();
            fail("it should thrown exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("ProductMustExists"));
        }
    }

    @Test
    public void testProductNotExistsInCart() throws Exception {
        String cartId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        createCart(cartId);
        createProduct(productId);

        RemoveProductFromCartRequest request = RemoveProductFromCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();

        try {
            command.execute(request).block();
            fail("it should thrown exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("ProductMustExistsInCart"));
        }
    }

    @Test
    public void testSuccess() throws Exception {
        String cartId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        createProduct(productId);
        createCartWithItem(cartId, productId);

        RemoveProductFromCartRequest request = RemoveProductFromCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();

        Cart result = command.execute(request).block();
        List<String> itemIds = result.getItems().stream()
                .map(CartItem::getId).collect(Collectors.toList());

        assertThat(itemIds, not(hasItem(productId)));
    }
}