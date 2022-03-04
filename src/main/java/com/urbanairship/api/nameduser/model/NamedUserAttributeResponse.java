package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.urbanairship.api.common.model.ErrorDetails;

public class NamedUserAttributeResponse {
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private NamedUserAttributeResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = Optional.fromNullable(builder.error);
        this.errorDetails = Optional.fromNullable(builder.errorDetails);
        
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
        return "NamedUserAttributeResponse{" +
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
        final NamedUserAttributeResponse other = (NamedUserAttributeResponse) obj;
        return Objects.equal(this.ok, other.ok)
                && Objects.equal(this.error, other.error) 
                && Objects.equal(this.errorDetails, other.errorDetails);
    }

    public static class Builder {
        boolean ok;
        private String error;
        private ErrorDetails errorDetails;

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

        public NamedUserAttributeResponse build() {
            return new NamedUserAttributeResponse(this);
        }
    }
}
