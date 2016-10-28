/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Map;

/**
 * Response encapsulates relevant data about responses from the
 * Urban Airship API. This is the object returned for successful API requests.
 * It contains an optional parsed API response body object with
 * the details of the Urban Airship response specifically, as well as the
 * API response headers and status code.
 */
public class Response<T> {

    private final Optional<T> body;
    private final ImmutableMap<String, Collection<String>> headers;
    private final int status;

    /**
     * Default constructor.
     *
     * @param body Optional parsed api response body.
     * @param headers Response headers.
     * @param status Response status.
     */
    Response(T body, Map<String, Collection<String>> headers, int status) {
        this.body = Optional.fromNullable(body);
        this.headers = ImmutableMap.copyOf(headers);
        this.status = status;
    }

    /**
     * Gets the parsed request response body.
     *
     * @return Parsed request response body.
     */
    public Optional<T> getBody() {
        return body;
    }

    /**
     * Gets the response headers.
     *
     * @return Map of response headers.
     */
    public ImmutableMap<String, Collection<String>> getHeaders() {
        return headers;
    }

    /**
     * Gets the response status code.
     *
     * @return Response status code.
     */
    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;

        Response response = (Response) o;

        if (status != response.status) return false;
        if (!body.equals(response.body)) return false;
        if (!headers.equals(response.headers)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = body.hashCode();
        result = 31 * result + headers.hashCode();
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
            "body=" + body +
            ", headers=" + headers +
            ", status=" + status +
            '}';
    }

    public static class Builder<T> {
        private T body = null;
        private Map<String, Collection<String>> headers;
        private int status;

        /**
         * Set the response body.
         *
         * @param body The response body of type T.
         * @return Builder
         */
        public Builder setBody(T body) {
            this.body = body;
            return this;
        }

        /**
         * Set the response headers.
         *
         * @param headers The response headers as a map.
         * @return Builder
         */
        public Builder setHeaders(Map<String, Collection<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the response status code.
         *
         * @param status The status code.
         * @return Builder
         */
        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        /**
         * Build the Response instance.
         * @return The response instance.
         */
        public Response build() {
            return new Response<>(body, headers, status);
        }
    }
}
