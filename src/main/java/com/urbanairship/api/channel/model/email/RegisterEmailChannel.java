package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the payload to be used for registering or updating an email
 * channel.
 */
public class RegisterEmailChannel extends PushModelObject {
    private final ChannelType type;
    private final Optional<Map<OptInLevel, String>> emailOptInLevel;
    private final String address;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;
    private final Optional<OptInMode> emailOptInMode;
    private final Optional<Map<String, String>> properties;
    private final Optional<Map<TrackingOptInLevel, String>> trackingOptInLevel;
    private final Optional<Map<String, List<String>>> tags;
    private final Optional<Map<String, String>> attributes;

    // Protected to facilitate subclassing for create and send child object
    protected RegisterEmailChannel(Builder builder) {
        this.type = ChannelType.EMAIL;
        this.emailOptInLevel = Optional.ofNullable((builder.emailOptInLevel));
        this.address = builder.address;
        this.timezone = Optional.ofNullable(builder.timezone);
        this.localeCountry = Optional.ofNullable(builder.localeCountry);
        this.localeLanguage = Optional.ofNullable(builder.localeLanguage);
        this.emailOptInMode = Optional.ofNullable((builder.emailOptInMode));
        this.properties = Optional.of(Collections.unmodifiableMap(builder.properties)).filter(map -> !map.isEmpty());
        this.trackingOptInLevel = Optional.of(Collections.unmodifiableMap(builder.trackingOptInLevel))
                .filter(map -> !map.isEmpty());
        this.tags = Optional.of(Collections.unmodifiableMap(builder.tags)).filter(map -> !map.isEmpty());
        this.attributes = Optional.of(Collections.unmodifiableMap(builder.attributes)).filter(map -> !map.isEmpty());
    }

