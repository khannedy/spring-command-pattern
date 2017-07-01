package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.GetCartDetailRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.GetCartDetailCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Component
public class GetCartDetailCommandImpl extends AbstractCommand<Cart, GetCartDetailRequest>
        implements GetCartDetailCommand {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Mono<Cart> doExecute(GetCartDetailRequest request) {
        return cartRepository.findById(request.getCartId());
    }
}
