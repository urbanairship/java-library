/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.notification.Notification;

import java.util.Objects;

/**
 * A PartialPushPayload object, used when creating an A/B test.
 */
public class PartialPushPayload {

    private final Optional<Notification> notification;
    private final Optional<PushOptions> pushOptions;
    private final Optional<InApp> inApp;

    private PartialPushPayload(Builder builder) {
        this.notification = Optional.fromNullable(builder.notification);
        this.pushOptions = Optional.fromNullable(builder.pushOptions);
        this.inApp = Optional.fromNullable(builder.inApp);
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
                ", inApp=" + inApp +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(notification, pushOptions, inApp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PartialPushPayload other = (PartialPushPayload) obj;
        return Objects.equals(this.notification, other.notification)
                && Objects.equals(this.pushOptions, other.pushOptions)
                && Objects.equals(this.inApp, other.inApp);
    }

    public static class Builder {
        private Notification notification = null;
        private PushOptions pushOptions = null;
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
         *     1. Either an in-app message or notification must be set.
         * </pre>
         *
         * @return PartialPushPayload
         */
        public PartialPushPayload build() {
            Preconditions.checkArgument(!(notification == null && inApp == null),
                    "At least one of 'notification' or 'inApp' must be set.");

            return new PartialPushPayload(this);
        }
    }
}
