package com.mtgcollectionorganizer.api.card.type.builder.exception;

public class InvalidTypeNameException extends RuntimeException {
    public InvalidTypeNameException(final String message){
        super(message);
    }
}
