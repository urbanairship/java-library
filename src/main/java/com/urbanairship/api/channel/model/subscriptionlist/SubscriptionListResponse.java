package com.urbanairship.api.channel.model.subscriptionlist;

import java.util.Optional;

public class SubscriptionListResponse {
    private final boolean ok;
    private final Optional<String> error;

    private SubscriptionListResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = Optional.ofNullable(builder.error);
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

    public static class Builder {
        private boolean ok;
        private String error = null;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public SubscriptionListResponse build() {
            return new SubscriptionListResponse(this);
        }
    }
}
