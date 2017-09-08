package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;

public class OpenChannelResponse {
    private final boolean ok;
    private final String channel_id;

    private OpenChannelResponse(Builder builder) {
        this.ok = builder.ok;
        this.channel_id = builder.channel_id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isOk() {
        return ok;
    }

    public String getChannel_id() {
        return channel_id;
    }

    @Override
    public String toString() {
        return "OpenChannelResponse{" +
                "ok=" + ok +
                ", channel_id='" + channel_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChannelResponse that = (OpenChannelResponse) o;
        return ok == that.ok &&
                Objects.equal(channel_id, that.channel_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channel_id);
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
