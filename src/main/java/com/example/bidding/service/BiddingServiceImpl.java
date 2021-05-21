package com.example.bidding.service;

import com.example.bidding.client.HttpClientExecutor;
import com.example.bidding.exception.BidNotFoundException;
import com.example.bidding.model.Bid;
import com.example.bidding.model.BidRequestDTO;
import com.example.bidding.parser.BidResponseParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class implements the BiddingService and responsible for all business logic.
 * Fetch the bidders url.
 * Create and send the post request at bidder servers
 * Find and return the highest bid from bidders
 */
@Service
public class BiddingServiceImpl implements BiddingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingServiceImpl.class);

    private final HttpClientExecutor httpClientExecutor;
    private final Environment environment;
    private String[] bidders;
    private final ObjectMapper objectMapper;
    private final BidResponseParser bidResponseParser;


    public BiddingServiceImpl(HttpClientExecutor httpClientExecutor,
                              Environment environment) {
        this.httpClientExecutor = httpClientExecutor;
        this.environment = environment;
        this.objectMapper = new ObjectMapper();
        bidResponseParser = new BidResponseParser();
    }


    /**
     * This method send bid request to bidders through httpClient and return the maximum bid
     * @param bidRequestDTO
     * @return Bid
     * @throws BidNotFoundException
     */
    @Override
    public Bid fetchHighestBidder(BidRequestDTO bidRequestDTO)  {
        LOGGER.info("Reading bidders url from applicalion.yaml");
        bidders = Objects.requireNonNull(environment.getProperty("bidders.urls")).split(",");
        Bid maxBid = new Bid("", Integer.MIN_VALUE, "");
        for(String bidderUrl : bidders) {
            LOGGER.info("Forwarding incoming bid request to {}", bidderUrl );
            try {
                String result = httpClientExecutor.execute(createPOSTRequest(bidRequestDTO, bidderUrl.trim()));
                //bidList.add(Stream.of(result).map(bidResponseParser).findFirst().get());
                Bid bid = Stream.of(result).map(bidResponseParser).findFirst().get();
                if(bid.getBid() > maxBid.getBid()) {
                    maxBid = bid;
                }
            }
            catch (Exception e ){
                LOGGER.error("Exception occurred while dealing with bidder", e);
            }

        }
        if(maxBid.getBid() == Integer.MIN_VALUE){
            LOGGER.error("No bids exist");
            throw new BidNotFoundException("Internal server error");
        }

        LOGGER.info("Returning the max bid {}", maxBid.getBid() );
        return maxBid;
    }

    /**
     * This method create post request from given parameters
     * @param bidRequestDTO
     * @param bidUrl
     * @return request
     * @throws IOException
     */
    private Request createPOSTRequest(BidRequestDTO bidRequestDTO, String bidUrl) throws IOException {
        String requestBody = objectMapper.writeValueAsString(bidRequestDTO);
        return Request.Post(URI.create(bidUrl)).bodyString(requestBody, ContentType.APPLICATION_JSON);
    }
}
