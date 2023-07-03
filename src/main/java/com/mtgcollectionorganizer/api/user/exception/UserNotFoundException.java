package com.mtgcollectionorganizer.api.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String parameter, final String userName) {
        super(String.format("User not found with %s %s", parameter, userName));
    }
}
