package com.idspring.commandpattern.controller;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.controller.CartAddProductRequest;
import com.idspring.commandpattern.model.controller.CartUpdateProductRequest;
import com.idspring.commandpattern.model.controller.Response;
import com.idspring.commandpattern.model.service.*;
import com.idspring.commandpattern.service.ServiceExecutor;
import com.idspring.commandpattern.service.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private ServiceExecutor serviceExecutor;

    @RequestMapping(value = "/{cartId}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Cart>> create(@PathVariable("cartId") String cartId) {
        CreateNewCartRequest request = CreateNewCartRequest.builder()
                .cartId(cartId)
                .build();

        return serviceExecutor.execute(CreateNewCartCommand.class, request)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{cartId}/_add-product", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Cart>> addProduct(@PathVariable("cartId") String cartId,
                                           @RequestBody CartAddProductRequest requestBody) {
        AddProductToCartRequest request = AddProductToCartRequest.builder()
                .cartId(cartId)
                .productId(requestBody.getProductId())
                .quantity(requestBody.getQuantity())
                .build();

        return serviceExecutor.execute(AddProductToCartCommand.class, request)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Cart>> detail(@PathVariable("cartId") String cartId) {
        GetCartDetailRequest request = GetCartDetailRequest.builder()
                .cartId(cartId)
                .build();

        return serviceExecutor.execute(GetCartDetailCommand.class, request)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{cartId}/_update-product", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Cart>> updateProduct(@PathVariable("cartId") String cartId,
                                              @RequestBody CartUpdateProductRequest requestBody) {
        UpdateProductInCartRequest request = UpdateProductInCartRequest.builder()
                .cartId(cartId)
                .productId(requestBody.getProductId())
                .quantity(requestBody.getQuantity())
                .build();

        return serviceExecutor.execute(UpdateProductInCartCommand.class, request)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/{cartId}/{productId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Cart>> removeProduct(@PathVariable("cartId") String cartId,
                                              @PathVariable("productId") String productId) {
        RemoveProductFromCartRequest request = RemoveProductFromCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();

        return serviceExecutor.execute(RemoveProductFromCartCommand.class, request)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
