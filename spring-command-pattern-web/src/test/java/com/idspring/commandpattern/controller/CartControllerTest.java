package com.idspring.commandpattern.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.model.controller.CartAddProductRequest;
import com.idspring.commandpattern.model.controller.CartUpdateProductRequest;
import com.idspring.commandpattern.model.service.AddProductToCartRequest;
import com.idspring.commandpattern.model.service.CreateNewCartRequest;
import com.idspring.commandpattern.model.service.GetCartDetailRequest;
import com.idspring.commandpattern.model.service.UpdateProductInCartRequest;
import com.idspring.commandpattern.service.command.AddProductToCartCommand;
import com.idspring.commandpattern.service.command.CreateNewCartCommand;
import com.idspring.commandpattern.service.command.GetCartDetailCommand;
import com.idspring.commandpattern.service.command.UpdateProductInCartCommand;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    @Value("${local.server.port}")
    private Integer serverPort;

    @MockBean
    private CreateNewCartCommand createNewCartCommand;

    @MockBean
    private AddProductToCartCommand addProductToCartCommand;

    @MockBean
    private GetCartDetailCommand getCartDetailCommand;

    @MockBean
    private UpdateProductInCartCommand updateProductInCartCommand;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CartItem> cartItems;

    private Cart cart;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;

        setUpCartItems();
        setUpCart();
    }

    private void setUpCartItems() {
        cartItems = Arrays.asList(
                CartItem.builder()
                        .id("itemId1")
                        .price(500L)
                        .quantity(10)
                        .name("Item Name1")
                        .build(),
                CartItem.builder()
                        .id("itemId2")
                        .price(1000L)
                        .quantity(5)
                        .name("Item Name2")
                        .build()
        );
    }

    private void setUpCart() {
        cart = Cart.builder()
                .id("cartId")
                .items(cartItems)
                .build();
    }

    @Test
    public void testCreateCartSuccess() throws Exception {
        mockCreateCommandReturnSuccess();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                .when()
                    .post("/carts/cardId")
                .then()
                    .body("code", equalTo(HttpStatus.OK.value()))
                    .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                    .body("data.id", equalTo("cartId"));
        // @formatter:on

        verify(createNewCartCommand, times(1))
                .execute(Mockito.any(CreateNewCartRequest.class));
    }

    @Test
    public void testCreateCartError() throws Exception {
        mockCreateCommandThrowException();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                .when()
                    .post("/carts/cardId")
                .then()
                    .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        // @formatter:on

        verify(createNewCartCommand, times(1))
                .execute(Mockito.any(CreateNewCartRequest.class));
    }

    private void mockCreateCommandReturnSuccess() {
        Cart cart = Cart.builder()
                .id("cartId")
                .build();

        when(createNewCartCommand.execute(Mockito.any(CreateNewCartRequest.class)))
                .thenReturn(Mono.just(cart));
    }

    private void mockCreateCommandThrowException() {
        when(createNewCartCommand.execute(Mockito.any(CreateNewCartRequest.class)))
                .thenReturn(Mono.error(new NullPointerException()));
    }

    @Test
    public void testAddProductSuccess() throws Exception {
        mockAddProductReturnSuccess();

        CartAddProductRequest request = CartAddProductRequest.builder()
                .productId("item1")
                .quantity(1)
                .build();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(request))
                .when()
                    .post("/carts/cardId/_add-product")
                .then()
                    .body("code", equalTo(HttpStatus.OK.value()))
                    .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                    .body("data.id", equalTo("cartId"))
                    .body("data.items.id", hasItems("itemId1", "itemId2"))
                    .body("data.items.price", hasItems(1000, 500))
                    .body("data.items.quantity", hasItems(10, 5))
                    .body("data.items.name", hasItems("Item Name1", "Item Name2"));
        // @formatter:on

        verify(addProductToCartCommand, times(1))
                .execute(Mockito.any(AddProductToCartRequest.class));
    }

    @Test
    public void testAddProductError() throws Exception {
        mockAddProductThrownException();

        CartAddProductRequest request = CartAddProductRequest.builder()
                .productId("item1")
                .quantity(1)
                .build();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(request))
                .when()
                    .post("/carts/cardId/_add-product")
                .then()
                    .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        // @formatter:on

        verify(addProductToCartCommand, times(1))
                .execute(Mockito.any(AddProductToCartRequest.class));
    }

    private void mockAddProductReturnSuccess() {
        when(addProductToCartCommand.execute(Mockito.any(AddProductToCartRequest.class)))
                .thenReturn(Mono.just(cart));
    }

    private void mockAddProductThrownException() {
        when(addProductToCartCommand.execute(Mockito.any(AddProductToCartRequest.class)))
                .thenReturn(Mono.error(new NullPointerException()));
    }

    @Test
    public void testGetCartSuccess() throws Exception {
        mockGetCartDetailSuccess();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                .when()
                    .get("/carts/cardId")
                .then()
                    .body("code", equalTo(HttpStatus.OK.value()))
                    .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                    .body("data.id", equalTo("cartId"));
        // @formatter:on

        verify(getCartDetailCommand, times(1))
                .execute(Mockito.any(GetCartDetailRequest.class));
    }

    @Test
    public void testGetCartError() throws Exception {
        mockGetCartDetailThrownException();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                .when()
                    .get("/carts/cardId")
                .then()
                    .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        // @formatter:on

        verify(getCartDetailCommand, times(1))
                .execute(Mockito.any(GetCartDetailRequest.class));
    }

    private void mockGetCartDetailSuccess() {
        when(getCartDetailCommand.execute(Mockito.any(GetCartDetailRequest.class)))
                .thenReturn(Mono.just(cart));
    }

    private void mockGetCartDetailThrownException() {
        when(getCartDetailCommand.execute(Mockito.any(GetCartDetailRequest.class)))
                .thenReturn(Mono.error(new NullPointerException()));
    }

    @Test
    public void testUpdateProductSuccess() throws Exception {
        mockUpdateProductSuccess();

        CartUpdateProductRequest request = CartUpdateProductRequest.builder()
                .productId("item1")
                .quantity(5)
                .build();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(request))
                .when()
                    .put("/carts/cardId/_update-product")
                .then()
                    .body("code", equalTo(HttpStatus.OK.value()))
                    .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                    .body("data.id", equalTo("cartId"))
                    .body("data.items.id", hasItems("itemId1", "itemId2"))
                    .body("data.items.price", hasItems(1000, 500))
                    .body("data.items.quantity", hasItems(10, 5))
                    .body("data.items.name", hasItems("Item Name1", "Item Name2"));
        // @formatter:on

        verify(updateProductInCartCommand, times(1))
                .execute(Mockito.any(UpdateProductInCartRequest.class));
    }

    @Test
    public void testUpdateProductError() throws Exception {
        mockUpdateProductThrownException();

        CartUpdateProductRequest request = CartUpdateProductRequest.builder()
                .productId("item1")
                .quantity(5)
                .build();

        // @formatter:off
        RestAssured.given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(request))
                .when()
                    .put("/carts/cardId/_update-product")
                .then()
                    .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        // @formatter:on

        verify(updateProductInCartCommand, times(1))
                .execute(Mockito.any(UpdateProductInCartRequest.class));
    }

    private void mockUpdateProductSuccess() {
        when(updateProductInCartCommand.execute(Mockito.any(UpdateProductInCartRequest.class)))
                .thenReturn(Mono.just(cart));
    }

    private void mockUpdateProductThrownException() {
        when(updateProductInCartCommand.execute(Mockito.any(UpdateProductInCartRequest.class)))
                .thenReturn(Mono.error(new NullPointerException()));
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(
                createNewCartCommand,
                addProductToCartCommand,
                getCartDetailCommand,
                updateProductInCartCommand
        );
    }
}