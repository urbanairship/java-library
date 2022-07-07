/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.notification.Notification;

import java.util.Optional;

/**
 * A VariantPushPayload object, used when creating an A/B test. A partial push notification object
 * represents a Push payload, excepting the audience and device_types fields because they are defined in the
 * experiment object. Message Center messages are not supported by the Experiments API so they are also not
 * included in the partial push payload object.
 */
public class VariantPushPayload {

    private final Optional<Notification> notification;
    private final Optional<PushOptions> pushOptions;
    private final Optional<InApp> inApp;

    private VariantPushPayload(Builder builder) {
        this.notification = Optional.ofNullable(builder.notification);
        this.pushOptions = Optional.ofNullable(builder.pushOptions);
        this.inApp = Optional.ofNullable(builder.inApp);
    }

    /**
     * Create a new VariantPushPayload Builder object.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the push notification object.
     * @return An optional Notification object
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    /**
     * Get the push options object. Specifies delivery options for the push.
     * @return An optional PushOptions object
     */
    public Optional<PushOptions> getPushOptions() {
        return pushOptions;
    }

    /**
     * Get the in-app message object.
     * @return An optional InApp object
     */
    public Optional<InApp> getInApp() {
        return inApp;
    }

    @Override
    public String toString() {
        return "VariantPushPayload{" +
                "notification=" + notification +
                ", pushOptions=" + pushOptions +
                ", inApp=" + inApp +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(notification, pushOptions, inApp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final VariantPushPayload other = (VariantPushPayload) obj;
        return Objects.equal(this.notification, other.notification)
                && Objects.equal(this.pushOptions, other.pushOptions)
                && Objects.equal(this.inApp, other.inApp);
    }

    /**
     * VariantPushPayload Builder
     */
    public static class Builder {
        private Notification notification = null;
        private PushOptions pushOptions = null;
        private InApp inApp = null;

        /**
         * Set the push notification.
         * @param notification A Notification object
         * @return Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Set push options. A place to specify delivery options for the push.
         * @param pushOptions A PushOptions object
         * @return Builder
         */
        public Builder setPushOptions(PushOptions pushOptions) {
            this.pushOptions = pushOptions;
            return this;
        }

        /**
         * Set the in-app message object.
         * @param inApp An InApp message object
         * @return Builder
         */
        public Builder setInApp(InApp inApp) {
            this.inApp = inApp;
            return this;
        }

        /**
         * Build the variant push object.
         * <pre>
         *     1. Either an in-app message or notification must be set.
         * </pre>
         *
         * @return VariantPushPayload
         */
        public VariantPushPayload build() {
            Preconditions.checkArgument(!(notification == null && inApp == null),
                    "At least one of 'notification' or 'inApp' must be set.");

            return new VariantPushPayload(this);
        }
    }
}
