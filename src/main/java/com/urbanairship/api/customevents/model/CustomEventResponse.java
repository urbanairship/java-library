package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.urbanairship.api.common.model.ErrorDetails;

public class CustomEventResponse {
    private final Optional<String> operationId;
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;


    public CustomEventResponse(String operationId, boolean ok, String error, ErrorDetails errorDetails) {
        this.operationId = Optional.fromNullable(operationId);
        this.ok = ok;
        this.error = Optional.fromNullable(error);
        this.errorDetails = Optional.fromNullable(errorDetails);
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     *
     * @return Operation id for this API request
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */

    public boolean isOk() {
        return ok;
    }

     /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "CustomEventResponse{" +
                "operationId=" + operationId +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomEventResponse that = (CustomEventResponse) o;

        return ok == that.ok &&
                Objects.equal(operationId, that.operationId) && 
                Objects.equal(this.error, that.error) &&
                Objects.equal(this.errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, ok, error, errorDetails);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String operationId;
        private boolean ok = false;
        private String error = null;
        private ErrorDetails errorDetails = null;

        private Builder() {

        }

        public Builder addOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public CustomEventResponse build() {
            return new CustomEventResponse(operationId, ok, error, errorDetails);
        }
    }
}
