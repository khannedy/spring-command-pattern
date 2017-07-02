package com.idspring.commandpattern.service.command.impl;

import com.idspring.commandpattern.model.service.PingRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class PingCommandImplTest {

    @Autowired
    private PingCommandImpl pingCommand;

    @Test
    public void execute() throws Exception {
        String response = pingCommand.execute(PingRequest.builder().build()).block();
        assertEquals("Pong", response);
    }

}