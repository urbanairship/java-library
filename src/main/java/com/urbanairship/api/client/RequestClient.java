package com.urbanairship.api.client;

import com.google.common.base.Optional;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

public interface RequestClient {
    public <T> Future<Response> executeAsync(final Request<T> request, final ResponseCallback callback, Map<String, String> headers);

    public String getAppKey();
    public Optional<String> getAppSecret();
    public Optional<String> getBearerToken();

    public void close() throws IOException;
}
