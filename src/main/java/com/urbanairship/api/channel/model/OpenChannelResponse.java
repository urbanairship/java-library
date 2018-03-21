package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;

public class OpenChannelResponse {
    private final boolean ok;
    private final String channelId;

    private OpenChannelResponse(Builder builder) {
        this.ok = builder.ok;
        this.channelId = builder.channel_id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
        return ok;
    }

    public String getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "OpenChannelResponse{" +
                "ok=" + ok +
                ", channelId='" + channelId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChannelResponse that = (OpenChannelResponse) o;
        return ok == that.ok &&
                Objects.equal(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelId);
    }

    public static class Builder {
        private boolean ok;
        private String channel_id;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setChannelId(String channel_id) {
            this.channel_id = channel_id;
            return this;
        }

        public OpenChannelResponse build() {
            return new OpenChannelResponse(this);
        }
    }
}
