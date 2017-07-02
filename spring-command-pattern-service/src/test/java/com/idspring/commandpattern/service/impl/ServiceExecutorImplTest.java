package com.idspring.commandpattern.service.impl;

import com.idspring.commandpattern.model.service.ServiceRequest;
import com.idspring.commandpattern.service.Command;
import com.idspring.commandpattern.service.command.PingCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class ServiceExecutorImplTest {

    @Autowired
    private ServiceExecutorImpl serviceExecutor;

    @Test
    public void testExecutePingCommand() throws Exception {
        ServiceRequest request = mock(ServiceRequest.class);
        String result = serviceExecutor.execute(PingCommand.class, request).block();

        assertEquals("Pong", result);
    }

    @Test(expected = BeansException.class)
    public void testCommandNotFound() throws Exception {
        ServiceRequest request = mock(ServiceRequest.class);
        serviceExecutor.execute(UnknownCommand.class, request);

        fail("It should be throw BeansException");
    }

    static class UnknownCommand implements Command<String, ServiceRequest> {

        @Override
        public Mono<String> execute(ServiceRequest request) {
            return Mono.empty();
        }
    }
}