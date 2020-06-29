package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.Campaigns;
import org.joda.time.DateTime;

public class CustomEventUser {

    private final CustomEventChannelType channelType;
    private final String channel;
    private final String namedUser;
    private final String occurred;

    private CustomEventUser(Builder builder) {
        this.channelType = builder.channelType;
        this.channel = builder.channel;
        this.namedUser = builder.namedUser;
        this.occurred = builder.occurred;
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
     * Get the Urban Airship channel identifier for the user who triggered the event.
     *
     * @return String

     */
    public String getChannel() {
        return channel;
    }

    public String getNamedUser() {
        return namedUser;
    }

    public String getOccurred() {
        return occurred;
    }

    /**
     * CustomEventUser Builder
     */
    public static class Builder {
        private String channel = null;
        private CustomEventChannelType channelType = null;
        private String namedUser;
        private String occurred;

        /**
         * Set the Urban Airship channel identifier for the user who triggered the event.
         *
         * @param channel String
         * @return CustomEventUser Builder
         */
        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         * Set the Urban Airship namedUser identifier for the user who triggered the event.
         *
         * @param namedUser String
         * @return CustomEventUser Builder
         */
        public Builder setNamedUser(String namedUser) {
            this.namedUser = namedUser;
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
            Preconditions.checkNotNull(channel, "Must contain either 'channel' or 'namedUser'");

            return new CustomEventUser(this);
        }
    }
}
