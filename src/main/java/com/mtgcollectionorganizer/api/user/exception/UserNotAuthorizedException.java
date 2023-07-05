package com.mtgcollectionorganizer.api.user.exception;

public class UserNotAuthorizedException extends RuntimeException{

    public UserNotAuthorizedException(final String username){
        super(String.format("user %s not authorized", username));
    }
}
