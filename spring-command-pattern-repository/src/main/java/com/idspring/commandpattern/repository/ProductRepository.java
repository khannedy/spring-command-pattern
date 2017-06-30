package com.idspring.commandpattern.repository;

import com.idspring.commandpattern.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
