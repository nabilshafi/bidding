package com.example.bidding;


import com.example.bidding.model.Bid;
import com.example.bidding.model.Problem;
import com.example.bidding.service.BiddingService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = BiddingApplication.class)
public class BidControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int port;

    @Mock
    Bid bid;

    @MockBean
    private BiddingService biddingService;

    @Before
    public void init()  {


    }

    @Test
    public void testInvalidParameters() {

        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/3");
        ResponseEntity<Problem> result = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), Problem.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody().getMessage()).isEqualTo("Incomplete parameters");

    }

    @Test
    public void testController() {

        Mockito.when(biddingService.fetchHighestBidder(Mockito.any())).thenReturn(bid);
        Mockito.when(this.bid.getContent()).thenReturn("a:750");
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/3").queryParam("a","5");
        ResponseEntity<String> result = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(result.getBody()).isEqualTo("a:750");

    }

}