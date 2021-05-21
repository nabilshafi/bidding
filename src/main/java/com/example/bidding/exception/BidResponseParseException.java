package com.example.bidding.exception;


/**
 * This class catches the exception ,occurs during the parsing of bidder's response.
 * It made to let know there is a problem with bidder response.
 */
public class BidResponseParseException  extends RuntimeException{

    public BidResponseParseException(Exception ex) {
        super(ex);
    }
}
