/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.urbanairship.api.client.parse.RequestErrorObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;

import java.io.IOException;

/**
 * Error object for API requests.
 * This is populated the as many details as are possible at the time of the
 * error. Optional values from Google Guava are used in place of values
 * that may not be present.
 */
public final class RequestError {

    /* Header keys, values */
    private final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String UA_APPLICATION_JSON = "application/vnd.urbanairship+json";
    final static String UA_APPLICATION_JSON_V3 = "application/vnd.urbanairship+json;version=3";

    private final boolean ok;
    private final Optional<String> operationId;
    private final String error;
    private final Optional<Number> errorCode;
    private final Optional<RequestErrorDetails> details;

    private RequestError(boolean ok, Optional<String> operationId, String error, Optional<Number> errorCode,
                         Optional<RequestErrorDetails> details) {
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
     * @param body Response body for the request that caused the exception
     * @param contentType String
     * @return APIError
     * @throws IOException if it fails reading the error
     */
    public static RequestError errorFromResponse(String body, String contentType) throws IOException {
        contentType = contentType.replace(" ", "");

        // Text/html
        if (contentType.equalsIgnoreCase(CONTENT_TYPE_TEXT_HTML)) {
            return nonJSONError(body);
        }

        // JSON but not v3
        else if (contentType.equalsIgnoreCase(CONTENT_TYPE_JSON)) {
            return nonV3JSONError(body);
        }

        // v3 JSON parsing
        else if (contentType.equalsIgnoreCase(UA_APPLICATION_JSON) || contentType.equalsIgnoreCase(UA_APPLICATION_JSON_V3)) {
            try {
                ObjectMapper mapper = RequestErrorObjectMapper.getInstance();
                return mapper.readValue(body, RequestError.class);
            } catch (APIParsingException e) {
                //Templates API returns a v3 Content-Type header, but does not conform to the standard error type
                return nonV3JSONError(body);
            }
        }

        // wut?
        else {
            return RequestError.newBuilder()
                    .setError("Unknown response parsing error")
                    .build();
        }
    }

    /*
    Currently sending text/plain errors for some requests, currently 404's
    do this. API-291, 12JUL13
     */
    @Deprecated
    private static RequestError nonJSONError(String body) throws
            IOException {
        return RequestError.newBuilder()
                .setError(body)
                .build();
    }

    /*
    Currently returning JSON, but not API v3 JSON for some requests.
    API-281, 12JUL13
     */
    @Deprecated
    private static RequestError nonV3JSONError(String body) throws IOException {
        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();
        JsonNode error = mapper.readTree(body);

        String errorMessage = extractErrorMessage(error);

        return RequestError.newBuilder()
                .setError(errorMessage)
                .build();
    }

    private static String extractErrorMessage(JsonNode error) {
        java.util.Optional<String> topLevelMessage = getTopLevelMessage(error);
        java.util.Optional<String> detailMessage = getDetailMessage(error);
        java.util.Optional<String> detailArrayMessage = getDetailArrayMessage(error);

        String errorMessage;
        if (topLevelMessage.isPresent()) {
            errorMessage = topLevelMessage.get();
        } else if (detailMessage.isPresent()) {
            errorMessage = detailMessage.get();
        } else if (detailArrayMessage.isPresent()) {
            errorMessage = detailArrayMessage.get();
        } else {
            errorMessage = "Unknown response parsing error";
        }

        return errorMessage;
    }

    private static java.util.Optional<String> getTopLevelMessage(JsonNode error) {
        JsonNode message = error.get("message");

        if (message != null) {
            return java.util.Optional.of(message.asText());
        } else {
            return java.util.Optional.empty();
        }
    }

    private static java.util.Optional<String> getDetailMessage(JsonNode error) {
        JsonNode details = error.get("details");
        if (details != null && !details.isArray()) {
            JsonNode message = details.get("message");
            if (message != null) {
                return java.util.Optional.of(message.asText());
            }
        }

        return java.util.Optional.empty();
    }

    private static java.util.Optional<String> getDetailArrayMessage(JsonNode error) {
        JsonNode details = error.get("details");
        if (details != null && details.isArray()) {
            JsonNode firstDetail = details.get(0);
            if (firstDetail != null) {
                JsonNode detailMessage = firstDetail.get("message");
                if (detailMessage != null) {
                    return java.util.Optional.of(detailMessage.asText());
                }
            }
        }

        return java.util.Optional.empty();
    }

    /**
     * Returns a APIError Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
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
    public Optional<RequestErrorDetails> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ok:");
        stringBuilder.append(getOk());
        stringBuilder.append("\nRequestError:");
        stringBuilder.append(getError());
        if (errorCode.isPresent()) {
            stringBuilder.append("\nCode:");
            stringBuilder.append(getErrorCode());
        }
        if (details.isPresent()) {
            stringBuilder.append("\nDetails:");
            stringBuilder.append(details.get().toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestError)) return false;

        RequestError that = (RequestError) o;

        if (ok != that.ok) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (operationId != null ? !operationId.equals(that.operationId) : that.operationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (ok ? 1 : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    /**
     * Builds an APIError.
     */
    public static class Builder {

        private boolean ok;
        private String operationId;
        private String error;
        private Number errorCode;
        private RequestErrorDetails details;

        public Builder setOk(boolean ok) {
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

        /**
         * Set the error code.
         *
         * @param errorCode The error code.
         * @return Build
         */
        public Builder setErrorCode(Number errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * Set the error details.
         *
         * @param details The RequestErrorDetails.
         * @return Builder
         */
        public Builder setDetails(RequestErrorDetails details) {
            this.details = details;
            return this;
        }

        /**
         * Build the RequestError instance.
         *
         * @return The RequestError instance.
         */
        public RequestError build() {
            return new RequestError(ok,
                    Optional.fromNullable(operationId),
                    error,
                    Optional.fromNullable(errorCode),
                    Optional.fromNullable(details));
        }
    }
}
