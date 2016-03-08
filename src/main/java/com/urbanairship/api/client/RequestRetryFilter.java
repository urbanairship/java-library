/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.ning.http.client.filter.FilterContext;
import com.ning.http.client.filter.FilterException;
import com.ning.http.client.filter.ResponseFilter;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResponseFilter in charge of async request retries on server errors. The filter is applied before the response reaches the
 * ResponseAsyncHandler, but calls upon the handler of a given request to track the retry count.
 *
 * Due to the idempotent nature of push requests, the client will only retry a POST request if it returns a 503.
 * The maximum POST request retry limit is configured in the {@link com.urbanairship.api.client.UrbanAirshipClient} builder.
 * This defaults to 0 as the client implementer must choose to accept the risk of duplicating a push, however the mechanism
 * to configure the limit is still provided. Other requests (PUT, DELETE, GET) will retry on any 5xx. The maximum non-post
 * request retry limit is also configured in the {@link com.urbanairship.api.client.UrbanAirshipClient} builder and defaults to 10.
 * If the count is below the max retry limit, the request will be replayed with an exponential backoff. If the limit is
 * reached, a ServerException is thrown.
 */
public class RequestRetryFilter implements ResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestRetryFilter.class);
    private static final int BASE_RETRY_TIME_MS = 5;
    private final int maxRetries;
    private final int maxPostRetries;

    public RequestRetryFilter(int maxRetries, int maxPostRetries) {
        this.maxRetries = maxRetries;
        this.maxPostRetries = maxPostRetries;
    }

    @Override
    public <T> FilterContext<T> filter(FilterContext<T> ctx) throws FilterException {
        int statusCode = ctx.getResponseStatus().getStatusCode();

        if (ctx.getRequest().getMethod().equals("POST") && statusCode == 503) {
         return retry(ctx, maxPostRetries);
        }

        if (statusCode >= 500) {
            return retry(ctx, maxRetries);
        }

        return ctx;
    }

    /**
     * If a request hasn't exceeded the retry limit, this method puts the thread to sleep and then replays the request.
     *
     * @param ctx The request FilterContext.
     * @param retryLimit The request retry limit.
     * @param <T> The response type returned by the async handler.
     * @return A FilterContext instance - either the original or a duplicate set to replay the request.
     */
    private <T> FilterContext<T> retry(FilterContext<T> ctx, int retryLimit) {
        int statusCode = ctx.getResponseStatus().getStatusCode();
        if (ctx.getAsyncHandler() instanceof ResponseAsyncHandler) {
            ResponseAsyncHandler asyncHandler = (ResponseAsyncHandler) ctx.getAsyncHandler();
            if (asyncHandler.getRetryCount() < retryLimit) {
                try {
                    int sleepTime = BASE_RETRY_TIME_MS * Math.max(1, RandomUtils.nextInt(1 << (asyncHandler.getRetryCount() + 1)));
                    log.info(String.format("Request failed with status code %s - waiting for %s ms before retrying request", statusCode, sleepTime));
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                asyncHandler.incrementRetryCount();
                return new FilterContext.FilterContextBuilder<>(ctx)
                    .request(ctx.getRequest())
                    .replayRequest(true)
                    .build();
            } else {
                log.warn(String.format("Request failed with status code %s after %s attempts", statusCode, asyncHandler.getRetryCount()));
                throw new ServerException(ctx.getResponseStatus().getStatusText());
            }
        }
        return ctx;
    }
}
