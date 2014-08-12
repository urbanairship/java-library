package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class DeviceTypeData extends PushModelObject {
    private final boolean all;
    private final Optional<ImmutableSet<DeviceType>> platforms;

    private DeviceTypeData(boolean all, Optional<ImmutableSet<DeviceType>> platforms) {
        this.all = all;
        this.platforms = platforms;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static DeviceTypeData all() {
        return DeviceTypeData.newBuilder()
            .setAll(true)
            .build();
    }

    public static DeviceTypeData of(DeviceType... deviceTypes) {
        DeviceTypeData.Builder builder = DeviceTypeData.newBuilder();
        for (DeviceType p : deviceTypes) {
            builder.addPlatform(p);
        }
        return builder.build();
    }

    public boolean isAll() {
        return all;
    }

    public Optional<ImmutableSet<DeviceType>> getPlatforms() {
        return platforms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceTypeData that = (DeviceTypeData) o;

        if (all != that.all) {
            return false;
        }
        if (platforms != null ? !platforms.equals(that.platforms) : that.platforms != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (platforms != null ? platforms.hashCode() : 0);
        result = 31 * result + (all ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlatformData{" +
            "all=" + all +
            ", platforms=" + platforms +
            '}';
    }

    public static class Builder {

        private boolean all = false;
        private ImmutableSet.Builder<DeviceType> platforms = null;

        private Builder() { }

        public Builder setAll(boolean value) {
            all = value;
            return this;
        }

        public Builder addPlatform(DeviceType deviceType) {
            if (platforms == null) {
                platforms = ImmutableSet.builder();
            }
            this.platforms.add(deviceType);
            return this;
        }

        public Builder addAllPlatforms(Iterable<DeviceType> platforms) {
            if (this.platforms == null) {
                this.platforms = ImmutableSet.builder();
            }
            this.platforms.addAll(platforms);
            return this;
        }

        public DeviceTypeData build() {
            Preconditions.checkArgument(!(all && (platforms != null)), "'device_types' cannot be both 'all' and a list of platforms.");
            return new DeviceTypeData(all,
                                    platforms == null
                                    ? Optional.<ImmutableSet<DeviceType>>absent()
                                    : Optional.fromNullable(platforms.build()));
        }
    }
}
