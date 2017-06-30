package com.idspring.commandpattern.model.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
public class AddProductToCartRequest implements ServiceRequest {

    private String cartId;

    private String productId;

    private Integer quantity;

}
