package com.idspring.commandpattern.service.command;

import com.idspring.commandpattern.model.service.ServiceRequest;
import com.idspring.commandpattern.service.Command;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public interface PingCommand extends Command<String, ServiceRequest> {

}
