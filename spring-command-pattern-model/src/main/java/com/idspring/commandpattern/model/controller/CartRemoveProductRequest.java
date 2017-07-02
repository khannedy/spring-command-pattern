package com.idspring.commandpattern.model.controller;

import lombok.Builder;
import lombok.Data;

/**
 * @author Eko Kurniawan Khannedy
 * @since 02/07/17
 */
@Data
@Builder
public class CartRemoveProductRequest {

    private String productId;

}
