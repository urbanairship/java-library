package com.urbanairship.api.segments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class SegmentListingResponse {
    private final Optional<String> nextPage;
    private final ImmutableList<SegmentListingView> segmentListingViews;

    private SegmentListingResponse(String nextPage, ImmutableList<SegmentListingView> segmentListingViews) {
        this.nextPage = Optional.fromNullable(nextPage);
        this.segmentListingViews = segmentListingViews;
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

    @Override
    public String toString() {
        return "SegmentListingResponse{" +
                "nextPage=" + nextPage +
                ", segments=" + segmentListingViews +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, segmentListingViews);
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
                Objects.equal(this.segmentListingViews, other.segmentListingViews);
    }

    public final static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<SegmentListingView> segmentObjects = ImmutableList.builder();

        private Builder() {
        }

        /**
         * Set the next page.
         *
         * @param nextPage
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

        public SegmentListingResponse build() {
            return new SegmentListingResponse(nextPage, segmentObjects.build());
        }
    }
}
