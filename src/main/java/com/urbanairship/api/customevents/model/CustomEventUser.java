package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class CustomEventUser {

    private final CustomEventChannelType channelType;
    private final String channel;

    private CustomEventUser(Builder builder) {
        this.channelType = builder.channelType;
        this.channel = builder.channel;
    }

    /**
     * New CustomEventUser Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the channel type.
     *
     * @return CustomEventChannelType
     */
    public CustomEventChannelType getChannelType() {
        return channelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomEventUser that = (CustomEventUser) o;

        return channelType == that.channelType &&
                Objects.equal(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelType, channel);
    }

    /**
     * Get the Airship channel identifier for the user who triggered the event.
     *
     * @return String

     */
    public String getChannel() {
        return channel;
    }

    /**
     * CustomEventUser Builder
     */
    public static class Builder {
        private String channel = null;
        private CustomEventChannelType channelType = null;

        /**
         * Set the Airship channel identifier for the user who triggered the event.
         *
         * @param channel String
         * @return CustomEventUser Builder
         */
        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         * Set the channel type.
         *
         * @param channelType CustomEventChannelType
         * @return CustomEventUser Builder
         */
        public Builder setCustomEventChannelType(CustomEventChannelType channelType) {
            this.channelType = channelType;
            return this;
        }

        public CustomEventUser build() {
            Preconditions.checkNotNull(channelType, "'channelType' must not be null");
            Preconditions.checkNotNull(channel, "'channel' must not be null");

            return new CustomEventUser(this);
        }
    }
}
