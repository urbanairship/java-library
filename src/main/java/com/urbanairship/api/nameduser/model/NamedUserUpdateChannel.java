package com.urbanairship.api.nameduser.model;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * Object that represents individual NamedUserUpdateChannel.
 */
public class NamedUserUpdateChannel {
    private final Optional<NamedUserUpdateDeviceType> deviceType;
    private final Optional<String> channelId;
    private final Optional<String> emailAddress;


    private NamedUserUpdateChannel(Builder builder) {
        this.deviceType = Optional.ofNullable(builder.deviceType);
        this.channelId = Optional.ofNullable(builder.channelId);
        this.emailAddress = Optional.ofNullable(builder.emailAddress);

    }

    /**
     * New NamedUserUpdateChannel builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the NamedUserUpdateChannel device type.
     *
     * @return String deviceType
     */
    public Optional<NamedUserUpdateDeviceType> getDeviceType() {
        return deviceType;
    }

    /**
     * Get the NamedUserUpdateChannel channelId.
     *
     * @return String channelId
     */
    public Optional<String> getChannelId() {
        return channelId;
    }

    /**
     * Get the NamedUserUpdateChannel emailAddress.
     *
     * @return String emaimAddress
     */
    public Optional<String> getEmailAddress() {
        return emailAddress;
    }


    /**
     * Create NamedUserUpdateChannel Builder
     */
    public static class Builder {
        NamedUserUpdateDeviceType deviceType;
        String channelId;
        String emailAddress;

        /**
         * Indicates that add a channel.
         *
         * @param deviceType String
         * @param channelId String
         * @return Builder
         */
        public Builder addChannel(NamedUserUpdateDeviceType deviceType, String channelId) {
            this.deviceType = deviceType;
            this.channelId = channelId;
            return this;
        }

        /**
         * Indicates that add an email address.
         *
         * @param emailAddress String
         * @return Builder
         */
        public Builder addEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public NamedUserUpdateChannel build() {
            Preconditions.checkArgument(channelId != null ^ emailAddress != null, "You can only add channel OR an email address");

            return new NamedUserUpdateChannel(this);
        }
    }
}