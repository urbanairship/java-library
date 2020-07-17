package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Map;

public class CustomEventBody {
    private final String name;
    private final Optional<BigDecimal> value;
    private final Optional<String> transaction;
    private final Optional<String> interactionId;
    private final Optional<String> interactionType;
    private final Optional<ImmutableMap<String, CustomEventPropValue>> properties;
    private final String sessionId;

    private CustomEventBody(Builder builder) {
        this.name = builder.name;
        this.value = Optional.fromNullable(builder.value);
        this.transaction = Optional.fromNullable(builder.transaction);
        this.interactionId = Optional.fromNullable(builder.interactionId);
        this.interactionType = Optional.fromNullable(builder.interactionType);

        if (builder.properties.build().isEmpty()) {
            this.properties = Optional.absent();
        } else {
            this.properties = Optional.of(builder.properties.build());
        }

        this.sessionId = builder.sessionId;
    }

    /**
     * New CustomEventBody Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Set the name of a description of what happened. Urban Airship’s analytics systems
     * will roll up events with the same name, providing counts and total value
     * associated with the event.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value. If the event is associated with a count or amount, the value field carries
     * that information. Our system will treat this field as though it were money—mathematical
     * operations will use fixed precision representations of the field. We will respect up
     * to six digits of precision to the right of the decimal point. This field is optional;
     * if it is absent its value will default to zero.
     *
     * @return Optional BigDecimal
     */
    public Optional<BigDecimal> getValue() {
        return value;
    }

    /**
     * Get the transaction. Optional. If the event is one in a series representing a single
     * transaction, use the transaction field to tie them together.
     *
     * @return Optional String
     */
    public Optional<String> getTransaction() {
        return transaction;
    }

    /**
     * Get the interactionId. Optional. The identifier defining where the event occurred.
     * In a traditional website, this would be the path and query string portion of the URL.
     * In a single page app that uses hash routing, it would be the path, query string,
     * and fragment identifier.
     *
     * @return Optional String
     */
    public Optional<String> getInteractionId() {
        return interactionId;
    }

    /**
     * Get the interactionType. String describing the type of interaction that led to the
     * firing of the custom event, e.g., url, social, email.
     *
     * @return Optional String
     */
    public Optional<String> getInteractionType() {
        return interactionType;
    }

    /**
     * Get all property entries. Object supporting key/value pairs that describe event
     * custom properties. Events are limited to 100 properties.
     * Maximum 255 character string length.
     *
     * @return Optional ImmutableMap of Strings
     */
    public Optional<ImmutableMap<String, CustomEventPropValue>> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomEventBody that = (CustomEventBody) o;

        return Objects.equal(name, that.name) &&
                Objects.equal(value, that.value) &&
                Objects.equal(transaction, that.transaction) &&
                Objects.equal(interactionId, that.interactionId) &&
                Objects.equal(interactionType, that.interactionType) &&
                Objects.equal(properties, that.properties) &&
                Objects.equal(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value, transaction, interactionId,
                interactionType, properties, sessionId);
    }

    /**
     * Get the sessionID. The user session during which the event occurred.
     * You must supply and maintain session identifiers.
     *
     * @return String
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * CustomEventBody Builder
     */
    public static class Builder {
        private String name = null;
        private BigDecimal value = null;
        private String transaction = null;
        private String interactionId = null;
        private String interactionType = null;
        private ImmutableMap.Builder<String, CustomEventPropValue> properties = ImmutableMap.builder();
        private String sessionId = null;

        private Builder() {

        }

        /**
         * Set the name of a description of what happened. Urban Airship’s analytics systems
         * will roll up events with the same name, providing counts and total value
         * associated with the event.
         *
         * @param name String
         * @return CustomEventBody Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the value. If the event is associated with a count or amount, the value field carries
         * that information. Our system will treat this field as though it were money—mathematical
         * operations will use fixed precision representations of the field. We will respect up
         * to six digits of precision to the right of the decimal point. This field is optional;
         * if it is absent its value will default to zero.
         *
         * @param value BigDecimal
         * @return CustomEventBody Builder
         */
        public Builder setValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        /**
         * Set the transaction. Optional. If the event is one in a series representing a single
         * transaction, use the transaction field to tie them together.
         *
         * @param transaction String
         * @return CustomEventBody Builder
         */
        public Builder setTransaction(String transaction) {
            this.transaction = transaction;
            return this;
        }

        /**
         * Set the interactionId. Optional. The identifier defining where the event occurred.
         * In a traditional website, this would be the path and query string portion of the URL.
         * In a single page app that uses hash routing, it would be the path, query string,
         * and fragment identifier.
         *
         * @param interactionId String
         * @return CustomEventBody Builder
         */
        public Builder setInteractionId(String interactionId) {
            this.interactionId = interactionId;
            return this;
        }

        /**
         * Set the interactionType. String describing the type of interaction that led to the
         * firing of the custom event, e.g., url, social, email.
         *
         * @param interactionType String
         * @return CustomEventBody Builder
         */
        public Builder setInteractionType(String interactionType) {
            this.interactionType = interactionType;
            return this;
        }

        /**
         * Add a properties entry. Object supporting key/value pairs that describe event
         * custom properties. Events are limited to 100 properties.
         * Maximum 255 character string length.
         *
         * @param key String
         * @param value CustomEventPropValue
         * @return CustomEventBody Builder
         */
        public Builder addPropertiesEntry(String key, CustomEventPropValue value) {
            this.properties.put(key, value);
            return this;
        }

        /**
         * Add all property entries. Object supporting key/value pairs that describe event
         * custom properties. Events are limited to 100 properties.
         * Maximum 255 character string length.
         *
         * @param entries A Map of Strings
         * @return CustomEventBody Builder
         */
        public Builder addAllPropertyEntries(Map<String, CustomEventPropValue> entries) {
            this.properties.putAll(entries);
            return this;
        }

        /**
         * Set the sessionID. The user session during which the event occurred.
         * You must supply and maintain session identifiers.
         *
         * @param sessionId String
         * @return CustomEventBody Builder
         */
        public Builder setSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public CustomEventBody build() {
            Preconditions.checkNotNull(name, "'name' must be set");
            Preconditions.checkNotNull(sessionId, "'sessionId' must be set");

            return new CustomEventBody(this);
        }
    }
}
