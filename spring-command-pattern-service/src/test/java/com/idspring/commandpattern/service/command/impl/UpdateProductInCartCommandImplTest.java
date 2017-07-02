package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.model.service.UpdateProductInCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.service.command.UpdateProductInCartCommand;
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
public class UpdateProductInCartCommandImplTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UpdateProductInCartCommandImpl command;

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

        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(1)
                .build();

        try {
            command.execute(request).block();
            fail("It should throw exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("CartMustExists", "ProductMustExists"));
        }
    }

    @Test
    public void testProductNotExists() throws Exception {
        String cartId = UUID.randomUUID().toString();
        createCart(cartId);

        String productId = UUID.randomUUID().toString();

        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(1)
                .build();

        try {
            command.execute(request).block();
            fail("It should throw exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("ProductMustExists"));
        }
    }

    @Test
    public void testProductNotExistsInCart() throws Exception {
        String cartId = UUID.randomUUID().toString();
        createCart(cartId);
        String productId = UUID.randomUUID().toString();
        createProduct(productId);

        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(1)
                .build();

        try {
            command.execute(request).block();
            fail("It should throw exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("ProductMustExistsInCart"));
        }
    }

    @Test
    public void testProductQuantityNotEnough() throws Exception {
        String productId = UUID.randomUUID().toString();
        createProduct(productId);

        String cartId = UUID.randomUUID().toString();
        createCartWithItem(cartId, productId);

        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(10)
                .build();

        try {
            command.execute(request).block();
            fail("It should throw exception");
        } catch (CommandValidationException ex) {
            List<String> messages = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages, hasItems("ProductQuantityMustEnough"));
        }
    }

    @Test
    public void testSuccess() throws Exception {
        String productId = UUID.randomUUID().toString();
        createProduct(productId);

        String cartId = UUID.randomUUID().toString();
        createCartWithItem(cartId, productId);

        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(5)
                .build();

        Cart result = command.execute(request).block();

        assertEquals(Integer.valueOf(6), result.getItems().get(0).getQuantity());
    }
}