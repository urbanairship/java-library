package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.location.model.Location;

public final class APILocationResponse {

    private final Optional<ImmutableList<Location>> features;

    private APILocationResponse(ImmutableList<Location> features) {
        this.features = Optional.fromNullable(features);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<ImmutableList<Location>> getFeatures() {
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
        final APILocationResponse other = (APILocationResponse) obj;
        return Objects.equal(this.features, other.features);
    }

    @Override
    public String toString() {
        return "APILocationResponse{" +
                "features=" + features +
                '}';
    }

    public static class Builder {
        private ImmutableList.Builder<Location> features = ImmutableList.builder();

        private Builder() {
        }

        public Builder addAllFeatures(Iterable<? extends Location> value) {
            this.features.addAll(value);
            return this;
        }

        public APILocationResponse build() {
            return new APILocationResponse(features.build());
        }
    }
}
