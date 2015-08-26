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

    public ChannelView(String channelId,
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getChannelId() {
        return channelId;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public boolean isInstalled() {
        return installed;
    }

    public boolean isOptIn() {
        return optIn;
    }

    public Optional<Boolean> getBackground() {
        return background;
    }

    public Optional<String> getPushAddress() {
        return pushAddress;
    }

    public DateTime getCreated() {
        return created;
    }

    public Optional<DateTime> getLastRegistration() {
        return lastRegistration;
    }

    public Optional<String> getAlias() {
        return alias;
    }

    public ImmutableSet<String> getTags() {
        return tags;
    }

    public ImmutableMap<String, ImmutableSet<String>> getTagGroups() {
        return tagGroups;
    }

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

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setChannelType(ChannelType channelType) {
            this.channelType = channelType;
            return this;
        }

        public Builder setInstalled(Boolean installed) {
            this.installed = installed;
            return this;
        }

        public Builder setOptIn(Boolean optIn) {
            this.optIn = optIn;
            return this;
        }

        public Builder setBackground(Boolean background) {
            this.background = background;
            return this;
        }

        public Builder setPushAddress(String pushAddress) {
            this.pushAddress = pushAddress;
            return this;
        }

        public Builder setCreated(DateTime created) {
            this.created = created;
            return this;
        }

        public Builder setLastRegistration(DateTime lastRegistration) {
            this.lastRegistration = lastRegistration;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder addAllTags(Iterable<String> tags) {
            Preconditions.checkNotNull(tags);

            for (String tag : tags) {
                this.tags.add(tag);
            }

            return this;
        }

        public Builder addTag(String tag) {
            if (StringUtils.isNotBlank(tag)) {
                this.tags.add(tag);
            }
            return this;
        }

        public Builder addTagGroup(Map.Entry<String, ImmutableSet<String>> tagGroup) {
            if (!tagGroup.getKey().isEmpty()) {
                this.tagGroups.put(tagGroup);
            }
            return this;
        }

        public Builder addAllTagGroups(ImmutableMap<String, ImmutableSet<String>> tagGroups) {
            if (!tagGroups.isEmpty()) {
                this.tagGroups.putAll(tagGroups);
            }
            return this;
        }

        public Builder setIosSettings(IosSettings iosSettings) {
            this.iosSettings = iosSettings;
            return this;
        }

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