package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.model.ChannelType;

import java.util.List;

/**
 * Represents the payload to be used for registering or updating an email channel.
 */
public class RegisterEmailChannel {

    private final ChannelType type;
    private final Optional<OptInLevel> emailOptInLevel;
    private final Optional<String> address;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;


    private RegisterEmailChannel(Builder builder) {
        this.type = ChannelType.EMAIL;
        this.emailOptInLevel = Optional.fromNullable((builder.email_opt_in_level));
        this.address = Optional.fromNullable(builder.address);
        this.timezone = Optional.fromNullable(builder.timezone);
        this.localeCountry = Optional.fromNullable(builder.locale_country);
        this.localeLanguage = Optional.fromNullable(builder.locale_language);
    }

    /**
     * Get the RegisterEmailChannelType.
     * @return ChannelType type
     */
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }

    /**
     * Get the channel email opt in level.
     * @return Optional OptInLevel emailOptInLevel
     */
    public Optional<OptInLevel> getEmailOptInLevel() {
        return emailOptInLevel;
    }

    /**
     * Get the email channel's address, a Unique identifier of the object
     * used as the primary ID in the delivery tier (webhook). One-to-one
     * with Channel ID. New addresses on existing channels will overwrite
     * old associations.If
     * missing, channel_id must be present.
     * @return Optional String address
     */
    public Optional<String> getAddress() {
        return address;
    }

    /**
     * Get a String representation of the timezone.
     * @return Optional String timezone
     */
    public Optional<String> getTimezone() {
        return timezone;
    }

    /**
     * Get a String representation of the locale country.
     * @return Optional String localeCountry
     */
    public Optional<String> getLocaleCountry() {
        return localeCountry;
    }

    /**
     * Get a String representation of the locale language.
     * @return Optional String localeLanguage
     */
    public Optional<String> getLocaleLanguage() {
        return localeLanguage;
    }

    /**
     * New Register Email Register Email Channel builder.
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
        RegisterEmailChannel that = (RegisterEmailChannel) o;
        return type == that.type &&
                Objects.equal(address, that.address) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage) &&
                Objects.equal(emailOptInLevel, that.emailOptInLevel);

    }

    @Override
    public String toString() {
        return "Channel{" +
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
     * Register Email RegisterEmailChannel Builder
     */
    public final static class Builder {
        private ChannelType type;
        private String address;
        private String timezone;
        private String locale_country;
        private String locale_language;
        private OptInLevel email_opt_in_level;

        private Builder() {}


        /**
         * Set the RegisterEmailChannel opt in status.
         * @param emailOptInLevel OptInLevel
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailOptInLevel(OptInLevel emailOptInLevel) {
            this.email_opt_in_level = emailOptInLevel;
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (webhook). One-to-one
         * with Channel ID. New addresses on existing channels will overwrite
         * old associations. Examples: email address, phone number. If
         * missing, channel_id must be present.
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
         * @param locale_country String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleCountry(String locale_country) {
            this.locale_country = locale_country;
            return this;
        }

        /**
         * Set a String localeLanguage, the two-letter language locale
         * shortcode. Will set the ua_locale_language tag group to the
         * specified value
         * @param locale_language String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.locale_language = locale_language;
            return this;
        }

        public RegisterEmailChannel build() {
            Preconditions.checkNotNull(email_opt_in_level, "'email_opt_in_level' cannot be null.");

            return new RegisterEmailChannel(this);
        }
    }
}



