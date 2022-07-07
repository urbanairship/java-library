/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class StaticListListingResponse {
    private final boolean ok;
    private final ImmutableList<StaticListView> staticListViews;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private StaticListListingResponse(Builder builder) {
        this.ok = builder.ok;
        this.staticListViews = builder.staticListObjects.build();
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
    }

    /**
     * New StaticListListingResponse builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
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
     * Get a list of static lists.
     *
     * @return An ImmutableList of StaticListLookupResponses
     */
    public ImmutableList<StaticListView> getStaticListViews() {
        return staticListViews;
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
        return "StaticListListingResponse{" +
                "ok=" + ok +
                ", staticListViews=" + staticListViews +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, staticListViews, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final StaticListListingResponse other = (StaticListListingResponse) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.staticListViews, other.staticListViews) &&
                Objects.equal(this.error, other.error) &&
                Objects.equal(this.errorDetails, other.errorDetails);
    }


    public static class Builder {
        private boolean ok;
        private ImmutableList.Builder<StaticListView> staticListObjects = ImmutableList.builder();
        private String error;
        private ErrorDetails errorDetails;

        /**
         * Set the ok status.
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        /**
         * Add a static list object for static list listing.
         *
         * @param staticListObject StaticListView
         * @return Builder
         */
        public Builder addStaticList(StaticListView staticListObject) {
            this.staticListObjects.add(staticListObject);
            return this;
        }

        /**
         * Add all the static list objects for static list listings
         *
         * @param staticListObjects Iterable of StaticListView objects
         * @return Builder
         */
        public Builder addAllStaticLists(Iterable<? extends StaticListView> staticListObjects) {
            this.staticListObjects.addAll(staticListObjects);
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
         * Build the StaticListListingResponse object
         *
         * @return ChannelResponse
         */
        public StaticListListingResponse build() {
            return new StaticListListingResponse(this);
        }
    }
}