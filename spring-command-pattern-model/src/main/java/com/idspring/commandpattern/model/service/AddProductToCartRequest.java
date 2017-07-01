package com.idspring.commandpattern.model.service;

import com.idspring.commandpattern.validation.CartMustExists;
import com.idspring.commandpattern.validation.ProductMustExists;
import com.idspring.commandpattern.validation.ProductQuantityMustEnough;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
@ProductQuantityMustEnough
public class AddProductToCartRequest implements ServiceRequest, ProductQuantityMustEnough.ProductQuantity {

    @NotBlank
    @CartMustExists
    private String cartId;

    @NotBlank
    @ProductMustExists
    private String productId;

    @Min(1)
    @Max(10)
    private Integer quantity;

}
