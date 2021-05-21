package com.example.bidding;

import com.example.bidding.client.HttpClientExecutor;
import com.example.bidding.model.Bid;
import com.example.bidding.model.BidRequestDTO;
import com.example.bidding.service.BiddingServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BiddingServiceTest {


    @Mock
    private Environment environment;

    @Mock
    private HttpClientExecutor httpClientExecutor;

    private BidRequestDTO bidRequestDTO;

    @InjectMocks
    private BiddingServiceImpl biddingService;

    @Before
    public void init()  {

        ReflectionTestUtils.setField(biddingService, "bidderUrls", "foo,jjj");

        bidRequestDTO = new BidRequestDTO(Integer.parseInt("1"), Mockito.anyMap());

    }
    @Test
    public void testFetchHighestBidder() throws IOException {

        String str = "{\"id\":\"3\",\"bid\":\"700\",\"content\":\"c:$price$\"}";
        String dummyServer = "localhost:any";

        Mockito.when(this.httpClientExecutor.execute(Mockito.any())).thenReturn(str);
        Mockito.when(this.environment.getProperty(Mockito.anyString())).thenReturn(dummyServer);

        Bid resultBid = biddingService.fetchHighestBidder(bidRequestDTO);

        assertThat(resultBid.getBid()).isEqualTo(700);

    }

    @Test
    public void testHighestBidder() throws IOException {

        String ser1 = "{\"id\":\"3\",\"bid\":\"700\",\"content\":\"c:$price$\"}";
        String ser2 = "{\"id\":\"3\",\"bid\":\"500\",\"content\":\"b:$price$\"}";
        String ser3 = "{\"id\":\"3\",\"bid\":\"100\",\"content\":\"a:$price$\"}";
        String dummyServer = "localhost:1,localhost:2,localhost:3";

        Mockito.when(this.httpClientExecutor.execute(Mockito.any())).thenReturn(ser1,ser2,ser3);
        Mockito.when(this.environment.getProperty(Mockito.anyString())).thenReturn(dummyServer);

        Bid resultBid = biddingService.fetchHighestBidder(bidRequestDTO);

        assertThat(resultBid.getBid()).isEqualTo(700);

    }

    @Test
    public void testNoBids() throws IOException {

        String dummyServer = "localhost:any";

        Mockito.when(this.httpClientExecutor.execute(Mockito.any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Mockito.when(this.environment.getProperty(Mockito.anyString())).thenReturn(dummyServer);

        try {
            Bid resultBid = biddingService.fetchHighestBidder(bidRequestDTO);

        }catch (Throwable e){
            assertThat(e.getMessage()).isEqualTo("Internal server error");
        }
    }



}
