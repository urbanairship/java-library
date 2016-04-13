/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;

/**
 * Exception class for client side API interactions
 */
public class ClientException extends RuntimeException {

    private final Optional<RequestError> error;
    private final Integer statusCode;
    private final String statusText;

    private ClientException(String message,
                            Integer statusCode,
                            Optional<RequestError> error) {
        super(message);
        this.statusCode = statusCode;
        this.statusText = message;
        this.error = error;
    }

    /**
     * New Builder for a ClientException instance.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the RequestError for this exception. This is optional.
     *
     * @return RequestError
     */
    public Optional<RequestError> getError() {
        return error;
    }

    /**
     * Get the raw HTTP Status Code from the API response.
     *
     * @return HTTP status Code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get the reason phrase from the API response status line.
     *
     * @return HTTP status message
     */
    public String getStatusText() {
        return statusText;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nAPIRequestException:");
        stringBuilder.append(String.format("\nMessage:%s", getMessage()));
        stringBuilder.append(String.format("\nStatusCode:%s", statusCode));
        stringBuilder.append(String.format("\nStatusText:%s", statusText));
        if (error.isPresent()) {
            stringBuilder.append(String.format("\nError:%s", error.get()));
        }
        if (getCause() != null) {
            stringBuilder.append(String.format("\nCause:%s",
                    getCause().getMessage()));
        }
        return stringBuilder.toString();
    }

    /**
     * ClientException Builder
     */
    public static class Builder {
        private Integer statusCode;
        private String message;
        private RequestError requestError = null;

        /**
         * Set the error message.
         *
         * @param message The message.
         * @return Builder
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        /**
         * Set the response status code.
         *
         * @param statusCode The status code.
         * @return Builder
         */
        public Builder setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Set the exception RequestError.
         *
         * @param requestError The request error.
         * @return Builder
         */
        public Builder setRequestError(RequestError requestError) {
            this.requestError = requestError;
            return this;
        }

        /**
         * Build the ClientException instance.
         *
         * @return ClientException
         */
        public ClientException build() {
            return new ClientException(message,
                    statusCode,
                    Optional.fromNullable(requestError));
        }
    }
}
