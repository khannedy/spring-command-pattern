package com.idspring.commandpattern.controller;

import com.idspring.commandpattern.model.controller.Response;
import com.idspring.commandpattern.service.ServiceExecutor;
import com.idspring.commandpattern.service.command.PingCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RestController
public class HomeController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ServiceExecutor serviceExecutor;

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<String>> home() {
        return Mono.just(Response.ok(applicationName))
                .subscribeOn(Schedulers.elastic());
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<String>> ping() {
        return serviceExecutor.execute(PingCommand.class, null)
                .map(Response::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
