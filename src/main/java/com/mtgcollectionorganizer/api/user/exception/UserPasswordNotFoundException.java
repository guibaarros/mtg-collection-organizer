package com.mtgcollectionorganizer.api.user.exception;

public class UserPasswordNotFoundException extends RuntimeException {
    public UserPasswordNotFoundException(final String userId) {
        super(String.format("UserPassword not found with userId %s", userId));
    }
}
