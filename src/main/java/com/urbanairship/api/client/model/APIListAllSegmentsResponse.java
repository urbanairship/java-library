/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

public final class APIListAllSegmentsResponse {

    private final String nextPage;
    private final ImmutableList<SegmentInformation> segments;

    private APIListAllSegmentsResponse(String nextPage, ImmutableList<SegmentInformation> segments) {
        this.nextPage = nextPage;
        this.segments = segments;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getNextPage() {
        return nextPage;
    }

    public ImmutableList<SegmentInformation> getSegments() {
        return segments;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, segments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIListAllSegmentsResponse other = (APIListAllSegmentsResponse) obj;
        return Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.segments, other.segments);
    }

    @Override
    public String toString() {
        return "APIListAllSegmentsResponse{" +
                "nextPage='" + nextPage + '\'' +
                ", segments=" + segments +
                '}';
    }

    public static class Builder {
        private String nextPage;
        private ImmutableList.Builder<SegmentInformation> segments = ImmutableList.builder();

        private Builder() {
        }

        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        public Builder setSegments(Iterable<? extends SegmentInformation> value) {
            this.segments.addAll(value);
            return this;
        }

        public APIListAllSegmentsResponse build() {
            return new APIListAllSegmentsResponse(nextPage, segments.build());
        }
    }
}

