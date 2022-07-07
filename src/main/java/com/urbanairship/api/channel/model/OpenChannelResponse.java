package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class OpenChannelResponse {
    private final boolean ok;
    private final String channelId;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private OpenChannelResponse(Builder builder) {
        this.ok = builder.ok;
        this.channelId = builder.channel_id;
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
    
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

    public Optional<String> getError() {
        return error;
    }

    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "OpenChannelResponse{" +
                "ok=" + ok +
                ", channelId='" + channelId +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChannelResponse that = (OpenChannelResponse) o;
        return ok == that.ok &&
                Objects.equal(channelId, that.channelId) &&
                Objects.equal(this.error, that.error) &&
                Objects.equal(this.errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelId, error, errorDetails);
    }

    public static class Builder {
        private boolean ok;
        private String channel_id;
        private String error;
        private ErrorDetails errorDetails;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setChannelId(String channel_id) {
            this.channel_id = channel_id;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }
    
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public OpenChannelResponse build() {
            return new OpenChannelResponse(this);
        }
    }
}
