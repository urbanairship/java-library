/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Represents the different platforms available.
 */
public final class PlatformData extends PushModelObject {
    private final boolean all;
    private final Optional<ImmutableSet<Platform>> platforms;

    private PlatformData(boolean all, Optional<ImmutableSet<Platform>> platforms) {
        this.all = all;
        this.platforms = platforms;
    }

    /**
     * New PlatformData builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Factory method for creating a PlatformData object that sends to all
     * available messaging platforms.
     * @return PlatformData
     */
    public static PlatformData all() {
        return PlatformData.newBuilder()
            .setAll(true)
            .build();
    }

    /**
     * Factory method for creating a PlatformData object with all the platforms
     * that were passed in
     * @param platforms Platform to send to.
     * @return PlatformData
     */
    public static PlatformData of(Platform... platforms) {
        PlatformData.Builder builder = PlatformData.newBuilder();
        for (Platform p : platforms) {
            builder.addPlatform(p);
        }
        return builder.build();
    }

    /**
     * Returns true if set for all platforms.
     * @return is all platforms
     */
    public boolean isAll() {
        return all;
    }

    /**
     * Return a set of all platforms this object is targeting.
     * @return platforms
     */
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

    /**
     * PlatformData Builder
     */
    public static class Builder {

        private boolean all = false;
        private ImmutableSet.Builder<Platform> platforms = null;

        private Builder() { }

        /**
         * New PlatformData builder set for all platforms.
         * @param value true or false for set all
         * @return Builder
         */
        public Builder setAll(boolean value) {
            all = value;
            return this;
        }

        /**
         * Add a platform to send to.
         * @param platform Platform
         * @return Builder
         */
        public Builder addPlatform(Platform platform) {
            if (platforms == null) {
                platforms = ImmutableSet.builder();
            }
            this.platforms.add(platform);
            return this;
        }

        /**
         * Add all platforms from the iterable.
         * @param platforms platform iterable.
         * @return Builder
         */
        public Builder addAllPlatforms(Iterable<Platform> platforms) {
            if (this.platforms == null) {
                this.platforms = ImmutableSet.builder();
            }
            this.platforms.addAll(platforms);
            return this;
        }

        /**
         * Build a PlatformData object.
         * @return PlatformData
         */
        public PlatformData build() {
            Preconditions.checkArgument(!(all && (platforms != null)), "'device_types' cannot be both 'all' and a list of platforms.");
            return new PlatformData(all,
                                    platforms == null
                                    ? Optional.<ImmutableSet<Platform>>absent()
                                    : Optional.fromNullable(platforms.build()));
        }
    }
}
