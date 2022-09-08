/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

public class AttributeListsListingResponse {
    private final boolean ok;
    private final ImmutableList<AttributeListsView> AttributeListsViews;

    private AttributeListsListingResponse(Builder builder) {
        this.ok = builder.ok;
        this.AttributeListsViews = builder.AttributeListsObjects.build();
    }

    /**
     * New AttributeListsListingResponse builder.
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
     * Get a list of attributes lists.
     *
     * @return An ImmutableList of AttributeLists
     */
    public ImmutableList<AttributeListsView> getAttributeListsViews() {
        return AttributeListsViews;
    }

    @Override
    public String toString() {
        return "AttributeListsListingResponse{" +
                "ok=" + ok +
                ", AttributeListsViews=" + AttributeListsViews +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, AttributeListsViews);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AttributeListsListingResponse other = (AttributeListsListingResponse) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.AttributeListsViews, other.AttributeListsViews);
    }


    public static class Builder {
        private boolean ok;
        private final ImmutableList.Builder<AttributeListsView> AttributeListsObjects = ImmutableList.builder();

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
         * Add a attribute list object for static list listing.
         *
         * @param attributeListsObject AttributeListsView
         * @return Builder
         */
        public Builder addAttributeLists(AttributeListsView attributeListsObject) {
            this.AttributeListsObjects.add(attributeListsObject);
            return this;
        }

        /**
         * Add all the attribute list objects for attribute list listings
         *
         * @param attributeListsObjects Iterable of AttributeListsView objects
         * @return Builder
         */
        public Builder addAllAttributeLists(Iterable<? extends AttributeListsView> attributeListsObjects) {
            this.AttributeListsObjects.addAll(attributeListsObjects);
            return this;
        }

        /**
         * Build the AttributeListsListingResponse object
         *
         * @return AttributeListsListingResponse
         */
        public AttributeListsListingResponse build() {
            return new AttributeListsListingResponse(this);
        }

    }
}