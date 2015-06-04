/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;


public final class APIReportsPushListingResponse {

    private final Optional<String> nextPage;
    private final ImmutableList<SinglePushInfoResponse> singlePushInfoResponseObjects;

    private APIReportsPushListingResponse(String nextPage,
                                          ImmutableList<SinglePushInfoResponse> singlePushInfoResponseObjects) {
        this.nextPage = Optional.fromNullable(nextPage);
        this.singlePushInfoResponseObjects = singlePushInfoResponseObjects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getNextPage() {
        return nextPage;
    }

    public ImmutableList<SinglePushInfoResponse> getSinglePushInfoResponseObjects() {
        return singlePushInfoResponseObjects;
    }

    @Override
    public String toString() {
        return "APIReportsListingResponse{" +
                "nextPage=" + nextPage +
                ", singlePushInfoResponseObjects=" + singlePushInfoResponseObjects +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, singlePushInfoResponseObjects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIReportsPushListingResponse other = (APIReportsPushListingResponse) obj;
        return Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.singlePushInfoResponseObjects, other.singlePushInfoResponseObjects);
    }

    public static class Builder {

        private String nextPage;
        private ImmutableList.Builder<SinglePushInfoResponse> singlePushInfoResponseBuilder = ImmutableList.builder();

        private Builder() {
        }

        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        public Builder addPushInfoResponse(SinglePushInfoResponse value) {
            this.singlePushInfoResponseBuilder.add(value);
            return this;
        }

        public Builder addAllPushInfoResponses(Iterable<? extends SinglePushInfoResponse> value) {
            this.singlePushInfoResponseBuilder.addAll(value);
            return this;
        }

        public APIReportsPushListingResponse build() {
            return new APIReportsPushListingResponse(nextPage, singlePushInfoResponseBuilder.build());
        }
    }
}
