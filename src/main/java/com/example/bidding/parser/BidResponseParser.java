package com.example.bidding.parser;

import com.example.bidding.exception.BidResponseParseException;
import com.example.bidding.model.Bid;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * This class parse the incoming bid response parameters to Bid object and replace the $price$ tag with the price of bid.
 */
public class BidResponseParser implements Function<String, Bid> {

    @Override
    public Bid apply(String responseBody) {
        try {
            JSONObject obj = new JSONObject(responseBody);
            String id = obj.getString("id");
            int bid = obj.getInt("bid");
            String content = obj.getString("content").replace("$price$", String.valueOf(bid));
            return new Bid(id, bid, content);
        } catch (Exception ex) {
            throw new BidResponseParseException(ex);
        }

    }
}
