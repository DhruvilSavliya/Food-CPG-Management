package com.food.cpg.exceptions;

/**
 * Service layer exception
 *
 * @author Kartik Gevariya
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super("An exception occurred while executing service call!");
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable t) {
        super(t);
    }

    public ServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}