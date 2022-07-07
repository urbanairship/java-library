package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class PlatformStatsResponse {
    private final Optional<String> nextPage;
    private final Optional<ImmutableList<PlatformStats>> platformStatsObjects;
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private PlatformStatsResponse() {
        this(null, null, null, null, null);
    }

    private PlatformStatsResponse(
            Optional<String> nextPage,
            Optional<ImmutableList<PlatformStats>> platformStatsObjects,
            Boolean ok,
            Optional<String> error,
            Optional<ErrorDetails> errorDetails) {
        this.nextPage = nextPage;
        this.platformStatsObjects = platformStatsObjects;
        this.ok = ok;
        this.error = error;
        this.errorDetails = errorDetails;
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

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "PlatformStatsResponse{" +
                "nextPage=" + nextPage +
                ", platformStatsObjects=" + platformStatsObjects +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, platformStatsObjects, ok, error, errorDetails);
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
        return Objects.equal(this.platformStatsObjects, other.platformStatsObjects) &&
        Objects.equal(ok, other.ok) &&
        Objects.equal(error, other.error) &&
        Objects.equal(errorDetails, other.errorDetails);
    }

    public static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<PlatformStats> platformStatsObjects = ImmutableList.builder();
        private boolean ok;
        private String error;
        private ErrorDetails errorDetails;

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
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        /**
         * Build the PlatformStatsResponse object
         *
         * @return PlatformStatsResponse
         */
        public PlatformStatsResponse build() {
            return new PlatformStatsResponse(Optional.ofNullable(nextPage), Optional.ofNullable(platformStatsObjects.build()), ok,Optional.ofNullable(error), Optional.ofNullable(errorDetails));
        }
    }

}
