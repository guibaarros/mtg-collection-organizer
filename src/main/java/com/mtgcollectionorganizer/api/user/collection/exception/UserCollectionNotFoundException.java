package com.mtgcollectionorganizer.api.user.collection.exception;

public class UserCollectionNotFoundException extends RuntimeException{

    public UserCollectionNotFoundException(final String id){
        super(String.format("User collection not found with id %s", id));
    }

    public UserCollectionNotFoundException(final String id, final String userId){
        super(String.format("User collection not found with id %s for user %s", id, userId));
    }
}
