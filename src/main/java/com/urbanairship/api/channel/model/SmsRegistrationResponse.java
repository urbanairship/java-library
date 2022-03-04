package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.urbanairship.api.common.model.ErrorDetails;

/**
 * Sms Registration response object.
 */
public class SmsRegistrationResponse {
    private final boolean ok;
    private final Optional<String> channelId;
    private final Optional<String> status;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;
    
    private SmsRegistrationResponse(Builder builder) {
        this.ok = builder.ok;
        this.channelId = Optional.fromNullable(builder.channelId);
        this.status = Optional.fromNullable(builder.status);
        this.error = Optional.fromNullable(builder.error);
        this.errorDetails = Optional.fromNullable(builder.errorDetails);

    }

    /**
     * SmsRegistrationResponse Builder.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * If false, the request was unsuccessful.
     * @return boolean ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Unique Channel ID for the SMS channel. Included when the request contains a valid opted in value.
     * @return Optional String channelId
     */
    public Optional<String> getChannelId() {
        return channelId;
    }

    /**
     * A response with a status key indicates that the channel has not been created.
     * If the value is pending, the opted_in field was not provided in the request,
     * and the channel will be created pending the user’s affirmative response.
     * @return Optional String status
     */
    public Optional<String> getStatus() {
        return status;
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
        return "SmsRegistrationResponse{" +
                "ok=" + ok +
                ", channelId='" + channelId +
                ", status='" + status +
                ", error='" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsRegistrationResponse that = (SmsRegistrationResponse) o;
        return ok == that.ok &&
                Objects.equal(channelId, that.channelId) &&
                Objects.equal(status, that.status) &&
                Objects.equal(error, that.error) &&
                Objects.equal(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelId, status, error, errorDetails);
    }

    /**
     * SmsRegistrationResponse Builder.
     */
    public static class Builder {
        private boolean ok;
        private String channelId = null;
        private String status = null;
        private String error = null;
        private ErrorDetails errorDetails = null;

        /**
         * Set ok. If false, the request was unsuccessful.
         * @param value If the request was successful.
         * @return SmsRegistrationResponse Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the unique Channel ID for the SMS channel. Included when the request contains a valid opted_in value.
         * @param channelId The channel id created for the registered device.
         * @return SmsRegistrationResponse Builder
         */
        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        /**
         * Set the response status. A response with a status key indicates that the channel has not been created.
         * If the value is pending, the opted_in field was not provided in the request,
         * and the channel will be created pending the user’s affirmative response.
         * @param status The status of the device registration.
         * @return SmsRegistrationResponse Builder
         */
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public SmsRegistrationResponse build() {
            return new SmsRegistrationResponse(this);
        }
    }
}
