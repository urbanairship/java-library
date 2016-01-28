/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelView;

/**
 * Represents a single named user object.
 */
public class NamedUserView {

    private final String namedUserId;
    private final ImmutableMap<String, ImmutableSet<String>> namedUserTags;
    private final ImmutableSet<ChannelView> channelViews;

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
                          ImmutableSet<ChannelView> channelViews) {
        this.namedUserId = namedUserId;
        this.namedUserTags = namedUserTags;
        this.channelViews = channelViews;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamedUserView that = (NamedUserView) o;

        if (!channelViews.equals(that.channelViews)) return false;
        if (!namedUserId.equals(that.namedUserId)) return false;
        if (!namedUserTags.equals(that.namedUserTags)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = namedUserId.hashCode();
        result = 31 * result + namedUserTags.hashCode();
        result = 31 * result + channelViews.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NamedUserView{" +
            "namedUserId='" + namedUserId + '\'' +
            ", namedUserTags=" + namedUserTags +
            ", channelViews=" + channelViews +
            '}';
    }

    public final static class Builder {
        private String namedUserId = null;
        private ImmutableMap<String, ImmutableSet<String>> namedUserTags = null;
        private ImmutableSet<ChannelView> channelViews = null;

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
         * Build the NamedUserView object
         * @return NamedUserView
         */
        public NamedUserView build() {

            return new NamedUserView(
                namedUserId,
                namedUserTags,
                channelViews
            );
        }
    }
}