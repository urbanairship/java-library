/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */


package com.urbanairship.api.client;

import java.util.Optional;

/**
 * Exception thrown when the client fails to connect to the API on 5xxs.
 */
public class ServerException extends RuntimeException {
    private final String statusText;
    private final Integer statusCode;
    private final Optional<RequestError> error;

    private ServerException(String statusText,
                            Integer statusCode,
                            Optional<RequestError> error) {
        super(statusText);
        this.statusText = statusText;
        this.statusCode = statusCode;
        this.error = error;
    }

    /**
     * New Builder for a ServerException instance.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the reason phrase from the API response status line.
     *
     * @return HTTP status statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Get the raw HTTP Status Code from the API response.
     *
     * @return HTTP status Code
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Get the RequestError for this exception. This is optional.
     *
     * @return RequestError
     */
    public Optional<RequestError> getError() {
        return error;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nServerException:");
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

    public static class Builder {
        private Integer statusCode;
        private String statusText;
        private RequestError requestError = null;

        /**
         * Set the error statusText.
         *
         * @param statusText The statusText.
         * @return Builder
         */
        public Builder setStatusText(String statusText) {
            this.statusText = statusText;
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
        public ServerException build() {
            return new ServerException(statusText,
                    statusCode,
                    Optional.ofNullable(requestError));
        }
    }
}
