package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class ChannelUninstallResponse {
    private final boolean ok;
    private final Optional<String> error;

    private ChannelUninstallResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = builder.error;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isOk() {
        return ok;
    }

    public Optional<String> getError() {
        return error;
    }

    @Override
    public String toString() {
        return "NamedUserUpdateResponse{" +
                "ok=" + ok +
                ",error='" + error + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, error);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChannelUninstallResponse other = (ChannelUninstallResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.error, other.error);
    }

    public static class Builder {
        boolean ok;
        Optional<String> error;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setError(Optional<String> string) {
            this.error = string;
            return this;
        }

        public ChannelUninstallResponse build() {
            return new ChannelUninstallResponse(this);
        }
    }
}
