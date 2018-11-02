package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Response;

public class RegisterEmailChannelResponse {

    private final boolean ok;
    private final Optional<String> channelId;

    private RegisterEmailChannelResponse() {
        this(false, null);
    }

    private RegisterEmailChannelResponse(boolean ok, String channelId) {
        this.ok = ok;
        this.channelId = Optional.fromNullable(channelId);
    }

    /**
     * New RegisterEmailChannelResponse builder
     *
     * @return Builder
     */
    public static RegisterEmailChannelResponse.Builder newBuilder() {
        return new RegisterEmailChannelResponse.Builder();
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
     * @return An Optional Sttring
     */
    public Optional<String> getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "APIListAllChannelsResponse{" +
                "ok=" + ok +
                ", channelId=" + channelId +
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
        final RegisterEmailChannelResponse other = (RegisterEmailChannelResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.channelId, other.channelId);
    }

    public static class Builder {

        private boolean ok;
        private String channelId = null;

        private Builder() {
        }

        /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public RegisterEmailChannelResponse.Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the Channel Id
         *
         * @param channelId String
         * @return Builder
         */
        public RegisterEmailChannelResponse.Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        /**
         * Build the RegisterEmailChannelResponse object
         * @return RegisterEmailChannelResponse
         */
        public RegisterEmailChannelResponse build() {
            if (ok != false) {
                Preconditions.checkArgument(channelId != null);
            }

            return new RegisterEmailChannelResponse(ok, channelId);
        }
    }
}
