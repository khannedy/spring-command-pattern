package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.CreateNewCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.CreateNewCartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Component
public class CreateNewCartCommandImpl extends AbstractCommand<Cart, CreateNewCartRequest>
        implements CreateNewCartCommand {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Mono<Cart> doExecute(CreateNewCartRequest request) {
        Cart cart = newCart(request.getCartId());
        return cartRepository.save(cart);
    }

    private Cart newCart(String id) {
        return Cart.builder()
                .id(id)
                .build();
    }
}
