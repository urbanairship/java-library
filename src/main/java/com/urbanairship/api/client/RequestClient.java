package com.urbanairship.api.client;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * RequestClient must be implemented to create a custom HTTP client that is then used by UrbanAirshipClient to make requests.
 */
public interface RequestClient extends Closeable {
    public <T> Future<Response> executeAsync(final Request<T> request, final ResponseCallback callback, Map<String, String> headers);
}
