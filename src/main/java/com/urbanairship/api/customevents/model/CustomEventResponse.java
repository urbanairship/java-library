package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class CustomEventResponse {
    private final Optional<String> operationId;
    private final boolean ok;

    public CustomEventResponse(String operationId, boolean ok) {
        this.operationId = Optional.fromNullable(operationId);
        this.ok = ok;
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
                Objects.equal(operationId, that.operationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, ok);
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */

    public boolean isOk() {
        return ok;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String operationId;
        private boolean ok = false;

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

        public CustomEventResponse build() {
            return new CustomEventResponse(operationId, ok);
        }
    }
}
