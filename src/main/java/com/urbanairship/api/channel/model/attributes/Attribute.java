package com.urbanairship.api.channel.model.attributes;

import com.google.common.base.Preconditions;
import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * Object that represents individual attributes.
 */
public class Attribute {
    private final AttributeAction action;
    private final String key;
    private final Optional<String> value;
    private final Optional<DateTime> timeStamp;

    private Attribute(Builder builder) {
        this.action = builder.action;
        this.key = builder.key;
        this.value = Optional.ofNullable(builder.value);
        this.timeStamp = Optional.ofNullable(builder.timeStamp);
    }

    /**
     * New atrribute builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the attribute action.
     *
     * @return AttributeAction action
     */
    public AttributeAction getAction() {
        return action;
    }

    /**
     * Get the attribute key.
     *
     * @return String key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the value for the attribute.
     *
     * @return Optional String value
     */
    public Optional<String> getValue() {
        return value;
    }

    /**
     * Get the timestamp when the attribute changed.
     *
     * @return Optional DateTime timestamp
     */
    public Optional<DateTime> getTimeStamp() {
        return timeStamp;
    }

    /**
     * Create Attribute Builder
     */
    public static class Builder {
        AttributeAction action;
        String key;
        String value;
        DateTime timeStamp;

        /**
         * Indicates that you want to set or remove an attribute on the audience.
         *
         * @param action AttributeAction
         * @return Builder
         */
        public Builder setAction(AttributeAction action) {
            this.action = action;
            return this;
        }

        /**
         * The attribute key you want to set.
         *
         * @param key String
         * @return Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the timestamp when the attribute changed. If no timestamp is provided, Airship uses the current time.
         *
         * @param timeStamp DateTime
         * @return Builder
         */
        public Builder setTimeStamp(DateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        /**
         * The value that you want to set for an attribute. Accepts strings for integer/number type attributes,
         * but your string must be convertible to a number or the request will fail.
         *
         * @param value String
         * @return Builder
         */
        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the value as a string representation of the DateTime object.
         *
         * @param value DateTime
         * @return Builder
         */
        public Builder setValue(DateTime value) {
            this.value = DateFormats.DATE_FORMATTER.print(value);
            return this;
        }

        /**
         * Sets the value as a string representation of the Integer value.
         *
         * @param value Integer
         * @return Builder
         */
        public Builder setValue(Integer value) {
            this.value = value.toString();
            return this;
        }

        public Attribute build() {
            Preconditions.checkNotNull(action, "Attribute action must be set.");
            Preconditions.checkNotNull(key, "key must be set.");

            if (action.equals(AttributeAction.SET)) {
                Preconditions.checkNotNull(value, "Value must not be null when setting attributes");
            }

            return new Attribute(this);
        }
    }
}
