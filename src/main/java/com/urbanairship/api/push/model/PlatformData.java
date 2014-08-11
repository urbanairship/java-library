package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class PlatformData extends PushModelObject {
    private final boolean all;
    private final Optional<ImmutableSet<Platform>> platforms;

    private PlatformData(boolean all, Optional<ImmutableSet<Platform>> platforms) {
        this.all = all;
        this.platforms = platforms;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static PlatformData all() {
        return PlatformData.newBuilder()
            .setAll(true)
            .build();
    }

    public static PlatformData of(Platform... platforms) {
        PlatformData.Builder builder = PlatformData.newBuilder();
        for (Platform p : platforms) {
            builder.addPlatform(p);
        }
        return builder.build();
    }

    public boolean isAll() {
        return all;
    }

    public Optional<ImmutableSet<Platform>> getPlatforms() {
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

        PlatformData that = (PlatformData) o;

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
        private ImmutableSet.Builder<Platform> platforms = null;

        private Builder() { }

        public Builder setAll(boolean value) {
            all = value;
            return this;
        }

        public Builder addPlatform(Platform platform) {
            if (platforms == null) {
                platforms = ImmutableSet.builder();
            }
            this.platforms.add(platform);
            return this;
        }

        public Builder addAllPlatforms(Iterable<Platform> platforms) {
            if (this.platforms == null) {
                this.platforms = ImmutableSet.builder();
            }
            this.platforms.addAll(platforms);
            return this;
        }

        public PlatformData build() {
            Preconditions.checkArgument(!(all && (platforms != null)), "'device_types' cannot be both 'all' and a list of platforms.");
            return new PlatformData(all,
                                    platforms == null
                                    ? Optional.<ImmutableSet<Platform>>absent()
                                    : Optional.fromNullable(platforms.build()));
        }
    }
}
