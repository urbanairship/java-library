/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;

import com.google.common.base.Objects;
import com.urbanairship.api.channel.information.model.ChannelView;

public final class APIListSingleChannelResponse {

    private final boolean ok;
    private final ChannelView channelObject;

    private APIListSingleChannelResponse(boolean ok, ChannelView channelObject) {
        this.ok = ok;
        this.channelObject = channelObject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
        return ok;
    }

    public ChannelView getChannelObject() {
        return channelObject;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelObject);
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
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.channelObject, other.channelObject);
    }

    @Override
    public String toString() {
        return "APIListSingleChannelResponse{" +
                "ok=" + ok +
                ", channelObject=" + channelObject +
                '}';
    }

    public static class Builder {

        private boolean ok;
        private ChannelView channelObject;

        private Builder() {
        }

        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        public Builder setChannelObject(ChannelView value) {
            this.channelObject = value;
            return this;
        }

        public APIListSingleChannelResponse build() {
            return new APIListSingleChannelResponse(ok, channelObject);
        }
    }
}

