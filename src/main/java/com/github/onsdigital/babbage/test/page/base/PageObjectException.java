package com.github.onsdigital.babbage.test.page.base;

/**
 * This exception signals that a {@link com.github.onsdigital.babbage.test.page.base.PageObject} is not able to work with the current markup.
 */
public class PageObjectException extends RuntimeException {

    public PageObjectException() {
    }

    public PageObjectException(String message) {
        super(message);
    }

    public PageObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageObjectException(Throwable cause) {
        super(cause);
    }

    public PageObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
