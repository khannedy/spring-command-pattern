package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.UpdateProductInCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.UpdateProductInCartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Component
public class UpdateProductInCartCommandImpl extends AbstractCommand<Cart, UpdateProductInCartRequest>
        implements UpdateProductInCartCommand {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Mono<Cart> doExecute(UpdateProductInCartRequest request) {
        return cartRepository.findById(request.getCartId())
                .map(cart -> updateCartItemQuantity(cart, request));
    }

    private Cart updateCartItemQuantity(Cart cart, UpdateProductInCartRequest request) {
        cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(request.getProductId()))
                .forEach(cartItem -> cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity()));

        return cart;
    }
}
