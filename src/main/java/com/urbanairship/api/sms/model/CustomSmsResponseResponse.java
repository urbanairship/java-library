package com.urbanairship.api.sms.model;

import com.google.common.base.Objects;

import java.util.Optional;

public final class CustomSmsResponseResponse {

    private final boolean ok;
    private final Optional<String> pushId;
    private final Optional<String> operationId;

    private CustomSmsResponseResponse(boolean ok, String pushId, String operationId) {
        this.ok = ok;
        this.pushId = Optional.ofNullable(pushId);
        this.operationId = Optional.ofNullable(operationId);
    }

    /**
     * New CustomSmsResponseResponse builder
     *
     * @return Builder
     */
    public static CustomSmsResponseResponse.Builder newBuilder() {
        return new CustomSmsResponseResponse.Builder();
    }

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the push Id if present
     *
     * @return An Optional Sttring
     */
    public Optional<String> getPushId() {
        return pushId;
    }

    /**
     * Get the operation Id if present
     *
     * @return An Optional Sttring
     */
    public Optional<String> getOperationId() {
        return operationId;
    }


    @Override
    public String toString() {
        return "CustomSmsResponseResponse{" +
                "ok=" + ok +
                ", pushId=" + pushId +
                ", operationId=" + operationId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, pushId, operationId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CustomSmsResponseResponse other = (CustomSmsResponseResponse) obj;
        return Objects.equal(this.ok, other.ok) && 
            Objects.equal(this.pushId, other.pushId) && 
            Objects.equal(this.operationId, other.operationId);
    }

    public static class Builder {

        private boolean ok;
        private String pushId = null;
        private String operationId = null;

        private Builder() {
        }

        /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public CustomSmsResponseResponse.Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the pushId
         *
         * @param pushId String
         * @return Builder
         */
        public CustomSmsResponseResponse.Builder setPushId(String pushId) {
            this.pushId = pushId;
            return this;
        }

        /**
         * Set the operationId
         *
         * @param operationId String
         * @return Builder
         */
        public CustomSmsResponseResponse.Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }


        /**
         * Build the CustomSmsResponseResponse object
         * @return CustomSmsResponseResponse
         */
        public CustomSmsResponseResponse build() {

            return new CustomSmsResponseResponse(ok, pushId, operationId);
        }
    }
}
