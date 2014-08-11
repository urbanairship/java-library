package com.urbanairship.api.client;

import java.util.List;

public final class APIListTagsResponse {

    private final List<String> tags;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListTagsResponse(List<String> tags){
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        APIListTagsResponse that = (APIListTagsResponse) o;

        if (!tags.equals(that.tags)) return false;

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
        private List<String> tags;

        private Builder() { }

        public Builder setTags(List<String> tags){
            this.tags = tags;
            return this;
        }

        public APIListTagsResponse build(){
            return new APIListTagsResponse(tags);
        }
    }
}
