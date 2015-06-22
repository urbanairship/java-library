package com.urbanairship.api.channel.information.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.information.util.MapUtil;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        private Map<String, Set<String>> audience = new HashMap<String, Set<String>>();
        private Map<String, Set<String>> addTags = null;
        private Map<String, Set<String>> removeTags = null;
        private Map<String, Set<String>> setTags = null;

        private Builder() {
        }

        public Builder addIOSChannel(String channel) {
            String key = "ios_channel";
            Set<String> channels = new HashSet<String>();
            channels.add(channel);
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addIOSChannels(String ... channels) {
            String key = "ios_channel";
            Set<String> channelSet = new HashSet<String>();
            channelSet.addAll(Arrays.asList(channels));
            MapUtil.appendMapValues(key, channelSet, this.audience);
            return this;
        }

        public Builder addIOSChannels(Set<String> channels) {
            String key = "ios_channel";
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addAndroidChannel(String channel) {
            String key = "android_channel";
            Set<String> channels = new HashSet<String>();
            channels.add(channel);
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addAndroidChannels(String ... channels) {
            String key = "android_channel";
            Set<String> channelSet = new HashSet<String>();
            channelSet.addAll(Arrays.asList(channels));
            MapUtil.appendMapValues(key, channelSet, this.audience);
            return this;
        }

        public Builder addAndroidChannels(Set<String> channels) {
            String key = "android_channel";
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addAmazonChannel(String channel) {
            String key = "amazon_channel";
            Set<String> channels = new HashSet<String>();
            channels.add(channel);
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addAmazonChannels(String ... channels) {
            String key = "amazon_channel";
            Set<String> channelSet = new HashSet<String>();
            channelSet.addAll(Arrays.asList(channels));
            MapUtil.appendMapValues(key, channelSet, this.audience);
            return this;
        }

        public Builder addAmazonChannels(Set<String> channels) {
            String key = "amazon_channel";
            MapUtil.appendMapValues(key, channels, this.audience);
            return this;
        }

        public Builder addTags(String tagGroup, Set<String> tags) {
            if (addTags == null) {
                addTags = new HashMap<String, Set<String>>();
            }
            MapUtil.appendMapValues(tagGroup, tags, this.addTags);
            return this;
        }

        public Builder removeTags(String tagGroup, Set<String> tags) {
            if (removeTags == null) {
                removeTags = new HashMap<String, Set<String>>();
            }
            MapUtil.appendMapValues(tagGroup, tags, this.removeTags);
            return this;
        }

        public Builder setTags(String tagGroup, Set<String> tags) {
            if (setTags == null) {
                setTags = new HashMap<String, Set<String>>();
            }
            MapUtil.appendMapValues(tagGroup, tags, this.setTags);
            return this;
        }

        public TagMutationPayload build() {
            Preconditions.checkArgument(addTags != null || removeTags != null || setTags != null,
                "Tag mutation action required");
            if (setTags != null) {
                Preconditions.checkArgument(removeTags == null && addTags == null,
                    "Tag setting cannot coexist with tag removal or addition");
            }

            return new TagMutationPayload(MapUtil.immutableMapConverter(audience),
                addTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(MapUtil.immutableMapConverter(addTags)),
                removeTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(MapUtil.immutableMapConverter(removeTags)),
                setTags == null ? Optional.<ImmutableMap<String,ImmutableSet<String>>>absent() : Optional.fromNullable(MapUtil.immutableMapConverter(setTags)));
        }
    }
}
