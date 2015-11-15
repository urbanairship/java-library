package com.urbanairship.api.nameduser.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelView;

public class NamedUserView {
    private final String namedUserId;
    private final ImmutableMap<String, ImmutableSet<String>> namedUserTags;
    private final ImmutableSet<ChannelView> channelViews;

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

    public String getNamedUserId() {
        return namedUserId;
    }

    public ImmutableMap<String, ImmutableSet<String>> getNamedUserTags() {
        return namedUserTags;
    }

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

        public Builder setNamedUserId(String namedUserId) {
            this.namedUserId = namedUserId;
            return this;
        }

        public Builder setNamedUserTags(ImmutableMap<String, ImmutableSet<String>> namedUserTags) {
            this.namedUserTags = namedUserTags;
            return this;
        }

        public Builder setChannelViews(ImmutableSet<ChannelView> channelViews) {
            this.channelViews = channelViews;
            return this;
        }

        public NamedUserView build() {

            return new NamedUserView(
                namedUserId,
                namedUserTags,
                channelViews
            );
        }
    }
}