package com.mtgcollectionorganizer.api.user.collection.exception;

public class UserCardNotFoundException extends RuntimeException{

    public UserCardNotFoundException(final String id){
        super(String.format("User card not found with id %s", id));
    }
}
