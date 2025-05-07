/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Content for IOSLiveActivity.
 */
public final class IOSLiveActivity extends PushModelObject{
    private final Optional<IOSLiveActivityAlert> iosLiveActivityAlert;
    private final Optional<Map<String, JsonNode>> contentState;
    private final Optional<Integer> dismissalDate;
    private final IOSLiveActivityEvent iosLiveActivityEvent;
    private final String name;
    private final Optional<Integer> priority;
    private final Optional<Double> relevanceScore;
    private final Optional<Integer> staleDate;
    private final Optional<Integer> timestamp;
    private final Optional<String> attributesType;
    private final Optional<Map<String, String>> attributes;

    private IOSLiveActivity(Optional<IOSLiveActivityAlert> iosLiveActivityAlert,
                            Optional<Map<String, JsonNode>> contentState,
                            Optional<Integer> dismissalDate,
                            IOSLiveActivityEvent iosLiveActivityEvent,
                            String name,
                            Optional<Integer> priority,
                            Optional<Double> relevanceScore,
                            Optional<Integer> staleDate,
                            Optional<Integer> timestamp,
                            Optional<String> attributesType,
                            Optional<Map<String, String>> attributes
                            ) {
        this.iosLiveActivityAlert = iosLiveActivityAlert;
        this.contentState = contentState;
        this.dismissalDate = dismissalDate;
        this.iosLiveActivityEvent = iosLiveActivityEvent;
        this.name = name;
        this.priority = priority;
        this.relevanceScore = relevanceScore;
        this.staleDate = staleDate;
        this.timestamp = timestamp;
        this.attributesType = attributesType;
        this.attributes = attributes;
    }

    /**
     * Get a IOSLiveActivity builder object.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<IOSLiveActivityAlert> getIosLiveActivityAlert() {
        return iosLiveActivityAlert;
    }

    public Optional<Map<String, JsonNode>> getContentState() { return contentState; }

    public Optional<Integer> getDismissalDate() {
        return dismissalDate;
    }

    public IOSLiveActivityEvent getIosLiveActivityEvent() {
        return iosLiveActivityEvent;
    }

    public String getName() { return name; }

    public Optional<Integer> getPriority() {
        return priority;
    }

    public Optional<Double> getRelevanceScore() {
        return relevanceScore;
    }

    public Optional<Integer> getStaleDate() {
        return staleDate;
    }

    public Optional<Integer> getTimestamp() {
        return timestamp;
    }

    public Optional<String> getAttributesType() {
        return attributesType;
    }

    public Optional<Map<String, String>> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSLiveActivity that = (IOSLiveActivity) o;
        return Objects.equals(iosLiveActivityAlert, that.iosLiveActivityAlert) &&
                Objects.equals(contentState, that.contentState) &&
                Objects.equals(dismissalDate, that.dismissalDate) &&
                iosLiveActivityEvent == that.iosLiveActivityEvent && Objects.equals(name, that.name) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(relevanceScore, that.relevanceScore) &&
                Objects.equals(staleDate, that.staleDate) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(attributesType, that.attributesType)
                && Objects.equals(attributes, that.attributes);

    }

    @Override
    public int hashCode() {
        return Objects.hash(iosLiveActivityAlert, contentState, dismissalDate, iosLiveActivityEvent, name, priority,
                relevanceScore, staleDate, timestamp, attributesType, attributes);
    }

    @Override
    public String toString() {
        return "IOSLiveActivity{" +
                "iosLiveActivityAlert=" + iosLiveActivityAlert +
                ", contentState=" + contentState +
                ", dismissalDate=" + dismissalDate +
                ", iosLiveActivityEvent=" + iosLiveActivityEvent +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", relevanceScore=" + relevanceScore +
                ", staleDate=" + staleDate +
                ", timestamp=" + timestamp +
                ", attributesType=" + attributesType +
                ", attributes=" + attributes +
                '}';
    }

    public static class Builder{
        private IOSLiveActivityAlert iosLiveActivityAlert = null;
        private ImmutableMap.Builder<String, JsonNode> contentState = ImmutableMap.builder();
        private Integer dismissalDate = null;
        private IOSLiveActivityEvent iosLiveActivityEvent = null;
        private String name = null;
        private Integer priority = null;
        private Double relevanceScore = null;
        private Integer staleDate = null;
        private Integer timestamp = null;
        private String attributesType = null;
        private ImmutableMap.Builder<String, String> attributes = ImmutableMap.builder();
        private static final ObjectMapper MAPPER = new ObjectMapper();

        private Builder() { }

        /**
         * Set the iosLiveActivityAlert for the IOSLiveActivity.
         * @param iosLiveActivityAlert String
         * @return Builder
         */
        public Builder setIosLiveActivityAlert(IOSLiveActivityAlert iosLiveActivityAlert) {
            this.iosLiveActivityAlert = iosLiveActivityAlert;
            return this;
        }

