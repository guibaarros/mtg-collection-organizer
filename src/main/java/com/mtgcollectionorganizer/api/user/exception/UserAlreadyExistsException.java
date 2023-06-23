package com.mtgcollectionorganizer.api.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String userName) {
        super(String.format("User already exists with username %s", userName));
    }
}
