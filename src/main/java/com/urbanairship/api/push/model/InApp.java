/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;


import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Represents a single InApp message object.
 */
public class InApp {
    private final String alert;
    private final String displayType;
    private final Optional<DateTime> expiry;
    private final Optional<Display> display;
    private final Optional<Actions> actions;
    private final Optional<Interactive> interactive;
    private final Optional<ImmutableMap<String, String>> extra;

    /**
     * Generate a new InApp Builder object.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private InApp(Builder builder) {
        this.alert = builder.alert;
        this.displayType = builder.displayType;
        this.expiry = Optional.fromNullable(builder.expiry);
        this.display = Optional.fromNullable(builder.display);
        this.actions = Optional.fromNullable(builder.actions);
        this.interactive = Optional.fromNullable(builder.interactive);
        if (builder.extra.build().isEmpty()) {
            this.extra = Optional.absent();
        } else {
            this.extra = Optional.fromNullable(builder.extra.build());
        }
    }

    /**
     * Get the alert string.
     *
     * @return A string specifying the alert.
     */
    public String getAlert() {
        return alert;
    }

    /**
     * Get the display type.
     *
     * @return A string representing display type.
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     * Get the message expiry time.
     *
     * @return An optional DateTime object specifying when the message expires.
     */
    public Optional<DateTime> getExpiry() {
        return expiry;
    }

    /**
     * Get the associated display object.
     *
     * @return An optional Display object.
     */
    public Optional<Display> getDisplay() {
        return display;
    }

    /**
     * Get the associated actions payload.
     *
     * @return An optional Actions object.
     */
    public Optional<Actions> getActions() {
        return actions;
    }

    /**
     * Get the associated interactive notification payload.
     *
     * @return An optional Interactive object.
     */
    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    /**
     * Get the associated extra mapping.
     *
     * @return An optional ImmutableMap of strings.
     */
    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        return "InApp{" +
                "alert=" + alert +
                ", displayType=" + displayType +
                ", expiry=" + expiry +
                ", actions=" + actions +
                ", interactive=" + interactive +
                ", extra=" + extra +
                '}';
    }

    public static class Builder {
        private String alert = null;
        // Note: Currently "banner" is the only acceptable option.
        private String displayType = "banner";
        private DateTime expiry = null;
        private Display display = null;
        private Actions actions = null;
        private Interactive interactive = null;
        private ImmutableMap.Builder<String, String> extra = ImmutableMap.builder();

        /**
         * Set the alert string.
         *
         * @param alert A string representing the message's alert.
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the message expiry.
         *
         * @param expiry A DateTime object representing the message expiry.
         * @return Builder
         */
        public Builder setExpiry(DateTime expiry) {
            this.expiry = expiry;
            return this;
        }

        /**
         * Set the message actions payload.
         *
         * @param actions An Actions object.
         * @return Builder
         */
        public Builder setActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Set the message display parameters.
         *
         * @param display a Display object.
         * @return Builder
         */
        public Builder setDisplay(Display display) {
            this.display = display;
            return this;
        }

        /**
         * Set the message interactive payload.
         *
         * @param interactive An Interactive object.
         * @return Builder
         */
        public Builder setInteractive(Interactive interactive) {
            this.interactive = interactive;
            return this;
        }

        /**
         * Add a key-value pair to the extras mapping.
         *
         * @param key String
         * @param val String
         * @return Builder
         */
        public Builder addExtra(String key, String val) {
            this.extra.put(key, val);
            return this;
        }

        /**
         * Add multiple key-value pairs to the extras mapping.
         *
         * @param entries An ImmutableMap of strings.
         * @return Builder
         */
        public Builder addAllExtras(Map<String, String> entries) {
            this.extra.putAll(entries);
            return this;
        }

        /**
         * Build the InApp object.
         * <pre>
         * 1. alert cannot be null.
         * </pre>
         *
         * @return InApp
         */
        public InApp build() {
            Preconditions.checkArgument(alert != null, "Alert must be specified for in-app messages.");

            return new InApp(this);
        }
    }
}
