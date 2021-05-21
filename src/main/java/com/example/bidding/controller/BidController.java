package com.example.bidding.controller;

import com.example.bidding.exception.IncompatibleParamException;
import com.example.bidding.model.BidRequestDTO;
import com.example.bidding.parser.RequestParamsParser;
import com.example.bidding.service.BiddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Stream;


/**
 * This is the controller class which is responsible to deal with client request
 */
@RestController
public class BidController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BidController.class);

    @Autowired
    private BiddingService biddingService;

    private final RequestParamsParser requestParamsParser = new RequestParamsParser();

    /**
     * This function deal with client auction request and return the max bidder.
     * @param id (eg: "3")
     * @param request (eg: "a=3&b=4)
     * @return String max bid
     * @throws IncompatibleParamException
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getHighestBidder(@PathVariable("id") String id, HttpServletRequest request)  {

            Map<String, String[]> parameterMap = request.getParameterMap();
            LOGGER.info("parsing the request parameters ");

            Map<String, String> attributes = Stream.of(parameterMap).map(requestParamsParser).findFirst().get();
            if(CollectionUtils.isEmpty(attributes)){
                throw new IncompatibleParamException("Incomplete parameters");
            }
            BidRequestDTO bidRequestDTO = new BidRequestDTO(Integer.parseInt(id), attributes);
            LOGGER.info("fetching the highest bid");
            return ResponseEntity.ok().body(biddingService.fetchHighestBidder(bidRequestDTO).getContent());

    }

}
