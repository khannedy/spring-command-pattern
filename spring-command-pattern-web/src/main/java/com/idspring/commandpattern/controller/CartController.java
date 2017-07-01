package com.idspring.commandpattern.controller;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.controller.CartAddProductRequest;
import com.idspring.commandpattern.model.controller.Response;
import com.idspring.commandpattern.model.service.AddProductToCartRequest;
import com.idspring.commandpattern.service.ServiceExecutor;
import com.idspring.commandpattern.service.command.AddProductToCartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/{cartId}/_add-product")
    public Mono<Response<Cart>> addProduct(@PathVariable("cartId") String cartId,
                                           @Validated CartAddProductRequest requestBody) {
        AddProductToCartRequest request = AddProductToCartRequest.builder()
                .cartId(cartId)
                .productId(requestBody.getProductId())
                .quantity(requestBody.getQuantity())
                .build();

        return serviceExecutor.execute(AddProductToCartCommand.class, request)
                .map(Response::okOrNotFound)
                .subscribeOn(Schedulers.elastic());
    }

}
