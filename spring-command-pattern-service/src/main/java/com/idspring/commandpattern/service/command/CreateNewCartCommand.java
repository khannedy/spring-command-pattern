package com.idspring.commandpattern.service.command;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.CreateNewCartRequest;
import com.idspring.commandpattern.service.Command;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
public interface CreateNewCartCommand extends Command<Cart, CreateNewCartRequest> {

}
