/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AddRemoveNamedUserFromTagPayload extends PushModelObject {

    String audienceName;
    private List<String> audience;

    private Map<String, List<String>> addedTags;
    private Map<String, List<String>> removedTags;

    private AddRemoveNamedUserFromTagPayload(String audienceName, List<String> audience, Map<String, List<String>> addedTags, Map<String, List<String>> removedTags) {
        this.audienceName = audienceName;
        this.audience = audience;
        this.addedTags = addedTags;
        this.removedTags = removedTags;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public List<String> getAudience() {
        return audience;
    }

    public Map<String, List<String>> getAddedTags() {
        return addedTags;
    }

    public Map<String, List<String>> getRemovedTags() {
        return removedTags;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static class Builder {

        private String audienceName;
        private List<String> audience;

        private Map<String, List<String>> addedTags = new HashMap<String, List<String>>();
        private Map<String, List<String>> removedTags = new HashMap<String, List<String>>();

        private Builder() {
        }

        public Builder setAudienceName(String audienceName) {
            this.audienceName = audienceName;
            return this;
        }

        public Builder addAudience(List<String> audience) {
            this.audience = audience;
            return this;
        }

        public Builder addTagsToTagGroup(String tagGroup, List<String> tags) {
            addedTags.put(tagGroup, tags);
            return this;
        }

        public Builder removeTagsFromTagGroup(String tagGroup, List<String> tags) {
            removedTags.put(tagGroup, tags);
            return this;
        }

        public AddRemoveNamedUserFromTagPayload build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(audienceName) &&
                            audience != null &&
                            audience.size() > 0,
                    "Audience name and audience must be provided");

            Preconditions.checkArgument((addedTags != null || removedTags != null) &&
                            (addedTags.keySet().size() > 0 || removedTags.keySet().size() > 0),
                    "Added tags or removed tags must be provided");


            return new AddRemoveNamedUserFromTagPayload(audienceName, audience, addedTags, removedTags);
        }
    }
}
