package com.idspring.commandpattern.service.command.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public class PingCommandImplTest {

    @Test
    public void execute() throws Exception {
        PingCommandImpl pingService = new PingCommandImpl();
        String response = pingService.execute(null).block();

        assertEquals("Pong", response);
    }

}