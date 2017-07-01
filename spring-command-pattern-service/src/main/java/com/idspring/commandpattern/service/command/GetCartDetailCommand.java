package com.idspring.commandpattern.service.command;

import com.idspring.commandpattern.entity.Cart;
import com.idspring.commandpattern.model.service.GetCartDetailRequest;
import com.idspring.commandpattern.service.Command;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
public interface GetCartDetailCommand extends Command<Cart, GetCartDetailRequest> {

}
