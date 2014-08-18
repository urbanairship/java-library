package com.urbanairship.api.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class APIListTagsResponse {

    private final ImmutableList<String> tags;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListTagsResponse(ImmutableList<String> tags){
        this.tags = tags;
    }

    public List<String> getTags() { return tags; }

    @Override
    public String toString() {
        return "APIListTagResponse{" +
                "tags=" + tags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        APIListTagsResponse that = (APIListTagsResponse) o;

        if (!tags.equals(that.tags)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }

    /**
     * APIListTagResponse Builder
     */
    public static class Builder {
        private ImmutableList.Builder<String> tags = ImmutableList.builder();

        private Builder() { }

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
