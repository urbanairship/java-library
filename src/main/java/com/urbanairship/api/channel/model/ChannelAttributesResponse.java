package com.urbanairship.api.channel.model;

import java.util.Optional;

public class ChannelAttributesResponse {
    private final boolean ok;
    Optional<String> warning;

    private ChannelAttributesResponse(Builder builder) {
        this.ok = builder.ok;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isOk() {
        return ok;
    }

    public Optional<String> getWarning() {
        return warning;
    }

    public static class Builder {
        boolean ok;
        String warning;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setWarning(String warning) {
            this.warning = warning;
            return this;
        }

        public ChannelAttributesResponse build() {
            return new ChannelAttributesResponse(this);
        }
    }
}
