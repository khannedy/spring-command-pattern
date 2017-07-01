package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.model.service.ServiceRequest;
import com.idspring.commandpattern.service.AbstractCommand;
import com.idspring.commandpattern.service.command.PingCommand;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Component
public class PingCommandImpl extends AbstractCommand<String, ServiceRequest> implements PingCommand {

    @Override
    public Mono<String> doExecute(ServiceRequest request) {
        return Mono.just("Pong");
    }
}
