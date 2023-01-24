/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import org.apache.commons.lang.math.RandomUtils;
import org.asynchttpclient.filter.FilterContext;
import org.asynchttpclient.filter.FilterException;
import org.asynchttpclient.filter.ResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * ResponseFilter in charge of async request retries on server errors. The filter is applied before the response reaches the
 * ResponseAsyncHandler, but calls upon the handler of a given request to track the retry count.
 *
 * Due to the idempotent nature of push requests, the client will by default not retry on a POST request if it returns a 5xx.
 * If the client user decides to do so, a retry predicate may be created and passed in by the {@link UrbanAirshipClient} builder.
 * The default predicate logic allows for retries on all non-POST 5xxs. The maximum non-post request retry limit is also
 * configured in the {@link UrbanAirshipClient} builder and defaults to 10.
 * If the count is below the max retry limit and the predicate allows for a retry, the request will be replayed with an
 * exponential backoff. If the limit is reached and the predicate allows for a retry, a response is returned with the status code of the failed request.
 */
public class RequestRetryFilter implements ResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestRetryFilter.class);
    private static final int BASE_RETRY_TIME_MS = 5;
    private static final Predicate<FilterContext> DEFAULT_PREDICATE = input -> !input.getRequest().getMethod().equals("POST") && input.getResponseStatus().getStatusCode() >= 500;

    private final int maxRetries;
    private final Predicate<FilterContext> retryPredicate;

    public RequestRetryFilter(int maxRetries, Optional<Predicate<FilterContext>> retryPredicate) {
        this.maxRetries = maxRetries;
        this.retryPredicate = retryPredicate.isPresent() ? retryPredicate.get() : DEFAULT_PREDICATE;
    }

    @Override
    public <T> FilterContext<T> filter(FilterContext<T> ctx) throws FilterException {
        int statusCode = ctx.getResponseStatus().getStatusCode();
        if (ctx.getAsyncHandler() instanceof ResponseAsyncHandler) {
            ResponseAsyncHandler asyncHandler = (ResponseAsyncHandler) ctx.getAsyncHandler();
            if (asyncHandler.getRetryCount() < maxRetries && retryPredicate.test(ctx)) {
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
            }

            if (asyncHandler.getRetryCount() >= maxRetries && retryPredicate.test(ctx)) {
                log.warn(String.format("Request failed with status code %s after %s attempts", statusCode, asyncHandler.getRetryCount()));
                return ctx;
            }
        }

        return ctx;
    }
}
