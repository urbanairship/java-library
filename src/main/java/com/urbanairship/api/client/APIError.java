/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.google.common.base.Optional;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

/**
 * Error object for API requests.
 * This is populated the as many details as are possible at the time of the
 * error. Optional values from Google Guava are used in place of values
 * that may not be present.
 */
public final class APIError {

    /* Header keys, values */
    private final static String CONTENT_TYPE_KEY = "Content-type";
    private final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json";

    private final Boolean ok;
    private final Optional<String> operationId;
    private final String error;
    private final Optional<Number> errorCode;
    private final Optional<APIErrorDetails> details;

    private APIError(Boolean ok, Optional<String> operationId, String error, Optional<Number> errorCode,
                     Optional<APIErrorDetails> details) {
        this.ok = ok;
        this.operationId = operationId;
        if (error == null || error.isEmpty()) {
            throw new IllegalArgumentException("Error cannot be null or empty");
        }
        this.error = error;
        this.errorCode = errorCode;
        this.details = details;
    }

    /**
     * Create an APIError from the response if it conforms to the API v3
     * Currently, three types of error bodies are returned, text/html strings,
     * basic JSON errors {"error":"message"} and the v3 error spec. This method
     * parses between three, and returns a best effort response.
     *
     * @param response HttpResponse for the request that caused the exception
     * @return APIError
     * @throws IOException
     */
    public static APIError errorFromResponse(HttpResponse response) throws IOException {

        HeaderElement[] headerElements = response.getFirstHeader(CONTENT_TYPE_KEY)
                .getElements();
        String contentType = headerElements[0].getName();

        // Text/html
        if (contentType.equalsIgnoreCase(CONTENT_TYPE_TEXT_HTML)) {
            return nonJSONError(response);
        }

        // JSON but not v3
        else if (contentType.equalsIgnoreCase(CONTENT_TYPE_JSON)) {
            return nonV3JSONError(response);
        }

        // v3 JSON parsing
        else if (contentType.equalsIgnoreCase(UA_APPLICATION_JSON)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = APIResponseObjectMapper.getInstance();
            return mapper.readValue(responseBody, APIError.class);
        }

        // wut?
        else {
            return APIError.newBuilder()
                    .setError("Unknown response parsing error")
                    .build();
        }
    }

    /*
    Currently sending text/plain errors for some requests, currently 404's
    do this. API-291, 12JUL13
     */
    @Deprecated
    private static APIError nonJSONError(HttpResponse response) throws
            IOException {
        String responseBody = EntityUtils.toString(response.getEntity());
        EntityUtils.consumeQuietly(response.getEntity());
        return APIError.newBuilder()
                .setError(responseBody)
                .build();
    }

    /*
    Currently returning JSON, but not API v3 JSON for some requests.
    API-281, 12JUL13
     */
    @Deprecated
    private static APIError nonV3JSONError(HttpResponse response) throws
            IOException {
        String responseBody = EntityUtils.toString(response.getEntity());
        EntityUtils.consume(response.getEntity());
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        Map<String, String> errorMsg =
                mapper.readValue(responseBody,
                        new TypeReference<Map<String, String>>() {
                        });

        return APIError.newBuilder()
                .setError(errorMsg.get("message"))
                .build();
    }

    /**
     * Returns a APIError Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public Boolean getOk() {
        return ok;
    }

    /**
     * Returns the operation id for the error. This value is useful for debugging
     * errors within the Urban Airship system, and should be sent to UA support
     * in cases where there is an issue. This value may be absent.
     *
     * @return Optional Operation ID
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * Returns a description of the error
     *
     * @return Optional error description.
     */
    public String getError() {
        return error;
    }

    /**
     * Returns an error code specific to the Urban Airship. This value may
     * be absent.
     *
     * @return Optional error code
     */
    public Optional<Number> getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the details of this error if error details are available from the
     * API. Currently errors from requests that are syntactically valid but
     * otherwise malformed, (missing fields, incompatible parameters, etc) should
     * return details to help identify the issue.
     *
     * @return Optional details object
     */
    public Optional<APIErrorDetails> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ok");
        stringBuilder.append(getOk());
        stringBuilder.append("APIError:");
        stringBuilder.append(getError());
        stringBuilder.append("\nCode:");
        stringBuilder.append(getErrorCode());
        if (details.isPresent()) {
            stringBuilder.append("\nDetails:");
            stringBuilder.append(details.get().toString());
        }
        return stringBuilder.toString();
    }

    /**
     * Builds an APIError.
     */
    public static class Builder {

        private Boolean ok;
        private String operationId;
        private String error;
        private Number errorCode;
        private APIErrorDetails details;

        public Builder setOk(Boolean ok) {
            this.ok = ok;
            return this;
        }

        /**
         * Set the operation id. This is optional
         *
         * @param operationId Operation id
         * @return This Builder
         */
        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        /**
         * Set a description for the error. This is required.
         *
         * @param error Human readable error description
         * @return This Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setErrorCode(Number errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder setDetails(APIErrorDetails details) {
            this.details = details;
            return this;
        }

        public APIError build() {
            return new APIError(ok,
                    Optional.fromNullable(operationId),
                    error,
                    Optional.fromNullable(errorCode),
                    Optional.fromNullable(details));
        }
    }
}
