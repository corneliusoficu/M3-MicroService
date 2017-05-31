package com.hazardmanager.users.custom_exceptions;

public class PasswordMismatchException extends Throwable {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
