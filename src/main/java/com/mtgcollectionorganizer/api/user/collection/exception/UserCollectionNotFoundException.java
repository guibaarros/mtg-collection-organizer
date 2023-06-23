package com.mtgcollectionorganizer.api.user.collection.exception;

public class UserCollectionNotFoundException extends RuntimeException{

    public UserCollectionNotFoundException(final String id){
        super(String.format("User not found with id %s", id));
    }
}
