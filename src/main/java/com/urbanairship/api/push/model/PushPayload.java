/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

/**
 * Represents a Push payload for the Urban Airship API
 */
public final class PushPayload extends PushModelObject {

    private final Selector audience;
    private final Optional<Notification> notification;
    private final Optional<RichPushMessage> message;
    private final DeviceTypeData deviceTypes;
    private final Optional<PushOptions> pushOptions;
    private final Optional<InApp> inApp;

    /**
     * PushPayload builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private PushPayload(Builder builder) {
        this.audience = builder.audience;
        this.notification = Optional.fromNullable(builder.notification);
        this.message = Optional.fromNullable(builder.message);
        this.deviceTypes = builder.deviceTypes;
        this.pushOptions = Optional.fromNullable(builder.pushOptions);
        this.inApp = Optional.fromNullable(builder.inApp);
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
     * Boolean indicating whether audience is SelectorType.ALL
     * @return audience is all
     */
    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    /**
     * Get the optional in app message.
     *
     * @return An optional InApp message object.
     */
    public Optional<InApp> getInApp() {
        return inApp;
    }

    public Optional<PushOptions> getPushOptions() {
        return pushOptions;
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
        if (pushOptions != null ? !pushOptions.equals(that.pushOptions) : that.pushOptions != null) {
            return false;
        }
        if (inApp != null ? !inApp.equals(that.inApp) : that.inApp != null) {
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
        result = 31 * result + (pushOptions != null ? pushOptions.hashCode() : 0);
        result = 31 * result + (inApp != null ? inApp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                ", message=" + message +
                ", deviceTypes=" + deviceTypes +
                ", pushOptions=" + pushOptions +
                ", inApp=" + inApp +
                '}';
    }

    public static class Builder {
        private DeviceTypeData deviceTypes = null;
        private Selector audience = null;
        private Notification notification = null;
        private RichPushMessage message = null;
        private PushOptions pushOptions = null;
        private InApp inApp = null;

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
         * Set the Notification.
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
         * Set the Device Type data.
         * @param deviceTypes DeviceTypeData
         * @return Builder
         */
        public Builder setDeviceTypes(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Set the push options.
         * @param pushOptions PushOptions
         * @return Builder
         */
        public Builder setPushOptions(PushOptions pushOptions) {
            this.pushOptions = pushOptions;
            return this;
        }

        /**
         * Set the in-app message.
         * @param inApp An InApp message object.
         * @return Builder
         */
        public Builder setInApp(InApp inApp) {
            this.inApp = inApp;
            return this;
        }

        /**
         * Build a PushPayload object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. At least one of notification, message, or inApp must be set.
         * 2. Audience must be set.
         * 3. DeviceTypes (device types) must be set.
         * </pre>
         *
         * @throws IllegalArgumentException
         * @throws NullPointerException
         * @return PushPayload
         */
        public PushPayload build() {
            Preconditions.checkArgument(!(notification == null && message == null && inApp == null),
                    "At least one of 'notification', 'message', or 'inApp' must be set.");
            Preconditions.checkNotNull(audience, "'audience' must be set");
            Preconditions.checkNotNull(deviceTypes, "'device_types' must be set");

            return new PushPayload(this);
        }
    }
}
