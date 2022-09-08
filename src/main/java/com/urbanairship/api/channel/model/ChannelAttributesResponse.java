package com.urbanairship.api.channel.model;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

public class ChannelAttributesResponse {
    private final boolean ok;
    private final Optional<String> warning;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;


    private ChannelAttributesResponse(Builder builder) {
        this.ok = builder.ok;
        this.warning = Optional.ofNullable(builder.warning);
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
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

    public Optional<String> getError() {
        return error;
    }

    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "ChannelAttributesResponse{" +
                "ok=" + ok +
                ", warning=" + warning +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, warning, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChannelAttributesResponse other = (ChannelAttributesResponse) obj;
        return Objects.equal(this.ok, other.ok) && 
            Objects.equal(this.warning, other.warning) &&
            Objects.equal(this.error, other.error) &&
            Objects.equal(this.errorDetails, other.errorDetails);
    }

    public static class Builder {
        boolean ok;
        String warning;
        String error;
        ErrorDetails errorDetails;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setWarning(String warning) {
            this.warning = warning;
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

        public ChannelAttributesResponse build() {
            return new ChannelAttributesResponse(this);
        }
    }
}
