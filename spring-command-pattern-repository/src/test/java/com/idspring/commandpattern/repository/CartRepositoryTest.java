package com.idspring.commandpattern.repository;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testSaveCart() throws Exception {
        Cart cart = Cart.builder()
                .id("id")
                .items(Arrays.asList(
                        CartItem.builder()
                                .id("1")
                                .name("Mie Ayam Goreng")
                                .price(1000L)
                                .quantity(10)
                                .build(),
                        CartItem.builder()
                                .id("1")
                                .name("Mie Ayam Rebus")
                                .price(500L)
                                .quantity(5)
                                .build()
                ))
                .build();

        Cart result = cartRepository.save(cart).block();

        assertEquals(cart, result);
    }

    @Test
    public void testSaveAndGetCart() throws Exception {
        Cart cart = Cart.builder()
                .id("id")
                .items(Arrays.asList(
                        CartItem.builder()
                                .id("1")
                                .name("Mie Ayam Goreng")
                                .price(1000L)
                                .quantity(10)
                                .build(),
                        CartItem.builder()
                                .id("1")
                                .name("Mie Ayam Rebus")
                                .price(500L)
                                .quantity(5)
                                .build()
                ))
                .build();

        cartRepository.save(cart).block();
        Cart result = cartRepository.findById(cart.getId()).block();

        assertEquals(cart, result);
    }
}