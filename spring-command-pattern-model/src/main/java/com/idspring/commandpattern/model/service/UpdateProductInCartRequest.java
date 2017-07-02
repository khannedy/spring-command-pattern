package com.idspring.commandpattern.model.service;

import com.idspring.commandpattern.validation.CartMustExists;
import com.idspring.commandpattern.validation.ProductMustExists;
import com.idspring.commandpattern.validation.ProductMustExistsInCart;
import com.idspring.commandpattern.validation.ProductQuantityMustEnough;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Data
@Builder
@ProductMustExistsInCart(path = "productId")
@ProductQuantityMustEnough(path = "quantity")
public class UpdateProductInCartRequest implements
        ProductQuantityMustEnough.ProductQuantityUpdate,
        ProductMustExistsInCart.ProductInCart,
        ServiceRequest {

    @NotBlank
    @CartMustExists
    private String cartId;

    @NotBlank
    @ProductMustExists
    private String productId;

    @Min(0)
    @Max(10)
    private Integer quantity;

}
