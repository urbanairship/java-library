/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

package com.urbanairship.api.tags.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

public class TagListListingResponse {
    private final boolean ok;
    private final ImmutableList<TagListView> TagListView;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private TagListListingResponse(
        @JsonProperty("ok") Boolean ok,
        @JsonProperty("lists") ImmutableList<TagListView> TagListView,
        @JsonProperty("error") String error,
        @JsonProperty("details") ErrorDetails errorDetails
    ) {
        this.ok = ok;
        this.TagListView = TagListView;
        this.error = Optional.fromNullable(error);
        this.errorDetails = Optional.fromNullable(errorDetails);
    }

    /**
     * New TagListListingResponse builder.
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
     * Get a list of tag lists.
     *
     * @return An ImmutableList of TagListView
     */
    public ImmutableList<TagListView> getTagListView() {
        return TagListView;
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
        return "TagListListingResponse{" +
                "ok=" + ok +
                ", TagListView=" + TagListView +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, TagListView, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TagListListingResponse other = (TagListListingResponse) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.TagListView, other.TagListView) &&
                Objects.equal(this.error, other.error) &&
                Objects.equal(this.errorDetails, other.errorDetails);
    }


    public static class Builder {
        private boolean ok;
        private ImmutableList.Builder<TagListView> TagListObjects = ImmutableList.builder();
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
         * Add a tag list object for tag list listing.
         *
         * @param TagListObject TagListView
         * @return Builder
         */
        public Builder addTagList(TagListView TagListObject) {
            this.TagListObjects.add(TagListObject);
            return this;
        }

        /**
         * Add all the tag list objects for tag list listings
         *
         * @param TagListObjects Iterable of TagListView objects
         * @return Builder
         */
        public Builder addAllTagList(Iterable<? extends TagListView> TagListObjects) {
            this.TagListObjects.addAll(TagListObjects);
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
         * Build the TagListListingResponse object
         *
         * @return ChannelResponse
         */
        public TagListListingResponse build() {
            return new TagListListingResponse(ok, TagListObjects.build(), error, errorDetails);
        }
    }
}