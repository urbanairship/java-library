package com.urbanairship.api.channel.model.email;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Objects;

/**
 * Represents the payload to be used for registering or updating an open channel.
 */
public class UninstallOpenChannel extends PushModelObject {

    private final String channelId;
    private final String deviceType;

    private UninstallOpenChannel(Builder builder) {
        this.channelId = builder.channel_id;
        this.deviceType = builder.device_type;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    /**
         * New UninstallOpenChannel builder.
         * @return Builder
         */
        public static Builder newBuilder () {
            return new Builder();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UninstallOpenChannel)) return false;
        UninstallOpenChannel that = (UninstallOpenChannel) o;
        return Objects.equals(getChannelId(), that.getChannelId()) &&
                Objects.equals(getDeviceType(), that.getDeviceType());
    }

    @Override
    public String toString() {
        return "UninstallOpenChannel{" +
                "channelId='" + channelId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChannelId(), getDeviceType());
    }

    /**
     * Create UninstallOpenChannel Builder
     */
    public final static class Builder {
        private String channel_id;
        private String device_type;

        private Builder() {}

        /**
         * Set the channelId
         * @param channelId String
         * @return RegisterEmailChannel Builder
         */
        public Builder setChannelId(String channelId) {
            this.channel_id = channelId;
            return this;
        }

        /**
         * Set the deviceType
         * @param deviceType String
         * @return RegisterEmailChannel Builder
         */
        public Builder setDeviceType(String deviceType) {
            this.device_type = deviceType;
            return this;
        }

        public UninstallOpenChannel build() {
            Preconditions.checkNotNull(channel_id, "'channel_id' cannot be null.");
            Preconditions.checkNotNull(device_type, "'device_type' cannot be null.");

            return new UninstallOpenChannel(this);
        }
    }
}



