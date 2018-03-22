package com.urbanairship.api.channel.model.open;


import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.List;

/**
 * Represents the payload to be used for registering or updating an open channel.
 */
public class Channel extends PushModelObject {

    private final ChannelType type;
    private final Optional<Boolean> optIn;
    private final Optional<String> address;
    private final Optional<Boolean> setTags;
    private final Optional<ImmutableList<String>> tags;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;
    private final OpenChannel open;

    private Channel(Builder builder) {
        this.type = builder.type;
        this.open = builder.open;
        this.address = Optional.fromNullable(builder.address);

        this.optIn = Optional.fromNullable(builder.optIn);
        this.setTags = Optional.fromNullable(builder.setTags);
        this.timezone = Optional.fromNullable(builder.timezone);
        this.localeCountry = Optional.fromNullable(builder.locale_country);
        this.localeLanguage = Optional.fromNullable(builder.locale_language);

        if (builder.tags.build().isEmpty()) {
            this.tags = Optional.absent();
        } else {
            this.tags = Optional.of(builder.tags.build());
        }
    }

    /**
     * Get the ChannelType.
     * @return ChannelType type
     */
    public ChannelType getType() {
        return type;
    }

    /**
     * Get the channel opt in status.
     * @return Optional Boolean optIn
     */
    public Optional<Boolean> getOptIn() {
        return optIn;
    }

    /**
     * Get the channel's address, a Unique identifier of the object
     * used as the primary ID in the delivery tier (webhook). One-to-one
     * with Channel ID. New addresses on existing channels will overwrite
     * old associations. Examples: email address, phone number. If
     * missing, channel_id must be present.
     * @return Optional String address
     */
    public Optional<String> getAddress() {
        return address;
    }

    /**
     * Get the setTags flag. Optional, though required if tags is present.
     * If true on update, value of tags overwrites any existing tags.
     * If false, tags are unioned with existing tags.
     * @return Optional Boolean setTags
     */
    public Optional<Boolean> getSetTags() {
        return setTags;
    }

    /**
     * Get the List of String representations of tags.
     * @return Optional ImmutableList of Strings
     */
    public Optional<ImmutableList<String>> getTags() {
        return tags;
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
     * Get open channel specific properties.
     * @return OpenChannel open
     */
    public OpenChannel getOpen() {
        return open;
    }

    /**
     * New Channel Builder.
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
        Channel that = (Channel) o;
        return type == that.type &&
                Objects.equal(optIn, that.optIn) &&
                Objects.equal(address, that.address) &&
                Objects.equal(setTags, that.setTags) &&
                Objects.equal(tags, that.tags) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage) &&
                Objects.equal(open, that.open);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "type=" + type +
                ", optIn=" + optIn +
                ", address=" + address +
                ", setTags=" + setTags +
                ", tags=" + tags +
                ", timezone=" + timezone +
                ", localeCountry=" + localeCountry +
                ", localeLanguage=" + localeLanguage +
                ", open=" + open +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, optIn, address, setTags, tags, timezone, localeCountry, localeLanguage, open);
    }

    /**
     * Channel Builder
     */
    public final static class Builder {
        private ChannelType type;
        private boolean optIn;
        private String address;
        private boolean setTags;
        private ImmutableList.Builder<String> tags = ImmutableList.builder();
        private String timezone;
        private String locale_country;
        private String locale_language;
        private OpenChannel open;

        private Builder() {}

        /**
         * Set the ChannelType. Must be open.
         * @param type ChannelType
         * @return Channel Builder
         */
        public Builder setChannelType(ChannelType type) {
            this.type = type;
            return this;
        }

        /**
         * Set the channel opt in status. If false, no payloads will be
         * delivered for the channel.
         * @param optIn boolean
         * @return Channel Builder
         */
        public Builder setOptIn(boolean optIn) {
            this.optIn = optIn;
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (webhook). One-to-one
         * with Channel ID. New addresses on existing channels will overwrite
         * old associations. Examples: email address, phone number. If
         * missing, channel_id must be present.
         * @param address String
         * @return Channel Builder
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Optional, though required if tags is present.
         * If true on update, value of tags overwrites any existing tags.
         * If false, tags are unioned with existing tags.
         * @param setTags boolean
         * @return Channel Builder
         */
        public Builder setTags(boolean setTags) {
            this.setTags = setTags;
            return this;
        }

        /**
         * Add a List of String representations of tags.
         * @param tags A List of Strings
         * @return Channel Builder
         */
        public Builder addAllTags(List<String> tags) {
            this.tags.addAll(tags);
            return this;
        }

        /**
         * Set a String representation of a tag.
         * @param tag String
         * @return Channel Builder
         */
        public Builder addTag(String tag) {
            tags.add(tag);
            return this;
        }

        /**
         * Set timezone string. An IANA tzdata identifier for the timezone
         * as a string, e.g., "America/Los Angeles". Will set the timezone
         * tag group tag with the specified value.
         * @param timezone String
         * @return Channel Builder
         */
        public Builder setTimeZone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        /**
         * Set a the localeCountry The two-letter country locale shortcode.
         * Will set the ua_locale_country tag group to the specified value.
         * @param locale_country String
         * @return Channel Builder
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
         * @return Channel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.locale_language = locale_language;
            return this;
        }

        /**
         * Set open channel specific properties.
         * @param open OpenChannel
         * @return Channel Builder
         */
        public Builder setOpenChannel(OpenChannel open) {
            this.open = open;
            return this;
        }

        public Channel build() {
            Preconditions.checkNotNull(type, "'type' cannot be null.");
            Preconditions.checkNotNull(open, "'open' cannot be null.");

            return new Channel(this);
        }
    }
}
