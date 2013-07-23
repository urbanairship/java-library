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
    private final PlatformData platforms;

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
                        PlatformData platforms) {
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.platforms = platforms;
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
     * Get the platforms
     * @return PlatformData
     */
    public PlatformData getPlatforms() {
        return platforms;
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
        if (platforms != null ? !platforms.equals(that.platforms) : that.platforms != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (platforms != null ? platforms.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
            "audience=" + audience +
            ", notification=" + notification +
            ", message=" + message +
            ", platforms=" + platforms +
            '}';
    }

    public static class Builder {
        private PlatformData platforms = null;
        private Selector audience = null;
        private Notification notification = null;
        private RichPushMessage message = null;

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
         * Set the platform data
         * @param platforms PlatformData
         * @return Builder
         */
        public Builder setPlatforms(PlatformData platforms) {
            this.platforms = platforms;
            return this;
        }

        /**
         * Build a PushPayload object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. At least one of notification or message must be set.
         * 2. Audience must be set.
         * 3. Platforms (device types) must be set.
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
            Preconditions.checkNotNull(platforms, "'device_types' must be set");

            return new PushPayload(audience,
                                   Optional.fromNullable(notification),
                                   Optional.fromNullable(message),
                                   platforms);
        }
    }
}
