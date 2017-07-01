package com.idspring.commandpattern.model.service;

import com.idspring.commandpattern.validation.CartMustNotExists;
import lombok.Builder;
import lombok.Data;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Data
@Builder
public class CreateNewCartRequest implements ServiceRequest {

    @CartMustNotExists
    private String cartId;
}
