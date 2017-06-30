package com.idspring.commandpattern.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
public class CartItem {

    private String id;

    private String name;

    private Long price;

    private Integer quantity;

}
