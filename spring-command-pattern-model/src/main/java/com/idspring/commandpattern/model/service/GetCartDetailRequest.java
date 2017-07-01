package com.idspring.commandpattern.model.service;

import com.idspring.commandpattern.validation.CartMustExists;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@Data
@Builder
public class GetCartDetailRequest implements ServiceRequest {

    @NotBlank
    @CartMustExists
    private String cartId;

}
