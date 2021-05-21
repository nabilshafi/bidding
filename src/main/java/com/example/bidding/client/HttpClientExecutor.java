package com.example.bidding.client;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class HttpClientExecutor {
    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;
    private static final int MAX_CONN_TOTAL = 100;

    private final Executor executor;

    public HttpClientExecutor () {
        this(DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public HttpClientExecutor(int timeout) {
        executor = Executor.newInstance(HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(timeout))
                        .setConnectionRequestTimeout((int) TimeUnit.SECONDS.toMillis(timeout))
                        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(timeout))
                        .build())
                .setMaxConnTotal(MAX_CONN_TOTAL)
                .build());
    }

    /**
     * This method executes the Request based on the given URL and return the result.
     * e.g Request.Get(<URL>)
     *
     * @param request
     * @return String
     * @throws IOException
     */
    public String execute(Request request) throws IOException {
        return EntityUtils.toString(executor.execute(request).returnResponse().getEntity());
    }
}
