package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class PlatformStatsResponse {
    private final Optional<String> nextPage;
    private final Optional<ImmutableList<PlatformStats>> platformStatsObjects;

    private PlatformStatsResponse() {
        this(null, null);
    }

    private PlatformStatsResponse(
            Optional<String> nextPage,
            Optional<ImmutableList<PlatformStats>> platformStatsObjects) {
        this.nextPage = nextPage;
        this.platformStatsObjects = platformStatsObjects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the next page attribute if present for a PlatformStatsRequest.
     *
     * @return An optional string
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get the list of PlatformStats objects for PlatformStatsRequest, if present.
     *
     * @return An Optional ImmutableList of PlatformStats objects
     */
    public Optional<ImmutableList<PlatformStats>> getPlatformStatsObjects() {
        return platformStatsObjects;
    }

    @Override
    public String toString() {
        return "PlatformStatsResponse{" +
                "nextPage=" + nextPage +
                "platformStatsObjects=" + platformStatsObjects +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(platformStatsObjects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlatformStatsResponse other = (PlatformStatsResponse) obj;
        return Objects.equal(this.platformStatsObjects, other.platformStatsObjects);
    }

    public static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<PlatformStats> platformStatsObjects = ImmutableList.builder();

        private Builder() {
        }

        /**
         * Set the next page
         *
         * @param value String
         * @return Builder
         */
        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        /**
         * Add a PlatformStats object for listing
         *
         * @param value PlatformStats
         * @return Builder
         */
        public Builder addPlatformStatsObject(PlatformStats value) {
            this.platformStatsObjects.add(value);
            return this;
        }

        /**
         * Add all PlatformStats objects for listing
         *
         * @param value Iterable of PlatformStats objects
         * @return Builder
         */
        public Builder addPlatformStatsObjects(Iterable<? extends PlatformStats> value) {
            this.platformStatsObjects.addAll(value);
            return this;
        }

        /**
         * Build the PlatformStatsResponse object
         *
         * @return PlatformStatsResponse
         */
        public PlatformStatsResponse build() {
            return new PlatformStatsResponse(Optional.fromNullable(nextPage), Optional.fromNullable(platformStatsObjects.build()));
        }
    }

}
