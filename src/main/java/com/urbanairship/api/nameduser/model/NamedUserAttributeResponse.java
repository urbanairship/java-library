package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.model.GenericResponse;

import java.util.Optional;

public class NamedUserAttributeResponse {
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;
    private final Optional<String> warning;

    private NamedUserAttributeResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
        this.warning = Optional.ofNullable(builder.warning);
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

    public Optional<String> getWarning() {
        return warning;
    }

    @Override
    public String toString() {
        return "NamedUserAttributeResponse{" +
                "ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                ", warning=" + warning +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, error, errorDetails, warning);
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
                && Objects.equal(this.errorDetails, other.errorDetails)
                && Objects.equal(this.warning, other.warning);
    }

    public static class Builder {
        boolean ok;
        private String error;
        private ErrorDetails errorDetails;
        private String warning;

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
        public Builder setWarning(String warning) {
            this.warning = warning;
            return this;
        }

        public NamedUserAttributeResponse build() {
            return new NamedUserAttributeResponse(this);
        }
    }
}
