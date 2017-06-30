package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.model.service.AddProductToCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.service.command.AddProductToCartCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddProductToCartCommandImplTest {

    @Autowired
    private AddProductToCartCommand command;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    private void createCart(String id) {
        Cart cart = Cart.builder()
                .id(id)
                .build();
        cartRepository.save(cart).block();
    }

    private void createProduct(String id) {
        Product product = Product.builder()
                .id(id)
                .name("Product Name")
                .price(1000L)
                .stock(100)
                .build();
        productRepository.save(product).block();
    }

    @Test
    public void testAddProductToEmptyCart() throws Exception {
        String cartId = "sample-cart-id";
        String productId = "sample-product-id";

        createCart(cartId);
        createProduct(productId);

        AddProductToCartRequest request = AddProductToCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(10)
                .build();

        Cart result = command.execute(request).block();
        Cart expected = Cart.builder()
                .id(cartId)
                .items(Collections.singletonList(
                        CartItem.builder()
                                .id(productId)
                                .name("Product Name")
                                .price(1000L)
                                .quantity(10)
                                .build()
                ))
                .build();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void testIncrementProductQuantityInCart() throws Exception {
        testAddProductToEmptyCart();

        String cartId = "sample-cart-id";
        String productId = "sample-product-id";

        AddProductToCartRequest request = AddProductToCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(10)
                .build();

        Cart result = command.execute(request).block();
        Cart expected = Cart.builder()
                .id(cartId)
                .items(Collections.singletonList(
                        CartItem.builder()
                                .id(productId)
                                .name("Product Name")
                                .price(1000L)
                                .quantity(20)
                                .build()
                ))
                .build();

        assertThat(result, equalTo(expected));
    }
}