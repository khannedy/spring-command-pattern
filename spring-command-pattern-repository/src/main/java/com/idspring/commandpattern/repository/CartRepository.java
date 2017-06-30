package com.idspring.commandpattern.repository;

import com.idspring.commandpattern.entity.Cart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public interface CartRepository extends ReactiveMongoRepository<Cart, String> {

}
