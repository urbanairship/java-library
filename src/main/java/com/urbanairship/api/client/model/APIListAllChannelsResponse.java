/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.information.model.ChannelView;

public final class APIListAllChannelsResponse {

    private final boolean ok;
    private final Optional<String> nextPage;
    private final ImmutableList<ChannelView> channelObjects;

    private APIListAllChannelsResponse(boolean ok, String nextPage, ImmutableList<ChannelView> channelObjects) {
        this.ok = ok;
        this.nextPage = Optional.fromNullable(nextPage);
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

    public ImmutableList<ChannelView> getChannelObjects() {
        return channelObjects;
    }

    @Override
    public String toString() {
        return "APIListAllChannelsResponse{" +
                "ok=" + ok +
                ", nextPage='" + nextPage + '\'' +
                ", channelObjects=" + channelObjects +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, nextPage, channelObjects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIListAllChannelsResponse other = (APIListAllChannelsResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.channelObjects, other.channelObjects);
    }

    public static class Builder {

        private boolean ok;
        private String nextPage;
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

        public Builder addChannel(ChannelView value) {
            this.channelObjects.add(value);
            return this;
        }

        public Builder addAllChannels(Iterable<? extends ChannelView> value) {
            this.channelObjects.addAll(value);
            return this;
        }

        public APIListAllChannelsResponse build() {
            return new APIListAllChannelsResponse(ok, nextPage, channelObjects.build());
        }
    }
}
