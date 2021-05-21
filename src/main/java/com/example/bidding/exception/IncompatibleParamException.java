package com.example.bidding.exception;

/**
 * This class catches exceptions occurs when request parameters are empty.
 * It made to let know, that there is no query parameters were given.
 */
public class IncompatibleParamException extends  RuntimeException {

    public IncompatibleParamException(String message){
        super(message);
    }

}
