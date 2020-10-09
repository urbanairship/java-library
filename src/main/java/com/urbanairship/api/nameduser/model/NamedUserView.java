/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelView;

import java.util.Map;

/**
 * Represents a single named user object.
 */
public class NamedUserView {

    private final String namedUserId;
    private final ImmutableMap<String, ImmutableSet<String>> namedUserTags;
    private final ImmutableSet<ChannelView> channelViews;
    private final ImmutableMap<String, String> attributes;
    private final ImmutableMap<String, String> userAttributes;

    /**
     * New NamedUserView builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private NamedUserView(String namedUserId,
                          ImmutableMap<String, ImmutableSet<String>> namedUserTags,
                          ImmutableSet<ChannelView> channelViews,
                          ImmutableMap<String, String> attributes,
                          ImmutableMap<String, String> userAttributes) {
        this.namedUserId = namedUserId;
        this.namedUserTags = namedUserTags;
        this.channelViews = channelViews;
        this.attributes = attributes;
        this.userAttributes = userAttributes;
    }

    /**
     * Get the named user ID.
     *
     * @return String
     */
    public String getNamedUserId() {
        return namedUserId;
    }

    /**
     * Get any associated tag groups and tags.
     *
     * @return ImmutableMap of tag groups and the relevant Immutable sets of tags
     */
    public ImmutableMap<String, ImmutableSet<String>> getNamedUserTags() {
        return namedUserTags;
    }

    /**
     * Get any associated channel objects.
     *
     * @return Set of ChannelView objects.
     */
    public ImmutableSet<ChannelView> getChannelViews() {
        return channelViews;
    }

    /**
     * Get map of attributes associated to the named user.
     *
     * @return ImmutableMap &lt;String,String&gt; attributes
     */
    public ImmutableMap<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Get map of user attributes on the named user.
     *
     * @return ImmutableMap &lt;String,String&gt; userAttributes
     */
    public ImmutableMap<String, String> getUserAttributes() {
        return userAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedUserView that = (NamedUserView) o;
        return Objects.equal(namedUserId, that.namedUserId) &&
                Objects.equal(namedUserTags, that.namedUserTags) &&
                Objects.equal(channelViews, that.channelViews) &&
                Objects.equal(attributes, that.attributes) &&
                Objects.equal(userAttributes, that.userAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(namedUserId, namedUserTags, channelViews, attributes, userAttributes);
    }

    @Override
    public String toString() {
        return "NamedUserView{" +
                "namedUserId='" + namedUserId + '\'' +
                ", namedUserTags=" + namedUserTags +
                ", channelViews=" + channelViews +
                ", attributes=" + attributes +
                ", userAttributes=" + userAttributes +
                '}';
    }

    public final static class Builder {
        private String namedUserId = null;
        private ImmutableMap<String, ImmutableSet<String>> namedUserTags = null;
        private ImmutableSet<ChannelView> channelViews = null;
        private ImmutableMap.Builder<String, String> attributes = ImmutableMap.builder();
        private ImmutableMap.Builder<String, String> userAttributes = ImmutableMap.builder();

        private Builder() { }

        /**
         * Set the named user ID.
         *
         * @param namedUserId String
         * @return Builder
         */
        public Builder setNamedUserId(String namedUserId) {
            this.namedUserId = namedUserId;
            return this;
        }

        /**
         * Set the associated tag groups and tags.
         *
         * @param namedUserTags Immutable map of tag groups and ImmutableSets of tags
         * @return Builder
         */
        public Builder setNamedUserTags(ImmutableMap<String, ImmutableSet<String>> namedUserTags) {
            this.namedUserTags = namedUserTags;
            return this;
        }

        /**
         * Set the associated channel objects.
         * @param channelViews Set of ChannelView objects.
         * @return Builder
         */
        public Builder setChannelViews(ImmutableSet<ChannelView> channelViews) {
            this.channelViews = channelViews;
            return this;
        }

        /**
         * Add all attributes to the named user.
         *
         * @param attributes Map of Strings attributes
         * @return Builder
         */
        public Builder addAllAttributes(Map<String, String> attributes) {
            for (String key : attributes.keySet()) {
                this.attributes.put(key, String.valueOf(attributes.get(key)));
            }
            return this;
        }

        /**
         * Add all user attributes to the named user.
         *
         * @param userAttributes Map of Strings userAttributes
         * @return Builder
         */
        public Builder addAllUserAttributes(Map<String, String> userAttributes) {
            for (String key : userAttributes.keySet()) {
                this.userAttributes.put(key, String.valueOf(userAttributes.get(key)));
            }
            return this;
        }

        /**
         * Build the NamedUserView object
         * @return NamedUserView
         */
        public NamedUserView build() {
            ImmutableMap<String, String> attributes = this.attributes.build();
            ImmutableMap<String, String> userAttributes = this.userAttributes.build();

            return new NamedUserView(
                namedUserId,
                namedUserTags,
                channelViews,
                attributes,
                userAttributes
            );
        }
    }
}