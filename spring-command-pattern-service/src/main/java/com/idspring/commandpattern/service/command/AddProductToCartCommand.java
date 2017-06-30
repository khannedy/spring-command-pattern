package com.idspring.commandpattern.service.command;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.AddProductToCartRequest;
import com.idspring.commandpattern.service.Command;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public interface AddProductToCartCommand extends Command<Cart, AddProductToCartRequest> {

}
