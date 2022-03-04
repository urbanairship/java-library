package com.urbanairship.api.channel.model.sms;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.channel.model.ChannelModelObject;

import org.joda.time.DateTime;

/**
 * Represents the payload to be used for updating a sms channel.
 */
public class UpdateSmsChannel extends ChannelModelObject {
    private final String msisdn;
    private final String sender;
    private final Optional<DateTime> optedIn;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;

    //Protected to facilitate subclassing for create and send child object
    protected UpdateSmsChannel(Builder builder) {
        this.msisdn = builder.msisdn;
        this.sender = builder.sender;
        this.optedIn = Optional.fromNullable(builder.optedIn);
        this.timezone = Optional.fromNullable(builder.timezone);
        this.localeCountry = Optional.fromNullable(builder.localeCountry);
        this.localeLanguage = Optional.fromNullable(builder.localeLanguage);
    }

    /**
     * Get the msisdn.
     *
     * @return String msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Get the sender.
     *
     * @return String sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Get the optedIn date.
     *
     * @return DateTime optedIn
     */
    public Optional<DateTime> getOptedIn() {
        return optedIn;
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
     * New UpdateSmsChannel builder.
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
        UpdateSmsChannel that = (UpdateSmsChannel) o;
        return msisdn == that.msisdn &&
                Objects.equal(sender, that.sender) &&
                Objects.equal(optedIn, that.optedIn) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage);
    }

    @Override
    public String toString() {
        return "UpdateSmsChannel{" +
                "msisdn=" + msisdn +
                ", sender=" + sender +
                ", optedIn=" + optedIn +
                ", timezone=" + timezone +
                ", localeCountry=" + localeCountry +
                ", localeLanguage=" + localeLanguage +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msisdn, sender, optedIn, timezone, localeCountry, localeLanguage);
    }

    /**
     * Create UpdateSmsChannel Builder
     */
    public final static class Builder {
        private String msisdn;
        private String sender;
        private DateTime optedIn;
        private String timezone;
        private String localeCountry;
        private String localeLanguage;

        protected Builder() {
        }

        /**
         * Set the msisdn.
         *
         * @param msisdn String
         * @return UpdateSmsChannel Builder
         */
        public Builder setMsisdn(String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        /**
         * Set the sender ID
         *
         * @param sender String
         * @return UpdateSmsChannel Builder
         */
        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        /**
         * Optional
         * Set the optedIn date
         *
         * @param optedIn DateTime
         * @return UpdateSmsChannel Builder
         */
        public Builder setOptedIn(DateTime optedIn) {
            this.optedIn = optedIn;
            return this;
        }

        /**
         * Set timezone string. An IANA tzdata identifier for the timezone
         * as a string, e.g., "America/Los Angeles". Will set the timezone
         * tag group tag with the specified value.
         *
         * @param timezone String
         * @return UpdateSmsChannel Builder
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
         * @return UpdateSmsChannel Builder
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
         * @return UpdateSmsChannel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.localeLanguage = locale_language;
            return this;
        }

        public UpdateSmsChannel build() {
            Preconditions.checkNotNull(msisdn, "msisdn cannot be null.");
            Preconditions.checkNotNull(sender, "sender cannot be null.");

            return new UpdateSmsChannel(this);
        }
    }
}
