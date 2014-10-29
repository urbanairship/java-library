package com.urbanairship.api.channel.information.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.information.model.ios.IosSettings;
import org.apache.commons.lang.StringUtils;

public final class ChannelView {

    private final String channelId;
    private final DeviceType deviceType;
    private final boolean installed;
    private final boolean optedIn;
    private final Optional<Boolean> background;
    private final Optional<String> pushAddress;
    private final long createdMillis;
    private final Optional<Long> lastRegistrationMillis;
    private final Optional<String> alias;
    private final ImmutableSet<String> tags;
    private final Optional<IosSettings> iosSettings;

    public static Builder newBuilder() {
        return new Builder();
    }

    public ChannelView(String channelId,
                       DeviceType deviceType,
                       boolean installed,
                       boolean optedIn,
                       Optional<Boolean> background,
                       Optional<String> pushAddress,
                       long createdMillis,
                       Optional<Long> lastRegistrationMillis,
                       Optional<String> alias,
                       ImmutableSet<String> tags,
                       Optional<IosSettings> iosSettings) {
        this.channelId = channelId;
        this.deviceType = deviceType;
        this.installed = installed;
        this.optedIn = optedIn;
        this.background = background;
        this.pushAddress = pushAddress;
        this.createdMillis = createdMillis;
        this.lastRegistrationMillis = lastRegistrationMillis;
        this.alias = alias;
        this.tags = tags;
        this.iosSettings = iosSettings;
    }

    public String getChannelId() {
        return channelId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public boolean isInstalled() {
        return installed;
    }

    public boolean isOptedIn() {
        return optedIn;
    }

    public Optional<Boolean> getBackground() {
        return background;
    }

    public Optional<String> getPushAddress() {
        return pushAddress;
    }

    public long getCreatedMillis() {
        return createdMillis;
    }

    public Optional<Long> getLastRegistrationMillis() {
        return lastRegistrationMillis;
    }

    public Optional<String> getAlias() {
        return alias;
    }

    public ImmutableSet<String> getTags() {
        return tags;
    }

    public Optional<IosSettings> getIosSettings() {
        return iosSettings;
    }

    @Override
    public String toString() {
        return "ChannelView{" +
                "channelId='" + channelId + '\'' +
                ", deviceType=" + deviceType +
                ", installed=" + installed +
                ", optedIn=" + optedIn +
                ", background=" + background +
                ", pushAddress=" + pushAddress +
                ", createdMillis=" + createdMillis +
                ", lastRegistrationMillis=" + lastRegistrationMillis +
                ", alias=" + alias +
                ", tags=" + tags +
                ", iosSettings=" + iosSettings +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelId, deviceType, installed, optedIn, background, pushAddress, createdMillis, lastRegistrationMillis, alias, tags, iosSettings);
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
        return Objects.equal(this.channelId, other.channelId) && Objects.equal(this.deviceType, other.deviceType) && Objects.equal(this.installed, other.installed) && Objects.equal(this.optedIn, other.optedIn) && Objects.equal(this.background, other.background) && Objects.equal(this.pushAddress, other.pushAddress) && Objects.equal(this.createdMillis, other.createdMillis) && Objects.equal(this.lastRegistrationMillis, other.lastRegistrationMillis) && Objects.equal(this.alias, other.alias) && Objects.equal(this.tags, other.tags) && Objects.equal(this.iosSettings, other.iosSettings);
    }

    public final static class Builder {
        private String channelId = null;
        private DeviceType deviceType = null;
        private Boolean optedIn = null;
        private Boolean installed = null;
        private Boolean background = null;
        private String pushAddress = null;
        private Long createdMillis = null;
        private Long lastRegistrationMillis = null;
        private String alias = null;
        private final ImmutableSet.Builder<String> tags = ImmutableSet.builder();
        private IosSettings iosSettings = null;

        private Builder() { }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public Builder setInstalled(Boolean installed) {
            this.installed = installed;
            return this;
        }

        public Builder setOptedIn(Boolean optedIn) {
            this.optedIn = optedIn;
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

        public Builder setCreatedMillis(Long createdMillis) {
            this.createdMillis = createdMillis;
            return this;
        }

        public Builder setLastRegistrationMillis(Long lastRegistrationMillis) {
            this.lastRegistrationMillis = lastRegistrationMillis;
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

        public Builder setIosSettings(IosSettings iosSettings) {
            this.iosSettings = iosSettings;
            return this;
        }

        public ChannelView build() {
            Preconditions.checkNotNull(channelId);
            Preconditions.checkNotNull(deviceType);
            Preconditions.checkNotNull(installed);
            Preconditions.checkNotNull(optedIn);
            Preconditions.checkNotNull(createdMillis);

            return new ChannelView(
                    channelId,
                    deviceType,
                    installed,
                    optedIn,
                    Optional.fromNullable(background),
                    Optional.fromNullable(pushAddress),
                    createdMillis,
                    Optional.fromNullable(lastRegistrationMillis),
                    Optional.fromNullable(alias),
                    tags.build(),
                    Optional.fromNullable(iosSettings)
            );
        }
    }
}
