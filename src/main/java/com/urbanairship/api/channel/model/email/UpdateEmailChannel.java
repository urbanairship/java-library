package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionList;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the payload to be used for updating an email channel.
 */
public class UpdateEmailChannel extends PushModelObject {
    private final ChannelType type;
    private final Optional<Map<OptInLevel, String>> emailOptInLevel;
    private final String address;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;

    //Protected to facilitate subclassing for create and send child object
    protected UpdateEmailChannel(Builder builder) {
        this.type = ChannelType.EMAIL;
        this.emailOptInLevel = Optional.ofNullable((builder.emailOptInLevel));
        this.address = builder.address;
        this.timezone = Optional.ofNullable(builder.timezone);
        this.localeCountry = Optional.ofNullable(builder.localeCountry);
        this.localeLanguage = Optional.ofNullable(builder.localeLanguage);
    }

    /**
     * Get the UpdateEmailChannelType.
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
     * New UpdateEmailChannel builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateEmailChannel that = (UpdateEmailChannel) o;
        return type == that.type &&
                Objects.equal(address, that.address) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage) &&
                Objects.equal(emailOptInLevel, that.emailOptInLevel);
    }

    @Override
    public String toString() {
        return "UpdateEmailChannel{" +
                "type=" + type +
                ", emailOptInLevel=" + emailOptInLevel +
                ", address=" + address +
                ", timezone=" + timezone +
                ", localeCountry=" + localeCountry +
                ", localeLanguage=" + localeLanguage +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, emailOptInLevel, address, timezone, localeCountry, localeLanguage);
    }

    /**
     * Create UpdateEmailChannel Builder
     */
    public final static class Builder {
        private ChannelType type;
        private String address;
        private String timezone;
        private String localeCountry;
        private String localeLanguage;
        private Map<OptInLevel, String> emailOptInLevel = new HashMap<>();

        protected Builder() {
        }

        /**
         * Set the EmailOptInLevel status and time.
         *
         * @param level time OptInLevel, String
         * @param time String
         * @return UpdateEmailChannel Builder
         */
        public Builder setEmailOptInLevel(OptInLevel level, String time) {
            this.emailOptInLevel.put(level, time);
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (Email).
         * The email address can't be updated, it has to be the email address associated with the email channel.
         *
         * @param address String
         * @return UpdateEmailChannel Builder
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
         * @return UpdateEmailChannel Builder
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
         * @return UpdateEmailChannel Builder
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
         * @return UpdateEmailChannel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.localeLanguage = locale_language;
            return this;
        }


        public UpdateEmailChannel build() {
            Preconditions.checkNotNull(address, "address cannot be null.");
            Preconditions.checkNotNull(emailOptInLevel, "'emailOptInLevel' cannot be null.");

            return new UpdateEmailChannel(this);
        }
    }
}
