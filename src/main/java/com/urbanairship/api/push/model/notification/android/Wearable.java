/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.android;


import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.Interactive;

import java.util.List;

/**
 * Represents an DeviceStats wearable object.
 */
public class Wearable {
    private final Optional<String> backgroundImage;
    private final Optional<Interactive> interactive;
    private final Optional<ImmutableList<ImmutableMap<String, String>>> extraPages;

    /**
     * Return a new Wearable Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private Wearable(Builder builder) {
        this.backgroundImage = Optional.fromNullable(builder.backgroundImage);
        this.interactive = Optional.fromNullable(builder.interactive);
        if (!builder.extraPages.build().isEmpty()) {
            this.extraPages = Optional.of(builder.extraPages.build());
        } else {
            this.extraPages = Optional.absent();
        }
    }

    /**
     * Get the background image URL.
     *
     * @return An optional string representing the background image URL.
     */
    public Optional<String> getBackgroundImage() {
        return this.backgroundImage;
    }

    /**
     * Get the associated interactive payload.
     *
     * @return An optional Interactive object.
     */
    public Optional<Interactive> getInteractive() {
        return this.interactive;
    }

    /**
     * Get any associated extra pages.
     *
     * @return An optional set of maps.
     */
    public Optional<ImmutableList<ImmutableMap<String, String>>> getExtraPages() {
        return this.extraPages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Wearable that = (Wearable)obj;
        if (backgroundImage != null ? !backgroundImage.equals(that.backgroundImage) : that.backgroundImage != null) {
            return false;
        }
        if (interactive != null ? !interactive.equals(that.interactive) : that.interactive != null) {
            return false;
        }
        if (extraPages != null ? !extraPages.equals(that.extraPages) : that.extraPages != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (backgroundImage != null ? backgroundImage.hashCode() : 0);
        result = 31 * result + (interactive != null ? interactive.hashCode() : 0);
        result = 31 * result + (extraPages != null ? extraPages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Wearable{" +
                "backgroundImage=" + backgroundImage +
                ", interactive=" + interactive +
                ", extraPages=" + extraPages +
                '}';
    }


    public static class Builder {
        private String backgroundImage = null;
        private Interactive interactive = null;
        private ImmutableList.Builder<ImmutableMap<String, String>> extraPages = ImmutableList.builder();

        private final static String TITLE = "title";
        private final static String ALERT = "alert";

        /**
         * Set the background image URL.
         *
         * @param value A string specifying the URL.
         * @return Builder
         */
        public Builder setBackgroundImage(String value) {
            this.backgroundImage = value;
            return this;
        }

        /**
         * Set the interactive payload.
         *
         * @param value An Interactive object.
         * @return Builder
         */
        public Builder setInteractive(Interactive value) {
            this.interactive = value;
            return this;
        }

        /**
         * Add an extra page to the wearable body.
         *
         * @param title String representing the page title.
         * @param alert String representing the page alert.
         * @return Builder
         */
        public Builder addExtraPage(String title, String alert) {
            ImmutableMap<String, String> page = ImmutableMap.<String, String>builder()
                    .put(TITLE, title)
                    .put(ALERT, alert)
                    .build();

            this.extraPages.add(page);
            return this;
        }

        /**
         * Add multiple extra pages to the extraPage list.
         *
         * @param extraPages An ImmutableMap of strings.
         * @return Builder
         */
        public Builder addAllExtraPages(List<ImmutableMap<String, String>> extraPages) {
            this.extraPages.addAll(extraPages);
            return this;
        }

        /**
         * Build the Wearable object.
         * <pre>
         * 1. At least one of backgroundImage, interactive, or extraPages must not be null.
         * </pre>
         *
         * @return Wearable
         */
        public Wearable build() {
            Preconditions.checkArgument(
                    backgroundImage != null || interactive != null || !extraPages.build().isEmpty(),
                    "At least one of backgroundImage, interactive, or extraPages must not be null/empty."
            );

            return new Wearable(this);
        }
    }
}
