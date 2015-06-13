package com.urbanairship.api.channel.information.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;

public class TagMutationPayload extends PushModelObject {

    private final ImmutableMap<String, ImmutableSet<String>> audience;
    private final Optional<ImmutableMap<String, ImmutableSet<String>>> addTags;
    private final Optional<ImmutableMap<String, ImmutableSet<String>>> removeTags;
    private final Optional<ImmutableMap<String, ImmutableSet<String>>> setTags;

    private TagMutationPayload(ImmutableMap<String, ImmutableSet<String>> audience,
                               Optional<ImmutableMap<String, ImmutableSet<String>>> addTags,
                               Optional<ImmutableMap<String, ImmutableSet<String>>> removeTags,
                               Optional<ImmutableMap<String, ImmutableSet<String>>> setTags) {
        this.audience = audience;
        this.addTags = addTags;
        this.removeTags = removeTags;
        this.setTags = setTags;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public ImmutableMap<String, ImmutableSet<String>> getAudience() {
        return audience;
    }

    public Optional<ImmutableMap<String, ImmutableSet<String>>> getAddedTags() {
        return addTags;
    }

    public Optional<ImmutableMap<String, ImmutableSet<String>>> getRemovedTags() {
        return removeTags;
    }

    public Optional<ImmutableMap<String, ImmutableSet<String>>> getSetTags() {
        return setTags;
    }

    @Override
    public String toString() {
        return "AddRemoveSet{" +
            "audience=" + audience +
            ", addTags=" + addTags +
            ", removeTags=" + removeTags +
            ", setTags=" + setTags +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(audience, addTags, removeTags, setTags);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TagMutationPayload other = (TagMutationPayload) obj;
        return Objects.equal(this.audience, other.audience) &&  Objects.equal(this.addTags, other.addTags) && Objects.equal(this.removeTags, other.removeTags) && Objects.equal(this.setTags, other.setTags);
    }


    public static class Builder {
        private ImmutableMap<String, ImmutableSet<String>> audience = null;
        private ImmutableMap.Builder<String, ImmutableSet<String>> addTags = null;
        private ImmutableMap.Builder<String, ImmutableSet<String>> removeTags = null;
        private ImmutableMap.Builder<String, ImmutableSet<String>> setTags = null;

        private Builder() {
        }

        public Builder setAudience(ImmutableMap<String, ImmutableSet<String>> value) {
            this.audience = value;
            return this;
        }

        public Builder addTags(String tagGroup, ImmutableSet<String> tags) {
            if (addTags == null) {
                addTags = ImmutableMap.builder();
            }
            this.addTags.put(tagGroup, tags);
            return this;
        }

        public Builder removeTags(String tagGroup, ImmutableSet<String> tags) {
            if (removeTags == null) {
                removeTags = ImmutableMap.builder();
            }
            this.removeTags.put(tagGroup, tags);
            return this;
        }

        public Builder setTags(String tagGroup, ImmutableSet<String> tags) {
            if (setTags == null) {
                setTags = ImmutableMap.builder();
            }
            this.setTags.put(tagGroup, tags);
            return this;
        }

        public TagMutationPayload build() {
            ImmutableSet channelTypes = ImmutableSet.builder().add("ios_channel", "android_channel", "amazon_channel").build();
            for (String key : audience.keySet()) {
                Preconditions.checkArgument(channelTypes.contains(key),
                    "Audience must be a channel type");
            }
            Preconditions.checkArgument(addTags != null || removeTags != null || setTags != null,
                "Tag mutation action required");
            if (setTags != null) {
                Preconditions.checkArgument(removeTags == null && addTags == null,
                    "Tag setting cannot coexist with tag removal or addition");
            }

            return new TagMutationPayload(audience,
                addTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(addTags.build()),
                removeTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(removeTags.build()),
                setTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(setTags.build()));
        }
    }
}
