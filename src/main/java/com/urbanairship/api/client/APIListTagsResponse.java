package com.urbanairship.api.client;

import java.util.List;

public class APIListTagsResponse {

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
