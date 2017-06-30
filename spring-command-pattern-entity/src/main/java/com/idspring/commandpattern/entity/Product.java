package com.idspring.commandpattern.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
@Document
public class Product {

    @Id
    private String id;

    private String name;

    private Long price;

    private Integer stock;

}
