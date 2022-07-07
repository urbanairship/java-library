/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.android;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * Represents an Android public_notification object.
 */
public class PublicNotification {
    private final Optional<String> title;
    private final Optional<String> alert;
    private final Optional<String> summary;

    private PublicNotification(Builder builder) {
        this.title = Optional.ofNullable(builder.title);
        this.alert = Optional.ofNullable(builder.alert);
        this.summary = Optional.ofNullable(builder.summary);
    }

    /**
     * Return a new PublicNotification Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the summary string.
     *
     * @return An optional summary string.
     */
    public Optional<String> getSummary() {
        return summary;
    }

    /**
     * Get the title string.
     *
     * @return An optional title string.
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get the alert string.
     *
     * @return An optional alert string.
     */
    public Optional<String> getAlert() {
        return alert;
    }

    @Override
    public String toString() {
        return "PublicNotification{" +
                "title=" + title +
                ", alert=" + alert +
                ", summary=" + summary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicNotification that = (PublicNotification) o;

        if (!alert.equals(that.alert)) return false;
        if (!summary.equals(that.summary)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + alert.hashCode();
        result = 31 * result + summary.hashCode();
        return result;
    }

    public static class Builder {
        private String summary = null;
        private String title = null;
        private String alert = null;

        /**
         * Set the summary string.
         *
         * @param summary String representing the notification summary.
         * @return Builder
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Set the title string.
         *
         * @param title String representing the notification title.
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the summary alert.
         *
         * @param alert String representing the notification alert.
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Build the PublicNotification object.
         *
         * <pre>
         *     1. At least one of summary, title, or alert must be set.
         * </pre>
         *
         * @return A PublicNotification object.
         */
        public PublicNotification build() {
            Preconditions.checkArgument(
                    this.summary != null || this.title != null || this.alert != null,
                    "At least one of summary, title, or alert must be set."
            );
            return new PublicNotification(this);
        }
    }
}
