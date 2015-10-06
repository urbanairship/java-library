/*
 * Copyright (c) 2013-2015. Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * Push listing response object
 */
public class PushListingResponse {
    private final Optional<String> nextPage;
    private final Optional<ImmutableList<SinglePushInfoResponse>> pushInfoObjects;

    private PushListingResponse() {
        this(null, null);
    }

    private PushListingResponse(
            String nextPage,
            Optional<ImmutableList<SinglePushInfoResponse>> pushInfoObjects)
    {
        this.nextPage = Optional.fromNullable(nextPage);
        this.pushInfoObjects = pushInfoObjects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the next page if present for a push listing request
     *
     * @return An optional String
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get the list of push info objects if present for a push listing
     * request
     *
     * @return An Optional ImmutableList of SinglePushInfoResponse objects
     */
    public Optional<ImmutableList<SinglePushInfoResponse>> getPushInfoObjects() {
        return pushInfoObjects;
    }

    @Override
    public String toString() {
        return "PushInfoResponse{" +
                "nextPage='" + nextPage + '\'' +
                ", pushInfoObjects =" + pushInfoObjects +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, pushInfoObjects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PushListingResponse other = (PushListingResponse) obj;
        return Objects.equal(this.nextPage, other.nextPage) &&
                Objects.equal(this.pushInfoObjects, other.pushInfoObjects);
    }

    public static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<SinglePushInfoResponse> pushInfoObjects = ImmutableList.builder();

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
         * Add a push info object for push listing
         *
         * @param value SinglePushInfoResponse
         * @return Builder
         */
        public Builder addPushInfoObject(SinglePushInfoResponse value) {
            this.pushInfoObjects.add(value);
            return this;
        }

        /**
         * Add all push info objects for push listing
         *
         * @param value Iterable of SinglePushInfoResponse objects
         * @return
         */
        public Builder addPushInfoObjects(Iterable<? extends SinglePushInfoResponse> value) {
            this.pushInfoObjects.addAll(value);
            return this;
        }

        /**
         * Build the PushListingResponse object
         *
         * @return PushListingResponse
         */
        public PushListingResponse build() {
            return new PushListingResponse(nextPage, Optional.fromNullable(pushInfoObjects.build()));
        }
    }
}
