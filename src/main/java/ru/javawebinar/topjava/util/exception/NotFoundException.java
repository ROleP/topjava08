package ru.javawebinar.topjava.util.exception;

/**
 * Created by rolep on 19/10/16.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