    /**
     * Get the RegisterEmailChannelType.
     *
     * @return ChannelType type
     */
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }

    /**
     * Get the channel email opt in level.
     *
     * @return Optional OptInLevel emailOptInLevel
     */
    public Optional<Map<OptInLevel, String>> getEmailOptInLevel() {
        return emailOptInLevel;
    }

    /**
     * Get the email channel's address,
     *
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get a String representation of the timezone.
     *
     * @return Optional String timezone
     */
    public Optional<String> getTimezone() {
        return timezone;
    }

    /**
     * Get a String representation of the locale country.
     *
     * @return Optional String localeCountry
     */
    public Optional<String> getLocaleCountry() {
        return localeCountry;
    }

    /**
     * Get a String representation of the locale language.
     *
     * @return Optional String localeLanguage
     */
    public Optional<String> getLocaleLanguage() {
        return localeLanguage;
    }

    /**
     * Get the channel email opt in mode.
     *
     * @return Optional OptInMode emailOptInMode
     */
    public Optional<OptInMode> getEmailOptInMode() {
        return emailOptInMode;
    }

    /**
     * Get properties for the channel email.
     * 
     * @return Optional Map of Strings
     */
    public Optional<Map<String, String>> getProperties() {
        return properties;
    }

    /**
     * Get the channel tracking opt in/out timestamps for click/open.
     *
     * @return Optional Map of TrackingOptInLevel to timestamp string
     */
    public Optional<Map<TrackingOptInLevel, String>> getTrackingOptInLevel() {
        return trackingOptInLevel;
    }

    /**
     * Get grouped tags to be applied at registration time.
     *
     * @return Optional Map of group name to list of tag strings
     */
    public Optional<Map<String, List<String>>> getTags() {
        return tags;
    }

    /**
     * Get attributes to be applied at registration time.
     *
     * @return Optional Map of attribute key to string value
     */
    public Optional<Map<String, String>> getAttributes() {
        return attributes;
    }

    /**
     * New RegisterEmailChannel builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegisterEmailChannel that = (RegisterEmailChannel) o;
        return type == that.type &&
                Objects.equal(address, that.address) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage) &&
                Objects.equal(emailOptInLevel, that.emailOptInLevel) &&
                Objects.equal(emailOptInMode, that.emailOptInMode) &&
                Objects.equal(properties, that.properties) &&
                Objects.equal(trackingOptInLevel, that.trackingOptInLevel) &&
                Objects.equal(tags, that.tags) &&
                Objects.equal(attributes, that.attributes);
    }

    @Override
    public String toString() {
        return "RegisterEmailChannel{" +
                "type=" + type +
                ", emailOptInLevel=" + emailOptInLevel +
                ", address=" + address +
                ", timezone=" + timezone +
                ", localeCountry=" + localeCountry +
                ", localeLanguage=" + localeLanguage +
                ", emailOptInMode=" + emailOptInMode +
                ", properties=" + properties +
                ", trackingOptInLevel=" + trackingOptInLevel +
                ", tags=" + tags +
                ", attributes=" + attributes +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, emailOptInLevel, address, timezone, localeCountry, localeLanguage, emailOptInMode,
                properties, trackingOptInLevel, tags, attributes);
    }

    /**
     * Create RegisterEmailChannel Builder
     */
    public final static class Builder {
        private String address;
        private String timezone;
        private String localeCountry;
        private String localeLanguage;
        private Map<OptInLevel, String> emailOptInLevel = new HashMap<>();
        private OptInMode emailOptInMode;
        private Map<String, String> properties = new HashMap<>();
        private Map<TrackingOptInLevel, String> trackingOptInLevel = new HashMap<>();
        private Map<String, List<String>> tags = new HashMap<>();
        private Map<String, String> attributes = new HashMap<>();

        protected Builder() {
        }

        /**
         * Set the EmailOptInLevel status and time.
         *
         * @param level time OptInLevel, String
         * @param time  String
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailOptInLevel(OptInLevel level, String time) {
            this.emailOptInLevel.put(level, time);
            return this;
        }

        /**
         * Set tracking opt in/out timestamp for click/open tracking.
         *
         * @param level TrackingOptInLevel
         * @param time  String timestamp
         * @return RegisterEmailChannel Builder
         */
        public Builder setTrackingOptInLevel(TrackingOptInLevel level, String time) {
            this.trackingOptInLevel.put(level, time);
            return this;
        }

        /**
         * Add a single tag under a tag group.
         *
         * @param group String tag group
         * @param tag   String tag value
         * @return Builder
         */
        public Builder addTag(String group, String tag) {
            this.tags.computeIfAbsent(group, g -> new java.util.ArrayList<>()).add(tag);
            return this;
        }

        /**
         * Add all grouped tags.
         *
         * @param tags Map of group to list of tags
         * @return Builder
         */
        public Builder addAllTags(Map<String, List<String>> tags) {
            for (String group : tags.keySet()) {
                this.tags.computeIfAbsent(group, g -> new java.util.ArrayList<>()).addAll(tags.get(group));
            }
            return this;
        }

        /**
         * Add a single attribute key/value.
         *
         * @param key   String
         * @param value String
         * @return Builder
         */
        public Builder addAttribute(String key, String value) {
            this.attributes.put(key, value);
            return this;
        }

        /**
         * Add all attributes key/value pairs.
         *
         * @param attributes Map of Strings
         * @return Builder
         */
        public Builder addAllAttributes(Map<String, String> attributes) {
            this.attributes.putAll(attributes);
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (Email).
         *
         * @param address String
         * @return RegisterEmailChannel Builder
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Set timezone string. An IANA tzdata identifier for the timezone
         * as a string, e.g., "America/Los Angeles". Will set the timezone
         * tag group tag with the specified value.
         *
         * @param timezone String
         * @return RegisterEmailChannel Builder
         */
        public Builder setTimeZone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        /**
         * Set a the localeCountry The two-letter country locale shortcode.
         * Will set the ua_locale_country tag group to the specified value.
         * Used _ notation to conform with previously written code
         *
         * @param locale_country String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleCountry(String locale_country) {
            this.localeCountry = locale_country;
            return this;
        }

        /**
         * Set a String localeLanguage, the two-letter language locale
         * shortcode. Will set the ua_locale_language tag group to the
         * specified value
         * Used _ notation to conform with previously written code
         *
         * @param locale_language String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.localeLanguage = locale_language;
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (Email).
         *
         * @param emailOptInMode OptInMode
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailOptInMode(OptInMode emailOptInMode) {
            this.emailOptInMode = emailOptInMode;
            return this;
        }

        /**
         * Add a property.
         * 
         * @param key   String
         * @param value String
         * @return EmailChannel Builder
         */
        public Builder addProperty(String key, String value) {
            properties.put(key, value);
            return this;
        }

        /**
         * Add all properties values.
         * 
         * @param properties Map of Strings.
         * @return EmailChannel Builder
         */
        public Builder addAllProperties(Map<String, String> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public RegisterEmailChannel build() {
            Preconditions.checkNotNull(address, "address cannot be null.");
            Preconditions.checkNotNull(emailOptInLevel, "'emailOptInLevel' cannot be null.");

            return new RegisterEmailChannel(this);
        }
    }
}
