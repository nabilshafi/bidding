package com.example.bidding.exception;

/**
 * This class catches the exception where there is no bid exist
 */
public class BidNotFoundException  extends RuntimeException {
    public BidNotFoundException(String message){
        super(message);
    }
}
