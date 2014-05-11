/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Represents a Push payload for the Urban Airship API
 */
public final class PushPayload extends PushModelObject {

    private final Selector audience;
    private final Optional<Notification> notification;
    private final Optional<RichPushMessage> message;
    private final DeviceTypeData deviceTypes;
    private final Optional<Options> options;

    /**
     * PushPayload builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private PushPayload(Selector audience,
                        Optional<Notification> notification,
                        Optional<RichPushMessage> message,
                        DeviceTypeData deviceTypes,
                        Optional<Options> options) {
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.deviceTypes = deviceTypes;
        this.options = options;
    }

    /**
     * Get the audience
     * @return audience
     */
    public Selector getAudience() {
        return audience;
    }

    /**
     * Get the Notification. This is optional.
     * @return Optional<<T>Notification</T>>
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    /**
     * Get the rich push message. This is optional
     * @return Optional<<T>RichPushMessage</T>>
     */
    public Optional<RichPushMessage> getMessage() {
        return message;
    }

    /**
     * Get the deviceTypes
     * @return DeviceTypeData
     */
    public DeviceTypeData getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Get the Options. This is optional.
     * @return Optional<<T>Options</T>>
     */
    public Optional<Options> getOptions() {
        return options;
    }

    /**
     * Boolean indicating whether audience is SelectorType.ALL
     * @return audience is all
     */
    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushPayload that = (PushPayload) o;

        if (audience != null ? !audience.equals(that.audience) : that.audience != null) {
            return false;
        }
        if (notification != null ? !notification.equals(that.notification) : that.notification != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (deviceTypes != null ? !deviceTypes.equals(that.deviceTypes) : that.deviceTypes != null) {
            return false;
        }
        if (options != null ? !options.equals(that.options) : that.options != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (deviceTypes != null ? deviceTypes.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
            "audience=" + audience +
            ", notification=" + notification +
            ", message=" + message +
            ", deviceTypes=" + deviceTypes +
            ", options=" + options +
            '}';
    }

    public static class Builder {
        private DeviceTypeData deviceTypes = null;
        private Selector audience = null;
        private Notification notification = null;
        private RichPushMessage message = null;
        private Options options = null;

        private Builder() { }

        /**
         * Set the Audience.
         * @param value audience Selector
         * @return Builder
         */
        public Builder setAudience(Selector value) {
            this.audience = value;
            return this;
        }

        /**
         * Set the Notification
         * @param notification Notification
         * @return Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Set the rich push message.
         * @param message RichPushMessage
         * @return Builder
         */
        public Builder setMessage(RichPushMessage message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Device Type data
         * @param deviceTypes DeviceTypeData
         * @return Builder
         */
        public Builder setDeviceTypes(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Set the Options
         * @param options Options
         * @return Builder
         */
        public Builder setOptions(Options options) {
            this.options = options;
            return this;
        }

        /**
         * Build a PushPayload object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. At least one of notification or message must be set.
         * 2. Audience must be set.
         * 3. DeviceTypes (device types) must be set.
         * </pre>
         *
         * @throws IllegalArgumentException
         * @throws NullPointerException
         * @return PushPayload
         */
        public PushPayload build() {
            Preconditions.checkArgument(!(notification == null && message == null),
                                        "At least one of 'notification' or 'message' must be set.");
            Preconditions.checkNotNull(audience, "'audience' must be set");
            Preconditions.checkNotNull(deviceTypes, "'device_types' must be set");

            return new PushPayload(audience,
                                   Optional.fromNullable(notification),
                                   Optional.fromNullable(message),
                                   deviceTypes,
                                   Optional.fromNullable(options));
        }
    }
}
