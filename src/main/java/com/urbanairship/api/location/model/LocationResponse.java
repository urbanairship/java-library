/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * Location lookup response object.
 */
public final class LocationResponse {

    private final Optional<ImmutableList<LocationView>> features;

    private LocationResponse(ImmutableList<LocationView> features) {
        this.features = Optional.fromNullable(features);
    }

    /**
     * New LocationResponse builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the response features.
     *
     * @return An optional immutable list of LocationView objects.
     */
    public Optional<ImmutableList<LocationView>> getFeatures() {
        return features;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(features);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LocationResponse other = (LocationResponse) obj;
        return Objects.equal(this.features, other.features);
    }

    @Override
    public String toString() {
        return "APILocationResponse{" +
                "features=" + features +
                '}';
    }

    public static class Builder {
        private ImmutableList.Builder<LocationView> features = ImmutableList.builder();

        private Builder() {
        }

        /**
         * Add the location features.
         *
         * @param value An iterable of LocationView objects.
         * @return Build
         */
        public Builder addAllFeatures(Iterable<? extends LocationView> value) {
            this.features.addAll(value);
            return this;
        }

        /**
         * Build the LocationResponse object.
         *
         * @return LocationResponse
         */
        public LocationResponse build() {
            return new LocationResponse(features.build());
        }
    }
}
