package com.idspring.commandpattern.service;

import com.idspring.commandpattern.model.service.ServiceRequest;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public interface Command<RESULT, REQUEST extends ServiceRequest> {

    Mono<RESULT> execute(REQUEST request);

}
