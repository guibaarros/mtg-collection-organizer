package com.mtgcollectionorganizer.api.card.exception;

public class CardNotFoundException extends RuntimeException{

    public CardNotFoundException(final String setCode, final Integer collectorNumber){
        super(String.format("Card Entity not found exception; Set Code = %s; Collector Number = %s", setCode, collectorNumber));
    }
}
