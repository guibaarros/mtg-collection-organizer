package com.mtgcollectionorganizer.api.user.exception;

public class UserPasswordNotFoundException extends RuntimeException {
    public UserPasswordNotFoundException(final String userId) {
        super(String.format("UserPassword not found with userId %s", userId));
    }

    public UserPasswordNotFoundException(final String parameter, final String value) {
        super(String.format("UserPassword not found with %s %s", parameter, value));
    }
}
