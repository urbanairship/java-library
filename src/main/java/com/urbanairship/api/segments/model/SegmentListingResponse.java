package com.urbanairship.api.segments.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class SegmentListingResponse {
    private final Optional<String> nextPage;
    private final ImmutableList<SegmentListingView> segmentListingViews;
    private final Optional<Boolean> ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private SegmentListingResponse(String nextPage, ImmutableList<SegmentListingView> segmentListingViews, Boolean ok, String error, ErrorDetails errorDetails) {
        this.nextPage = Optional.ofNullable(nextPage);
        this.segmentListingViews = segmentListingViews;
        this.ok = Optional.ofNullable(ok);
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
    }

    /**
     * New SegmentListingResponse builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * Get the next page.
     *
     * @return An Optional String
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get segment objects.
     *
     * @return An Optional ImmutableList of SegmentListingView objects
     */
    public ImmutableList<SegmentListingView> getSegmentListingViews() {
        return segmentListingViews;
    }

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public Optional<Boolean> getOk() {
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
        return "SegmentListingResponse{" +
                "nextPage=" + nextPage +
                ", segments=" + segmentListingViews +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, segmentListingViews, ok, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SegmentListingResponse other = (SegmentListingResponse) obj;

        return Objects.equal(this.nextPage, other.nextPage) &&
                Objects.equal(this.segmentListingViews, other.segmentListingViews) &&
                Objects.equal(ok, other.ok) &&
                Objects.equal(error, other.error) &&
                Objects.equal(errorDetails, other.errorDetails);
    }

    public final static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<SegmentListingView> segmentObjects = ImmutableList.builder();
        private boolean ok = true;
        private String error;
        private ErrorDetails errorDetails;

        private Builder() {
        }

        /**
         * Set the next page.
         *
         * @param nextPage String
         * @return Builder
         */
        public Builder setNextPage(String nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        /**
         * Add a segment object for segment listing.
         *
         * @param value SegmentListingView
         * @return Builder
         */
        public Builder addSegmentObject(SegmentListingView value) {
            this.segmentObjects.add(value);
            return this;
        }

        /**
         * Add all the segment objects for a segment listing.
         *
         * @param value Iterable of SegmentListingView objects
         * @return Builder
         */
        public Builder addAllSegmentObjects(Iterable<? extends SegmentListingView> value) {
            this.segmentObjects.addAll(value);
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

        public SegmentListingResponse build() {
            return new SegmentListingResponse(nextPage, segmentObjects.build(), ok, error, errorDetails);
        }
    }
}
