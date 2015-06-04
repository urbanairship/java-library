/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Exception class for client side API interactions
 */
public class APIRequestException extends RuntimeException {

    private final HttpResponse httpResponse;
    private final Optional<APIError> error;

    private APIRequestException(String message,
                                HttpResponse httpResponse,
                                Optional<APIError> error,
                                Throwable cause) {
        super(message, cause);
        this.httpResponse = httpResponse;
        this.error = error;
    }

    /**
     * New Builder for an APIRequestException
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Create an APIRequestException from the given response. Will attempt to
     * create an APIError that represents the underlying issue.
     *
     * @param response The HttpResponse that caused the exception
     * @return APIRequestException
     * @throws IOException
     */
    public static APIRequestException exceptionForResponse(
            HttpResponse response) throws IOException {

        APIError apiError = APIError.errorFromResponse(response);
        return APIRequestException.newBuilder()
                .setMessage(response.getStatusLine().getReasonPhrase())
                .setApiError(apiError)
                .setHttpResponse(response)
                .build();
    }

    /**
     * Get the raw Apache HttpResponse for this exception
     *
     * @return HttpResponse
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * Get the APIError for this exception. This is optional.
     *
     * @return APIError
     */
    public Optional<APIError> getError() {
        return error;
    }

    /**
     * Get the raw HTTP Status Code from the HttpResponse
     *
     * @return HTTP Status Code
     */
    public int httpResponseStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    /**
     * Get the reason phrase from the HttpResponse StatusLine
     *
     * @return Status message for the HttpResponse
     */
    public String httpResponseStatusMessage() {
        return httpResponse.getStatusLine().getReasonPhrase();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nAPIRequestException:");
        stringBuilder.append(String.format("\nMessage:%s", getMessage()));
        stringBuilder.append(String.format("\nHttpResponse:%s", httpResponse.toString()));
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
     * APIRequestException Builder
     */
    public static class Builder {
        private String message;
        private HttpResponse httpResponse;
        private APIError apiError;
        private Throwable cause;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setHttpResponse(HttpResponse httpResponse) {
            this.httpResponse = httpResponse;
            return this;
        }

        public Builder setApiError(APIError apiError) {
            this.apiError = apiError;
            return this;
        }

        public Builder setCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public APIRequestException build() {
            return new APIRequestException(message,
                    httpResponse,
                    Optional.fromNullable(apiError),
                    cause);
        }
    }
}
