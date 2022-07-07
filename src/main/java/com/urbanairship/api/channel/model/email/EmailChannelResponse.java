package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;


public final class EmailChannelResponse {

    private final boolean ok;
    private final Optional<String> channelId;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;


    private EmailChannelResponse(Builder builder) {
        this.ok = builder.ok;
        this.channelId = Optional.ofNullable(builder.channelId);
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
    }

    /**
     * New EmailChannelResponse builder
     *
     * @return Builder
     */
    public static EmailChannelResponse.Builder newBuilder() {
        return new EmailChannelResponse.Builder();
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
     * Get the channel Id if present
     *
     * @return An Optional String
     */
    public Optional<String> getChannelId() {
        return channelId;
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
        return "EmailChannelResponse{" +
                "ok=" + ok +
                ", channelId=" + channelId +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final EmailChannelResponse other = (EmailChannelResponse) obj;
        return Objects.equal(this.ok, other.ok) && 
            Objects.equal(this.channelId, other.channelId) &&
            Objects.equal(this.error, other.error) &&
            Objects.equal(this.errorDetails, other.errorDetails);
    }

    public static class Builder {

        private boolean ok;
        private String channelId = null;
        private String error = null;
        private ErrorDetails errorDetails = null;


        private Builder() {
        }

        /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public EmailChannelResponse.Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the Channel Id
         *
         * @param channelId String
         * @return Builder
         */
        public EmailChannelResponse.Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public EmailChannelResponse.Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public EmailChannelResponse.Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }


        /**
         * Build the EmailChannelResponse object
         * @return EmailChannelResponse
         */
        public EmailChannelResponse build() {

            return new EmailChannelResponse(this);
        }
    }
}
