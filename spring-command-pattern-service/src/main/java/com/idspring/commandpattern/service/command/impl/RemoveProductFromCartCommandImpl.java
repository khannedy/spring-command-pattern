package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.model.service.RemoveProductFromCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.RemoveProductFromCartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 02/07/17
 */
@Component
public class RemoveProductFromCartCommandImpl extends AbstractCommand<Cart, RemoveProductFromCartRequest>
        implements RemoveProductFromCartCommand {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Mono<Cart> doExecute(RemoveProductFromCartRequest request) {
        return cartRepository.findById(request.getCartId())
                .map(cart -> findCartItemAndRemoveIt(cart, request.getProductId()))
                .flatMap(cart -> cartRepository.save(cart));
    }

    private Cart findCartItemAndRemoveIt(Cart cart, String productId) {
        CartItem cartItem = findItemInCart(cart, productId);
        return removeItemFromCart(cart, cartItem);
    }

    private CartItem findItemInCart(Cart cart, String productId) {
        return cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(productId))
                .findFirst().get();
    }

    private Cart removeItemFromCart(Cart cart, CartItem cartItem) {
        cart.getItems().remove(cartItem);
        return cart;
    }
}
