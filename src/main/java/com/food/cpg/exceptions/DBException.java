package com.food.cpg.exceptions;

/**
 * This is a data access layer exception
 *
 * @author Kartik Gevariya
 */
public class DBException extends Exception {
    private static final long serialVersionUID = 1L;

    public DBException() {
        super("Data access exception");
    }

    public DBException(String msg) {
        super(msg);
    }

    public DBException(Throwable t) {
        super(t);
    }

    public DBException(String msg, Throwable t) {
        super(msg, t);
    }
}