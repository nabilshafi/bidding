package com.example.bidding.model;

import java.io.Serializable;
import java.util.Map;


/**
 * This class represent as BidRequestDTO which is the POJO form of incoming request parameters.
 */
public class BidRequestDTO implements Serializable {

    private final Integer id;
    private final Map<String, String> attributes;

    public BidRequestDTO(Integer id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public Integer getId() {
        return id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
