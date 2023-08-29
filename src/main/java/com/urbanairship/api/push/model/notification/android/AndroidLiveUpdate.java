/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.android;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityAlert;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Content for IOSLiveActivity.
 */
public final class AndroidLiveUpdate extends PushModelObject{
    private final Optional<Map<String, String>> contentState;
    private final Optional<Integer> dismissalDate;
    private final AndroidLiveUpdateEvent androidLiveUpdateEvent;
    private final String name;
    private final Optional<Integer> timestamp;
    private final Optional<String> type;

    private AndroidLiveUpdate(Optional<Map<String, String>> contentState,
                              Optional<Integer> dismissalDate,
                              AndroidLiveUpdateEvent androidLiveUpdateEvent,
                              String name,
                              Optional<Integer> timestamp,
                              Optional<String> type
                            ) {
        this.contentState = contentState;
        this.dismissalDate = dismissalDate;
        this.androidLiveUpdateEvent = androidLiveUpdateEvent;
        this.name = name;
        this.timestamp = timestamp;
        this.type = type;
    }

    /**
     * Get a AndroidLiveUpdate builder object.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Map<String, String>> getContentState() { return contentState; }

    public Optional<Integer> getDismissalDate() {
        return dismissalDate;
    }

    public AndroidLiveUpdateEvent getAndroidLiveUpdateEvent() {
        return androidLiveUpdateEvent;
    }

    public String getName() { return name; }

    public Optional<Integer> getTimestamp() {
        return timestamp;
    }

    public Optional<String> getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidLiveUpdate that = (AndroidLiveUpdate) o;
        return Objects.equals(contentState, that.contentState) &&
                Objects.equals(dismissalDate, that.dismissalDate) &&
                androidLiveUpdateEvent == that.androidLiveUpdateEvent && Objects.equals(name, that.name) &&
                Objects.equals(timestamp, that.timestamp) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentState, dismissalDate, androidLiveUpdateEvent, name, timestamp, type);
    }

    @Override
    public String toString() {
        return "AndroidLiveUpdate{" +
                "contentState=" + contentState +
                ", dismissalDate=" + dismissalDate +
                ", androidLiveUpdateEvent=" + androidLiveUpdateEvent +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }

    public static class Builder{
        private ImmutableMap.Builder<String, String> contentState = ImmutableMap.builder();
        private Integer dismissalDate = null;
        private AndroidLiveUpdateEvent androidLiveUpdateEvent = null;
        private String name = null;
        private Integer timestamp = null;
        private String type = null;

        private Builder() { }

        /**
         * Add a content state. You can provide additional key-value pair.
         * @param key String
         * @param value String
         * @return Builder
         */
        public Builder addContentState(String key, String value) {
            contentState.put(key, value);
            return this;
        }

        /**
         * Add all contentStates values.
         * @param contentStates Map of Strings.
         * @return Builder
         */
        public Builder addAllContentStates(Map<String, String> contentStates) {
            this.contentState.putAll(contentStates);
            return this;
        }

        /**
         * Set the dismissalDate for the AndroidLiveUpdate.
         * @param dismissalDate Integer
         * @return Builder
         */
        public Builder setDismissalDate(Integer dismissalDate) {
            this.dismissalDate = dismissalDate;
            return this;
        }

        /**
         * Set the androidLiveUpdateEvent for the AndroidLiveUpdate.
         * @param androidLiveUpdateEvent String
         * @return Builder
         */
        public Builder setAndroidLiveUpdateEvent(AndroidLiveUpdateEvent androidLiveUpdateEvent) {
            this.androidLiveUpdateEvent = androidLiveUpdateEvent;
            return this;
        }

        /**
         * Set the name for the AndroidLiveUpdate.
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the timestamp for the AndroidLiveUpdate.
         * @param timestamp Integer
         * @return Builder
         */
        public Builder setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Set the type for the AndroidLiveUpdate.
         * @param type Integer
         * @return Builder
         */
        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Build AndroidLiveUpdate
         * @return AndroidLiveUpdate
         */
        public AndroidLiveUpdate build() {
            return new AndroidLiveUpdate(
                    Optional.ofNullable(contentState.build()),
                    Optional.ofNullable(dismissalDate),
                    androidLiveUpdateEvent,
                    name,
                    Optional.ofNullable(timestamp),
                    Optional.ofNullable(type)
                    );
        }
    }
}