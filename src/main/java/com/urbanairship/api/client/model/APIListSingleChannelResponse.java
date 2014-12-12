/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.urbanairship.api.channel.information.model.ChannelView;

public final class APIListSingleChannelResponse {

    private final ChannelView channelObject;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListSingleChannelResponse(ChannelView channelObject) {
        this.channelObject = channelObject;
    }

    public ChannelView getChannelObject() {
        return channelObject;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(channelObject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIListSingleChannelResponse other = (APIListSingleChannelResponse) obj;
        return Objects.equal(this.channelObject, other.channelObject);
    }

    @Override
    public String toString() {
        return "APIListSingleChannelResponse{" +
                "channelObject=" + channelObject +
                '}';
    }

    public static class Builder {

        private ChannelView channelObject;

        private Builder() { }

        public Builder setChannelObject(ChannelView value) {
            this.channelObject = value;
            return this;
        }

        public APIListSingleChannelResponse build() {
            return new APIListSingleChannelResponse(channelObject);
        }
    }
}

