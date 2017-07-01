package com.idspring.commandpattern.service.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
public class CommandValidationException extends RuntimeException {

    private Set<ConstraintViolation<?>> constraintViolations;

    @SuppressWarnings("unchecked")
    public CommandValidationException(Set constraintViolations) {
        this.constraintViolations = (Set<ConstraintViolation<?>>) constraintViolations;
    }

    public Set<ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }
}
