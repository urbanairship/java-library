/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

import java.util.Optional;

/**
 * A PartialPushPayload object, used when creating a template push.
 */
public class PartialPushPayload {

    private final Optional<Notification> notification;
    private final Optional<PushOptions> pushOptions;
    private final Optional<RichPushMessage> richPushMessage;
    private final Optional<InApp> inApp;

    private PartialPushPayload(Builder builder) {
        this.notification = Optional.ofNullable(builder.notification);
        this.pushOptions = Optional.ofNullable(builder.pushOptions);
        this.richPushMessage = Optional.ofNullable(builder.richPushMessage);
        this.inApp = Optional.ofNullable(builder.inApp);
    }

    /**
     * Create a new PartialPushPayload Builder object.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the push notification object.
     *
     * @return An optional Notification object
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    /**
     * Get the push options object.
     *
     * @return An optional PushOptions object
     */
    public Optional<PushOptions> getPushOptions() {
        return pushOptions;
    }

    /**
     * Get the rich push message object.
     *
     * @return An optional RichPushMessage object
     */
    public Optional<RichPushMessage> getRichPushMessage() {
        return richPushMessage;
    }

    /**
     * Get the in-app message object.
     *
     * @return An optional InApp object
     */
    public Optional<InApp> getInApp() {
        return inApp;
    }

    @Override
    public String toString() {
        return "PartialPushPayload{" +
                "notification=" + notification +
                ", pushOptions=" + pushOptions +
                ", richPushMessage=" + richPushMessage +
                ", inApp=" + inApp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartialPushPayload that = (PartialPushPayload) o;

        if (!inApp.equals(that.inApp)) return false;
        if (!notification.equals(that.notification)) return false;
        if (!pushOptions.equals(that.pushOptions)) return false;
        if (!richPushMessage.equals(that.richPushMessage)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = notification.hashCode();
        result = 31 * result + pushOptions.hashCode();
        result = 31 * result + richPushMessage.hashCode();
        result = 31 * result + inApp.hashCode();
        return result;
    }

    public static class Builder {
        private Notification notification = null;
        private PushOptions pushOptions = null;
        private RichPushMessage richPushMessage = null;
        private InApp inApp = null;

        /**
         * Set the push notification.
         *
         * @param notification A Notification object
         * @return Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Set push options.
         *
         * @param pushOptions A PushOptions object
         * @return Builder
         */
        public Builder setPushOptions(PushOptions pushOptions) {
            this.pushOptions = pushOptions;
            return this;
        }

        /**
         * Set the rich push message object.
         *
         * @param richPushMessage A RichPushMessage object
         * @return Builder
         */
        public Builder setRichPushMessage(RichPushMessage richPushMessage) {
            this.richPushMessage = richPushMessage;
            return this;
        }

        /**
         * Set the in-app message object.
         *
         * @param inApp An InApp message object
         * @return Builder
         */
        public Builder setInApp(InApp inApp) {
            this.inApp = inApp;
            return this;
        }

        /**
         * Build the partial push object.
         *
         * <pre>
         *     1. Either an in-app message, rich push message, or notification must be set.
         * </pre>
         *
         * @return PartialPushPayload
         */
        public PartialPushPayload build() {
            Preconditions.checkArgument(!(notification == null && richPushMessage == null && inApp == null),
                    "At least one of 'notification', 'richPushMessage', or 'inApp' must be set.");

            return new PartialPushPayload(this);
        }
    }
}
