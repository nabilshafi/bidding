package com.example.bidding.service;

import com.example.bidding.model.Bid;
import com.example.bidding.model.BidRequestDTO;

import java.io.IOException;

public interface BiddingService {

    Bid fetchHighestBidder(BidRequestDTO bidRequestDTO);
}
