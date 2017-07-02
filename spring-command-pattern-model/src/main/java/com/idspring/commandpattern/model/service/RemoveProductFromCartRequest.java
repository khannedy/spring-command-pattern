package com.idspring.commandpattern.model.service;

import com.idspring.commandpattern.validation.CartMustExists;
import com.idspring.commandpattern.validation.ProductMustExists;
import com.idspring.commandpattern.validation.ProductMustExistsInCart;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Eko Kurniawan Khannedy
 * @since 02/07/17
 */
@Data
@Builder
@ProductMustExistsInCart(path = "productId")
public class RemoveProductFromCartRequest implements ServiceRequest,
        ProductMustExistsInCart.ProductInCart {

    @NotBlank
    @CartMustExists
    private String cartId;

    @NotBlank
    @ProductMustExists
    private String productId;

}
