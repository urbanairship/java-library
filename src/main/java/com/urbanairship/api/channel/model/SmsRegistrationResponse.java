package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class SmsRegistrationResponse {
    private final boolean ok;
    private final Optional<String> channelId;
    private final Optional<String> status;
    private final Optional<String> errors;

    private SmsRegistrationResponse(Builder builder) {
        this.ok = builder.ok;
        this.channelId = Optional.fromNullable(builder.channelId);
        this.status = Optional.fromNullable(builder.status);
        this.errors = Optional.fromNullable(builder.errors);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isOk() {
        return ok;
    }

    public Optional<String> getChannelId() {
        return channelId;
    }

    public Optional<String> getStatus() {
        return status;
    }

    public Optional<String> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsRegistrationResponse that = (SmsRegistrationResponse) o;
        return ok == that.ok &&
                Objects.equal(channelId, that.channelId) &&
                Objects.equal(status, that.status) &&
                Objects.equal(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, channelId, status, errors);
    }

    public static class Builder {
        private boolean ok;
        private String channelId = null;
        private String status = null;
        private String errors = null;

        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setErrors(String errors) {
            this.errors = errors;
            return this;
        }

        public SmsRegistrationResponse build() {
            return new SmsRegistrationResponse(this);
        }
    }
}
