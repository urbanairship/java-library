package com.urbanairship.api.channel.model.subscriptionlist;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class SubscriptionListResponse {
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private SubscriptionListResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);

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

    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "SubscriptionListResponse{" +
                "ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SubscriptionListResponse other = (SubscriptionListResponse) obj;
        return Objects.equal(this.ok, other.ok) && 
            Objects.equal(this.error, other.error) &&
            Objects.equal(this.errorDetails, other.errorDetails);
    }

    public static class Builder {
        private boolean ok;
        private String error = null;
        private ErrorDetails errorDetails = null;

        public Builder setOk(boolean ok) {
            this.ok = ok;
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

        public SubscriptionListResponse build() {
            return new SubscriptionListResponse(this);
        }
    }
}
