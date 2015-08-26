/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class ChannelResponse {
    private final boolean ok;
    private final Optional<String> nextPage;
    private final Optional<ChannelView> channelObject;
    private final Optional<ImmutableList<ChannelView>> channelObjects;

    private ChannelResponse() {
        this(false, null, null, null);
    }

    private ChannelResponse(boolean ok, String nextPage, Optional<ChannelView> channelObject, Optional<ImmutableList<ChannelView>> channelObjects) {
        this.ok = ok;
        this.nextPage = Optional.fromNullable(nextPage);
        this.channelObject = channelObject;
        this.channelObjects = channelObjects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
        return ok;
    }

    public Optional<String> getNextPage() {
        return nextPage;
    }

    public Optional<ChannelView> getChannelObject() {
        return channelObject;
    }

    public Optional<ImmutableList<ChannelView>> getChannelObjects() {
        return channelObjects;
    }

    @Override
    public String toString() {
        return "APIListAllChannelsResponse{" +
            "ok=" + ok +
            ", nextPage='" + nextPage + '\'' +
            ", channelObject=" + channelObject +
            ", channelObjects=" + channelObjects +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, nextPage, channelObject, channelObjects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChannelResponse other = (ChannelResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.channelObject, other.channelObject) && Objects.equal(this.channelObjects, other.channelObjects);
    }

    public static class Builder {

        private boolean ok;
        private String nextPage = null;
        private ChannelView channelObject = null;
        private ImmutableList.Builder<ChannelView> channelObjects = ImmutableList.builder();

        private Builder() {
        }

        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        public Builder setChannelObject(ChannelView value) {
            this.channelObject = value;
            return this;
        }

        public Builder addChannel(ChannelView value) {
            this.channelObjects.add(value);
            return this;
        }

        public Builder addAllChannels(Iterable<? extends ChannelView> value) {
            this.channelObjects.addAll(value);
            return this;
        }

        public ChannelResponse build() {
            return new ChannelResponse(ok, nextPage,Optional.fromNullable(channelObject), Optional.fromNullable(channelObjects.build()));
        }
    }
}