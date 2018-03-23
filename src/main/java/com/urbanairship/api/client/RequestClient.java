package com.urbanairship.api.client;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * RequestClient must be implemented to create a custom HTTP client that is then used by UrbanAirshipClient to make requests.
 */
public interface RequestClient extends Closeable {
    /**
     * Executes the HTTP request.
     * @param request the Request.
     * @param callback the ResponseCallback.
     * @param headers a Map of the headers.
     * @param <T> type
     * @return The Future Response.
     */
    public <T> Future<Response> executeAsync(final Request<T> request, final ResponseCallback callback, Map<String, String> headers);
}
