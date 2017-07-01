package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.entity.CartItem;
import com.idspring.commandpattern.entity.Product;
import com.idspring.commandpattern.model.service.AddProductToCartRequest;
import com.idspring.commandpattern.repository.CartRepository;
import com.idspring.commandpattern.repository.ProductRepository;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.AddProductToCartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class AddProductToCartCommandImpl extends AbstractCommand<Cart, AddProductToCartRequest>
        implements AddProductToCartCommand {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Mono<Cart> doExecute(AddProductToCartRequest request) {
        return cartRepository.findById(request.getCartId()).flatMap(cart ->
                productRepository.findById(request.getProductId()).map(product ->
                        addOrUpdateProductInCart(cart, product, request.getQuantity())
                )
        ).flatMap(cart -> cartRepository.save(cart));
    }

    private Cart addOrUpdateProductInCart(Cart cart, Product product, Integer quantity) {
        if (isCartContainProduct(cart, product)) {
            incrementProductQuantity(cart, product, quantity);
        } else {
            addNewProductToCart(cart, product, quantity);
        }

        return cart;
    }

    private boolean isCartContainProduct(Cart cart, Product product) {
        return cart.getItems().stream()
                .anyMatch(cartItem -> cartItem.getId().equals(product.getId()));
    }

    private void incrementProductQuantity(Cart cart, Product product, Integer quantity) {
        cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(product.getId()))
                .forEach(cartItem -> cartItem.setQuantity(cartItem.getQuantity() + quantity));
    }

    private void addNewProductToCart(Cart cart, Product product, Integer quantity) {
        CartItem item = CartItem.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(quantity)
                .build();

        cart.getItems().add(item);
    }
}
