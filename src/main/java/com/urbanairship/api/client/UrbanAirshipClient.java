/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The UrbanAirshipClient class handles HTTP requests to the Urban Airship API.
 */
public class UrbanAirshipClient implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    private final RequestClient client;
    private final String key;
    private final Optional<String> secret;
    private final Optional<String> bearerToken;

    private String userAgent;

    public static final String EU_URI = "https://go.airship.eu";

    private UrbanAirshipClient(Builder builder) {
        this.client = builder.client;
        userAgent = getUserAgent();
        this.key = builder.key;
        this.secret = Optional.fromNullable(builder.secret);
        this.bearerToken = Optional.fromNullable(builder.bearerToken);
    }

    /**
     * New UrbanAirshipClient Builder.
     *
     * @return UrbanAirshipClient Builder.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public <T> Future<Response> executeAsync(final Request<T> request, ResponseCallback callback) {
        return client.executeAsync(request, callback, createHeaders(request));
    }

    public <T> Future<Response> executeAsync(Request<T> request) {
        return executeAsync(request, null);
    }

    public <T> Response execute(Request<T> request) throws IOException {
        return execute(request, null);
    }

    public <T> Response execute(Request<T> request, ResponseCallback callback) throws IOException {
        try {
            return executeAsync(request, callback).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while retrieving response from future", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to retrieve response from future", e);
        }
    }

    private Map<String, String> createHeaders(Request request) {
        Map<String, String> headers = new HashMap<>();

        headers.put("User-Agent", userAgent);
        Map<String, String> requestHeaders = request.getRequestHeaders();
        if (requestHeaders != null) {
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                headers.put(entry.getKey(), entry.getValue());
            }
        }

        String auth;

        if (request.bearerTokenAuthRequired()) {
            Preconditions.checkArgument(bearerToken.isPresent(), "Bearer token required for request: " + request);
            auth = "Bearer " + getBearerToken().get();
        } else if (getAppSecret().isPresent()) {
            auth = "Basic " + BaseEncoding.base64().encode((getAppKey() + ":" + getAppSecret().get()).getBytes());
        } else if (bearerToken.isPresent() && request.canUseBearerTokenAuth()){
            auth = "Bearer " + getBearerToken().get();
        } else {
            throw new IllegalArgumentException("Bearer token auth not supported for this request, app secret must be set for request: " + request);
        }

        headers.put("Authorization", auth);
        headers.put("X-UA-Appkey", getAppKey());

        return headers;
    }

    /**
     * Retrieve the client user agent.
     *
     * @return The user agent.
     */
    @VisibleForTesting
    public String getUserAgent() {
        String userAgent = "UNKNOWN";
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream != null) {
            Properties props = new Properties();
            try {
                props.load(stream);
                stream.close();
                userAgent = "UAJavaLib/" + props.get("client.version") + " " + getAppKey();
            } catch (IOException e) {
                log.error("Failed to retrieve client user agent due to IOException - setting to \"UNKNOWN\"", e);
            }
        }
        return userAgent;
    }

    /**
     * Close the underlying HTTP client's thread pool.
     */
    @Override
    public void close() throws IOException {
        log.info("Closing client");
        client.close();
    }

    /**
     * Get the app key.
     * 
     * @return The app key.
     */
    public String getAppKey() {
        return key;
    }

    /**
     * Get the app secret.
     * 
     * @return The app secret.
     */
    public Optional<String> getAppSecret() {
        return secret;
    }

    /**
     * Get the bearer token.
     * 
     * @return The bearer token.
     */
    public Optional<String> getBearerToken() {
        return bearerToken;
    }

    /**
     * Get the request client.
     * 
     * @return The RequestClient.
     */
    public RequestClient getRequestClient() {
        return client;
    }

    /**
     * UrbanAirshipClient builder.
     */
    public static class Builder {

        private String key;
        private String secret;
        private String bearerToken;
        private String baseUri = "https://go.urbanairship.com";
        private RequestClient client;

        /**
         * Set the app key.
         * 
         * @param key String app key
         * @return Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the app secret.
         * 
         * @param appSecret String app secret
         * @return Builder
         */
        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        /**
         * Changes base URI. Without changing URI, it defaults to
         * https://go.urbanairship.com. To send to European Servers use
         * UrbanAirshipClient.EU_URI
         * 
         * @param baseUri String baseUri used for requests
         * @return Builder
         */
        public Builder setBaseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        /**
         * Set the bearer token.
         * 
         * @param bearerToken String bearer token
         * @return Builder
         */
        public Builder setBearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
            return this;
        }

        /**
         * Set a custom client.
         * 
         * @param client RequestClient client
         * @return Builder
         */
        public Builder setClient(RequestClient client) {
            this.client = client;
            return this;
        }

        /**
         * Build an UrbanAirshipClient object. Will fail if any of the following
         * preconditions are not met.
         * 
         * <pre>
         * 1. App key or client must be set.
         * 2. App secret or bearer token must be set if no client is provided.
         * 3. The base URI has been overridden but not set.
         * 4. Max for non-POST 5xx retries must be set, already defaults to 10 when using the default client.
         * 5. HTTP client config builder must be set in the default client, already defaults to a new builder.
         * </pre>
         *
         * @return UrbanAirshipClient
         */
        public UrbanAirshipClient build() {
            Preconditions.checkNotNull(key, "app key needed to build APIClient");
            if (secret == null && bearerToken == null) {
                throw new NullPointerException("secret or the bearer token must be set");
            }

            if (client == null) {
                client = AsyncRequestClient.newBuilder().setBaseUri(baseUri).build();
            }

            return new UrbanAirshipClient(this);
        }
    }
}
