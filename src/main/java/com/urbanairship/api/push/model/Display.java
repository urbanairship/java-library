/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;


import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * Represents a display object.
 */
public class Display {
    private final Optional<String> primaryColor;
    private final Optional<String> secondaryColor;
    private final Optional<Integer> duration;
    private final Optional<Position> position;

    /**
     * New Display builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private Display(Builder builder) {
        this.primaryColor = Optional.ofNullable(builder.primaryColor);
        this.secondaryColor = Optional.ofNullable(builder.secondaryColor);
        this.duration = Optional.ofNullable(builder.duration);
        this.position = Optional.ofNullable(builder.position);
    }

    /**
     * Get the primary color string.
     *
     * @return An optional string representing primary color data.
     */
    public Optional<String> getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Get the secondary color string.
     *
     * @return An optional string representing secondary color data.
     */
    public Optional<String> getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * Get the message duration.
     *
     * @return An optional integer representing message duration.
     */
    public Optional<Integer> getDuration() {
        return duration;
    }

    /**
     * Get the message position.
     *
     * @return An optional string representing message position. Will be one of "top"
     * or "bottom".
     */
    public Optional<Position> getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Display{" +
                "primaryColor=" + primaryColor +
                ", secondaryColor=" + secondaryColor +
                ", duration=" + duration +
                ", position=" + position +
                '}';
    }

    public static class Builder {
        private String primaryColor = null;
        private String secondaryColor = null;
        private Integer duration = null;
        private Position position = null;

        /**
         * Set the primary color string.
         *
         * @param primaryColor A string in the API color format specifying primary color.
         * @return Builder
         */
        public Builder setPrimaryColor(String primaryColor) {
            this.primaryColor = primaryColor;
            return this;
        }

        /**
         * Set the secondary color string.
         *
         * @param secondaryColor A string in the API color format specifying secondary color.
         * @return Builder
         */
        public Builder setSecondaryColor(String secondaryColor) {
            this.secondaryColor = secondaryColor;
            return this;
        }

        /**
         * Set the message duration.
         *
         * @param duration An integer specifying message duration.
         * @return Builder
         */
        public Builder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Set the message position.
         *
         * @param position A Position object specifying message position.
         * @return Builder
         */
        public Builder setPosition(Position position) {
            this.position = position;
            return this;
        }

        /**
         * Build the Display object.
         * <pre>
         * 1. At least one of primaryColor, secondaryColor, duration, or position must not be null.
         * </pre>
         *
         * @return Display
         */
        public Display build() {
            Preconditions.checkArgument(
                    primaryColor != null || secondaryColor != null || duration != null || position != null,
                    "At least one of primaryColor, secondaryColor, duration, or position must not be null."
            );

            return new Display(this);
        }
    }
}