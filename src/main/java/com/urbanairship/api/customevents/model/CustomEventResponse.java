package com.urbanairship.api.customevents.model;

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
