/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

public class StaticListListingResponse {
    private final boolean ok;
    private final ImmutableList<StaticListView> staticListViews;

    private StaticListListingResponse(Builder builder) {
        this.ok = builder.ok;
        this.staticListViews = builder.staticListObjects.build();
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

    @Override
    public String toString() {
        return "StaticListListingResponse{" +
                "ok=" + ok +
                ", staticListViews=" + staticListViews +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, staticListViews);
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
                Objects.equal(this.staticListViews, other.staticListViews);
    }


    public static class Builder {
        private boolean ok;
        private ImmutableList.Builder<StaticListView> staticListObjects = ImmutableList.builder();

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
         * Build the StaticListListingResponse object
         *
         * @return ChannelResponse
         */
        public StaticListListingResponse build() {
            return new StaticListListingResponse(this);
        }
    }
}