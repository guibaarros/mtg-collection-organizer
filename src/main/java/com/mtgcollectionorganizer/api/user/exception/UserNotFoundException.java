package com.mtgcollectionorganizer.api.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String userName) {
        super(String.format("User not found with username %s", userName));
    }
}
