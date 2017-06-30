package com.idspring.commandpattern.repository;

import com.idspring.commandpattern.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() throws Exception {
        Product product = Product.builder()
                .id("id")
                .name("name")
                .price(1000L)
                .stock(1000)
                .build();

        Product result = productRepository.save(product).block();

        assertEquals(result, product);
    }

    @Test
    public void testSaveAndGetProduct() throws Exception {
        Product product = Product.builder()
                .id("id")
                .name("name")
                .price(1000L)
                .stock(1000)
                .build();

        productRepository.save(product).block();
        Product result = productRepository.findById("id").block();

        assertEquals(result, product);
    }
}