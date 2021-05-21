package com.example.bidding.model;


/**
 * This class is the representation of bids in the form of POJO, which is the response from bidders.
 */
public class Bid {

    private final String id;
    private final int bid;
    private final String content;

    public Bid(String id, int bid, String content) {
        this.id = id;
        this.bid = bid;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public int getBid() {
        return bid;
    }

    public String getContent() {
        return content;
    }

}