        /**
         * Add a content state. You can provide additional key-value pair.
         * @param key String
         * @param value Object
         * @return Builder
         */
        public Builder addContentState(String key, Object value) {
            JsonNode node = MAPPER.valueToTree(value);
            contentState.put(key, node);
            return this;
        }

        /**
         * Add all contentStates values.
         * @param contentStates Map of Strings.
         * @return Builder
         */
        public Builder addAllContentStates(Map<String, ?> contentStates) {
            contentStates.forEach((k, v) ->
                    contentState.put(k, MAPPER.valueToTree(v))
            );
            return this;
        }

        /**
         * Set the dismissalDate for the IOSLiveActivity.
         * @param dismissalDate Integer
         * @return Builder
         */
        public Builder setDismissalDate(Integer dismissalDate) {
            this.dismissalDate = dismissalDate;
            return this;
        }

        /**
         * Set the iosLiveActivityEvent for the IOSLiveActivity.
         * @param iosLiveActivityEvent String
         * @return Builder
         */
        public Builder setIosLiveActivityEvent(IOSLiveActivityEvent iosLiveActivityEvent) {
            this.iosLiveActivityEvent = iosLiveActivityEvent;
            return this;
        }

        /**
         * Set the name for the IOSLiveActivity.
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the priority for the IOSLiveActivity.
         * @param priority String
         * @return Builder
         */
        public Builder setPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Set the relevanceScore for the IOSLiveActivity.
         * @param relevanceScore Double
         * @return Builder
         */
        public Builder setRelevanceScore(Double relevanceScore) {
            this.relevanceScore = relevanceScore;
            return this;
        }

        /**
         * Set the staleDate for the IOSLiveActivity.
         * @param staleDate Integer
         * @return Builder
         */
        public Builder setStaleDate(Integer staleDate) {
            this.staleDate = staleDate;
            return this;
        }

        /**
         * Set the timestamp for the IOSLiveActivity.
         * @param timestamp Integer
         * @return Builder
         */
        public Builder setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Set the attributesType for the IOSLiveActivity.
         * @param attributesType String
         * @return Builder
         */
        public Builder setAttributesType(String attributesType) {
            this.attributesType = attributesType;
            return this;
        }

        /**
         * Add an attribute. You can provide additional key-value pair.
         * @param key String
         * @param value String
         * @return Builder
         */
        public Builder addAttribute(String key, String value) {
            attributes.put(key, value);
            return this;
        }

        /**
         * Add all attributes values.
         * @param attributes Map of Strings.
         * @return Builder
         */
        public Builder addAllAttributes(Map<String, String> attributes) {
            this.attributes.putAll(attributes);
            return this;
        }



        /**
         * Build IOSLiveActivity
         * @return IOSLiveActivity
         */
        public IOSLiveActivity build() {

            if (iosLiveActivityEvent == IOSLiveActivityEvent.START) {
                Preconditions.checkArgument(attributesType != null && !attributes.build().isEmpty(), "attributesType and attributes are required for event START.");
            }
            return new IOSLiveActivity(
                    Optional.ofNullable(iosLiveActivityAlert),
                    Optional.of(contentState.build()),
                    Optional.ofNullable(dismissalDate),
                    iosLiveActivityEvent,
                    name,
                    Optional.ofNullable(priority),
                    Optional.ofNullable(relevanceScore),
                    Optional.ofNullable(staleDate),
                    Optional.ofNullable(timestamp),
                    Optional.ofNullable(attributesType),
                    Optional.of(attributes.build())
                    );
        }
    }
}