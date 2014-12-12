/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class APIListTagsResponse {

    private final ImmutableList<String> tags;

    private APIListTagsResponse(ImmutableList<String> tags) {
        this.tags = tags;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "APIListTagResponse{" +
                "tags=" + tags +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tags);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIListTagsResponse other = (APIListTagsResponse) obj;
        return Objects.equal(this.tags, other.tags);
    }

    /**
     * APIListTagResponse Builder
     */
    public static class Builder {
        private ImmutableList.Builder<String> tags = ImmutableList.builder();

        private Builder() {
        }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public Builder allAllTags(Iterable<? extends String> tags) {
            this.tags.addAll(tags);
            return this;
        }

        public APIListTagsResponse build() {
            Preconditions.checkNotNull(tags);
            return new APIListTagsResponse(tags.build());
        }
    }
}
