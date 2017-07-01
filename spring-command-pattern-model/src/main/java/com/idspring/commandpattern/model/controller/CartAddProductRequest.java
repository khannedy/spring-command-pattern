package com.idspring.commandpattern.model.controller;

import lombok.Builder;
import lombok.Data;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
public class CartAddProductRequest {

    private String productId;

    private Integer quantity;

}
