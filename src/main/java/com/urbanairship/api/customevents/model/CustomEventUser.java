package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import java.util.Optional;
import com.google.common.base.Preconditions;

public class CustomEventUser {

    private final Optional<CustomEventChannelType> channelType;
    private final Optional<String> channel;
    private final Optional<String> namedUserId;

    private CustomEventUser(Builder builder) {
        this.channelType = Optional.ofNullable(builder.channelType);
        this.channel = Optional.ofNullable(builder.channel);
        this.namedUserId = Optional.ofNullable(builder.namedUserId);
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
    public Optional<CustomEventChannelType> getChannelType() {
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
    public Optional<String> getChannel() {
        return channel;
    }

    /**
     * Get the Airship named user identifier for the named user who triggered the event.
     *
     * @return String

     */
    public Optional<String> getNamedUserId() {
        return namedUserId;
    }

    /**
     * CustomEventUser Builder
     */
    public static class Builder {
        private String channel;
        private CustomEventChannelType channelType;
        private String namedUserId;

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
         * Set the Airship named user identifier for the named user who triggered the event.
         *
         * @param namedUserId String
         * @return CustomEventUser Builder
         */
        public Builder setNamedUserId(String namedUserId) {
            this.namedUserId = namedUserId;
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
            Preconditions.checkArgument(((channelType != null && channel != null) || namedUserId != null),
                    "Must provide channel and channelType, or namedUserId");
            Preconditions.checkArgument((!(channelType != null && channel != null && namedUserId != null)),
                    "Must provide either channel and channelType or namedUserId, not both");

            return new CustomEventUser(this);
        }
    }
}
