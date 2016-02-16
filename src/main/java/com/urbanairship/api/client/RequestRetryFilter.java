/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.ning.http.client.filter.FilterContext;
import com.ning.http.client.filter.FilterException;
import com.ning.http.client.filter.ResponseFilter;
import org.apache.commons.lang.math.RandomUtils;

/**
 * ResponseFilter in charge of async request retries on 5xxs. The filter is applied before the response reaches the
 * ResponseAsyncHandler, but calls upon the handler of a given request to track the retry count. If the count is below the max retry limit,
 * the request will be replayed with an exponential backoff. If the limit is reached, a ServerException is thrown.
 */
public class RequestRetryFilter implements ResponseFilter {

    private static final int BASE_RETRY_TIME_MS = 5;
    private final int maxRetries;

    public RequestRetryFilter(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public <T> FilterContext<T> filter(FilterContext<T> ctx) throws FilterException {
        if (ctx.getResponseStatus().getStatusCode() >= 500) {
            if (ctx.getAsyncHandler() instanceof ResponseAsyncHandler) {
                ResponseAsyncHandler asyncHandler = (ResponseAsyncHandler) ctx.getAsyncHandler();
                if (asyncHandler.getRetryCount() < maxRetries) {
                    try {
                        Thread.sleep(BASE_RETRY_TIME_MS * Math.max(1, RandomUtils.nextInt(1 << (asyncHandler.getRetryCount() + 1))));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    asyncHandler.incrementRetryCount();
                    return new FilterContext.FilterContextBuilder<>(ctx)
                        .request(ctx.getRequest())
                        .replayRequest(true)
                        .build();
                } else {
                    throw new ServerException(ctx.getResponseStatus().getStatusText());
                }
            }
        }

        return ctx;
    }
}
