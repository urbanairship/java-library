/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ios.IosSettings;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Represents a single channel object.
 */
public final class ChannelView {

    private final String channelId;
    private final ChannelType channelType;
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

    private ChannelView() {
        this(null, null, true, true, Optional.<Boolean>absent(), Optional.<String>absent(), null,
            Optional.<DateTime>absent(), Optional.<String>absent(), null, null, Optional.<IosSettings>absent());
    }

    private ChannelView(String channelId,
                       ChannelType channelType,
                       boolean installed,
                       boolean optIn,
                       Optional<Boolean> background,
                       Optional<String> pushAddress,
                       DateTime created,
                       Optional<DateTime> lastRegistration,
                       Optional<String> alias,
                       ImmutableSet<String> tags,
                       ImmutableMap<String, ImmutableSet<String>> tagGroups,
                       Optional<IosSettings> iosSettings) {
        this.channelId = channelId;
        this.channelType = channelType;
        this.installed = installed;
        this.optIn = optIn;
        this.background = background;
        this.pushAddress = pushAddress;
        this.created = created;
        this.lastRegistration = lastRegistration;
        this.alias = alias;
        this.tags = tags;
        this.tagGroups = tagGroups;
        this.iosSettings = iosSettings;
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
     * @return ChannelType
     */
    public ChannelType getChannelType() {
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

    @Override
    public String toString() {
        return "ChannelView{" +
            "channelId='" + channelId + '\'' +
            ", deviceType=" + channelType +
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
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelId, channelType, installed, optIn, background, pushAddress, created, lastRegistration, alias, tags, tagGroups, iosSettings);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChannelView other = (ChannelView) obj;
        return Objects.equal(this.channelId, other.channelId) &&
            Objects.equal(this.channelType, other.channelType) &&
            Objects.equal(this.installed, other.installed) &&
            Objects.equal(this.optIn, other.optIn) &&
            Objects.equal(this.background, other.background) &&
            Objects.equal(this.pushAddress, other.pushAddress) &&
            Objects.equal(this.created, other.created) &&
            Objects.equal(this.lastRegistration, other.lastRegistration) &&
            Objects.equal(this.alias, other.alias) &&
            Objects.equal(this.tags, other.tags) &&
            Objects.equal(this.tagGroups, other.tagGroups) &&
            Objects.equal(this.iosSettings, other.iosSettings);
    }

    public final static class Builder {
        private final ImmutableSet.Builder<String> tags = ImmutableSet.builder();
        private final ImmutableMap.Builder<String, ImmutableSet<String>> tagGroups = ImmutableMap.builder();
        private String channelId = null;
        private ChannelType channelType = null;
        private Boolean optIn = null;
        private Boolean installed = null;
        private Boolean background = null;
        private String pushAddress = null;
        private DateTime created = null;
        private DateTime lastRegistration = null;
        private String alias = null;
        private IosSettings iosSettings = null;

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
         * @param channelType ChannelType
         * @return Builder
         */
        public Builder setChannelType(ChannelType channelType) {
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
         * @return
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
         * Build the ChannelView object
         * @return ChannelView
         */
        public ChannelView build() {
            Preconditions.checkNotNull(channelId);
            Preconditions.checkNotNull(channelType);
            Preconditions.checkNotNull(installed);
            Preconditions.checkNotNull(optIn);
            Preconditions.checkNotNull(created);

            return new ChannelView(
                channelId,
                channelType,
                installed,
                optIn,
                Optional.fromNullable(background),
                Optional.fromNullable(pushAddress),
                created,
                Optional.fromNullable(lastRegistration),
                Optional.fromNullable(alias),
                tags.build(),
                tagGroups.build(),
                Optional.fromNullable(iosSettings)
            );
        }
    }
}