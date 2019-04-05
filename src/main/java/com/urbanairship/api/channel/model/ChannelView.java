/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.web.WebSettings;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Represents a single channel object.
 */
public final class ChannelView {

    private final String channelId;
    private final String channelType;
    private final boolean installed;
    private final boolean optIn;
    private final Optional<Boolean> background;
    private final Optional<String> pushAddress;
    private final DateTime created;
    private final Optional<DateTime> lastRegistration;
    private final Optional<String> alias;
    private final ImmutableSet<String> tags;
    private final ImmutableMap<String, ImmutableSet<String>> tagGroups;
    private final Optional<IosSettings> iosSettings;
    private final Optional<WebSettings> web;
    private final Optional<OpenChannel> open;
    private final Optional<String> address;
    private final Optional<String> namedUser;

    private ChannelView(Builder builder) {
        this.channelId = builder.channelId;
        this.channelType = builder.channelType;
        this.installed = builder.installed;
        this.optIn = builder.optIn;
        this.background = Optional.fromNullable(builder.background);
        this.pushAddress = Optional.fromNullable(builder.pushAddress);
        this.created = builder.created;
        this.lastRegistration = Optional.fromNullable(builder.lastRegistration);
        this.alias = Optional.fromNullable(builder.alias);
        this.tags = builder.tags.build();
        this.tagGroups = builder.tagGroups.build();
        this.iosSettings = Optional.fromNullable(builder.iosSettings);
        this.web = Optional.fromNullable(builder.webSettings);
        this.open = Optional.fromNullable(builder.openChannel);
        this.address = Optional.fromNullable(builder.address);
        this.namedUser = Optional.fromNullable(builder.namedUser);
    }

    /**
     * New ChannelView builder.
     *
     * @return builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the channel ID
     *
     * @return String channel ID
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Get the channel type
     *
     * @return String channelType
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * Get the installed status as a boolean
     *
     * @return boolean installed status
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     * Get the opt-in status as a boolean
     *
     * @return boolean opt-in status
     */
    public boolean isOptIn() {
        return optIn;
    }

    /**
     * Get the background status as a boolean
     *
     * @return Optional boolean background status
     */
    public Optional<Boolean> getBackground() {
        return background;
    }

    /**
     * Get the push address
     *
     * @return Optional String push address
     */
    public Optional<String> getPushAddress() {
        return pushAddress;
    }

    /**
     * Get the time when the channel was created
     *
     * @return DateTime of creation
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * Get the time of last registration
     *
     * @return Optional DateTime of last registration
     */
    public Optional<DateTime> getLastRegistration() {
        return lastRegistration;
    }

    /**
     * Get the associated alias
     *
     * @return Optional String alias
     */
    public Optional<String> getAlias() {
        return alias;
    }

    /**
     * Get any associated tags
     *
     * @return ImmutableSet of tags
     */
    public ImmutableSet<String> getTags() {
        return tags;
    }

    /**
     * Get any associated tag groups and tags
     *
     * @return ImmutableMap of tag groups and the relevant Immutable sets of tags
     */
    public ImmutableMap<String, ImmutableSet<String>> getTagGroups() {
        return tagGroups;
    }

    /**
     * Get the iOS settings
     *
     * @return Optional IosSettings
     */
    public Optional<IosSettings> getIosSettings() {
        return iosSettings;
    }

    /**
     * Get the WebSettings displayed only for WebSettings channels
     *
     * @return Optional WebSettings
     */
    public Optional<WebSettings> getWebSettings() {
        return web;
    }

    /**
     * Get the OpenChannel Platform Options Object.
     *
     * @return Optional OpenChannel
     */
    public Optional<OpenChannel> getOpen() {
        return open;
    }

    /**
     * Get the address. The primary identifier of a record. For example,
     * in an SMS integration, it could be the end user’s phone number.
     *
     * @return Optional String address
     */
    public Optional<String> getAddress() {
        return address;
    }

    /**
     * Get the named user. A Named User is a proprietary identifier
     * that maps customer-chosen IDs, e.g., CRM data, to Channels.
     *
     * @return Optional String namedUser
     */
    public Optional<String> getNamedUser() {
        return namedUser;
    }

    @Override
    public String toString() {
        return "ChannelView{" +
                "channelId='" + channelId + '\'' +
                ", channelType='" + channelType + '\'' +
                ", installed=" + installed +
                ", optIn=" + optIn +
                ", background=" + background +
                ", pushAddress=" + pushAddress +
                ", created=" + created +
                ", lastRegistration=" + lastRegistration +
                ", alias=" + alias +
                ", tags=" + tags +
                ", tagGroups=" + tagGroups +
                ", iosSettings=" + iosSettings +
                ", web=" + web +
                ", open=" + open +
                ", address=" + address +
                ", namedUser=" + namedUser +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelId, channelType, installed, optIn,
                background, pushAddress, created, lastRegistration,
                alias, tags, tagGroups, iosSettings,
                web, open, address, namedUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelView that = (ChannelView) o;
        return installed == that.installed &&
                optIn == that.optIn &&
                Objects.equal(channelId, that.channelId) &&
                Objects.equal(channelType, that.channelType) &&
                Objects.equal(background, that.background) &&
                Objects.equal(pushAddress, that.pushAddress) &&
                Objects.equal(created, that.created) &&
                Objects.equal(lastRegistration, that.lastRegistration) &&
                Objects.equal(alias, that.alias) &&
                Objects.equal(tags, that.tags) &&
                Objects.equal(tagGroups, that.tagGroups) &&
                Objects.equal(iosSettings, that.iosSettings) &&
                Objects.equal(web, that.web) &&
                Objects.equal(open, that.open) &&
                Objects.equal(address, that.address) &&
                Objects.equal(namedUser, that.namedUser);
    }

