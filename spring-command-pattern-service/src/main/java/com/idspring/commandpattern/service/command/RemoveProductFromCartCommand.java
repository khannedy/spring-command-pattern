package com.idspring.commandpattern.service.command;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.RemoveProductFromCartRequest;
import com.idspring.commandpattern.service.Command;

/**
 * @author Eko Kurniawan Khannedy
 * @since 02/07/17
 */
public interface RemoveProductFromCartCommand extends Command<Cart, RemoveProductFromCartRequest> {

}
