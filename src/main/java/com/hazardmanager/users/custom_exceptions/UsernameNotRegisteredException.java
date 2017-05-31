package com.hazardmanager.users.custom_exceptions;

public class UsernameNotRegisteredException extends Throwable {
    public UsernameNotRegisteredException(String message) {
        super(message);
    }
}