    public final static class Builder {
        private final ImmutableSet.Builder<String> tags = ImmutableSet.builder();
        private final ImmutableMap.Builder<String, ImmutableSet<String>> tagGroups = ImmutableMap.builder();
        private String channelId = null;
        private String channelType = null;
        private Boolean optIn = null;
        private Boolean installed = null;
        private Boolean background = null;
        private String pushAddress = null;
        private DateTime created = null;
        private DateTime lastRegistration = null;
        private String alias = null;
        private IosSettings iosSettings = null;
        private WebSettings webSettings = null;
        private OpenChannel openChannel = null;
        private String address = null;
        private String namedUser = null;

        private Builder() {
        }

        /**
         * Set the channel ID
         *
         * @param channelId String
         * @return Builder
         */
        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        /**
         * Set the channel type
         *
         * @param channelType String
         * @return Builder
         */
        public Builder setChannelType(String channelType) {
            this.channelType = channelType;
            return this;
        }

        /**
         * Set the installed status
         *
         * @param installed boolean
         * @return Builder
         */
        public Builder setInstalled(Boolean installed) {
            this.installed = installed;
            return this;
        }

        /**
         * Set the opt-in status
         *
         * @param optIn boolean
         * @return Builder
         */
        public Builder setOptIn(Boolean optIn) {
            this.optIn = optIn;
            return this;
        }

        /**
         * Set the background status
         *
         * @param background boolean
         * @return Builder
         */
        public Builder setBackground(Boolean background) {
            this.background = background;
            return this;
        }

        /**
         * Set the push address
         *
         * @param pushAddress String
         * @return Builder
         */
        public Builder setPushAddress(String pushAddress) {
            this.pushAddress = pushAddress;
            return this;
        }

        /**
         * Set the created time
         * @param created DateTime
         * @return Builder
         */
        public Builder setCreated(DateTime created) {
            this.created = created;
            return this;
        }

        /**
         * Set the last registration time
         * @param lastRegistration DateTime
         * @return Builder
         */
        public Builder setLastRegistration(DateTime lastRegistration) {
            this.lastRegistration = lastRegistration;
            return this;
        }

        /**
         * Set the alias
         *
         * @param alias String
         * @return Builder
         */
        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        /**
         * Set the channel tags
         *
         * @param tags Iterable of tags
         * @return Builder
         */
        public Builder addAllTags(Iterable<String> tags) {
            Preconditions.checkNotNull(tags);

            for (String tag : tags) {
                this.tags.add(tag);
            }

            return this;
        }

        /**
         * Add a tag
         *
         * @param tag String
         * @return Builder
         */
        public Builder addTag(String tag) {
            if (StringUtils.isNotBlank(tag)) {
                this.tags.add(tag);
            }
            return this;
        }

        /**
         * Add a tag group with tags
         *
         * @param tagGroup A map entry of a tag group and ImmutableSet of tags
         * @return Builder
         */
        public Builder addTagGroup(Map.Entry<String, ImmutableSet<String>> tagGroup) {
            if (!tagGroup.getKey().isEmpty()) {
                this.tagGroups.put(tagGroup);
            }
            return this;
        }

        /**
         * Add all the tag groups with tags
         *
         * @param tagGroups ImmutableMap of tag groups and ImmutableSets of tags
         * @return Builder
         */
        public Builder addAllTagGroups(ImmutableMap<String, ImmutableSet<String>> tagGroups) {
            if (!tagGroups.isEmpty()) {
                this.tagGroups.putAll(tagGroups);
            }
            return this;
        }

        /**
         * Set the iOS settings
         *
         * @param iosSettings IosSettings
         * @return Builder
         */
        public Builder setIosSettings(IosSettings iosSettings) {
            this.iosSettings = iosSettings;
            return this;
        }

        /**
         * Set the webSettings object
         *
         * @param webSettings WebSettings
         * @return Builder
         */
        public Builder setWebSettings(WebSettings webSettings) {
            this.webSettings = webSettings;
            return this;
        }

        /**
         * Set the OpenChannel Platform Options Object.
         * @param openChannel OpenChannel
         * @return Builder
         */
        public Builder setOpenChannel(OpenChannel openChannel) {
            this.openChannel = openChannel;
            return this;
        }

        /**
         * Set the address. The primary identifier of a record. For example,
         * in an SMS integration, it could be the end user’s phone number.
         * @param address String
         * @return Builder
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Set the named user.
         * @param namedUser String
         * @return Builder
         */
        public Builder setNamedUser(String namedUser) {
            this.namedUser = namedUser;
            return this;
        }

        /**
         * Build the ChannelView object
         * @return ChannelView
         */
        public ChannelView build() {
            Preconditions.checkNotNull(channelId);
            Preconditions.checkNotNull(channelType);
            Preconditions.checkNotNull(installed);
            Preconditions.checkNotNull(optIn);
            Preconditions.checkNotNull(created);

            return new ChannelView(this);
        }
    }
}