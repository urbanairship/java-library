/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * The UrbanAirshipClient class handles HTTP requests to the Urban Airship API.
 */
public class UrbanAirshipClient {

    private static final Logger log = LoggerFactory.getLogger(UrbanAirshipClient.class);

    private final RequestClient client;

    private UrbanAirshipClient(Builder builder) {
        this.client = builder.client;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public <T> Future<Response> execute(final Request<T> request, ResponseCallback callback) {
        return client.executeAsync(request, callback);
    }

    /**
     * Close the underlying HTTP client's thread pool.
     */
    public void close() throws IOException {
        log.info("Closing client");
        client.close();
    }

    /**
     * Get the app key.
     * @return The app key.
     */
    public String getAppKey() {
        return client.getAppKey();
    }

    /**
     * Get the app secret.
     * @return The app secret.
     */
    public Optional<String> getAppSecret() {
        return client.getAppSecret();
    }

    /**
     * Get the bearer token.
     * @return The bearer token.
     */
    public Optional<String> getBearerToken() {
        return client.getBearerToken();
    }

    /**
     * Get the request client.
     * @return The RequestClient.
     */
    public RequestClient getRequestClient() {
        return client;
    }

    /* Builder for newAPIClient */

    public static class Builder {

        private String key;
        private String secret;
        private String bearerToken;
        private RequestClient client;

        /**
         * Set the app key.
         * @param key String app key
         * @return Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the app secret.
         * @param appSecret String app secret
         * @return Builder
         */
        public Builder setSecret(String appSecret) {
            this.secret = appSecret;
            return this;
        }

        /**
         * Set the bearer token.
         * @param bearerToken String bearer token
         * @return Builder
         */
        public Builder setBearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
            return this;
        }

        /**
         * Set a custom client.
         * @param client RequestClient client
         * @return Builder
         */
        public Builder setClient(RequestClient client) {
            this.client = client;
            return this;
        }


        /**
         * Build an UrbanAirshipClient object.  Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. App key must be set.
         * 2. App secret must be set.
         * 3. The base URI has been overridden but not set.
         * 4. Max for non-POST 5xx retries must be set, already defaults to 10.
         * 5. HTTP client config builder must be set, already defaults to a new builder.
         * </pre>
         *
         * @return UrbanAirshipClient
         */
        public UrbanAirshipClient build() {
            if(client == null) {
                if (secret == null && bearerToken == null) {
                    throw new NullPointerException("secret or the bearer token must be set");
                }

                client = AsyncRequestClient.newBuilder()
                        .setKey(key)
                        .setSecret(secret)
                        .setBearerToken(bearerToken)
                        .build();
            }

            return new UrbanAirshipClient(this);
        }
    }
}
