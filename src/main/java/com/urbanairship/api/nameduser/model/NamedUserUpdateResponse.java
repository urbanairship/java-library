package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class NamedUserUpdateResponse {
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ImmutableList<String>> attributeWarnings;
    private final Optional<ImmutableList<String>> tagWarnings;


    private NamedUserUpdateResponse(Builder builder) {
        this.ok = builder.ok;
        this.error = builder.error;
        this.attributeWarnings = builder.attributeWarnings;
        this.tagWarnings = builder.tagWarnings;

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

    public Optional<ImmutableList<String>> getAttributeWarnings() {
        return attributeWarnings;
    }

    public Optional<ImmutableList<String>> getTagWarnings() {
        return tagWarnings;
    }

    @Override
    public String toString() {
        return "NamedUserUpdateResponse{" +
                "ok=" + ok +
                ", error='" + error + '\'' +
                ", attributeWarnings=" + attributeWarnings +
                ", tagWarnings=" + tagWarnings +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, error, attributeWarnings, tagWarnings);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final NamedUserUpdateResponse other = (NamedUserUpdateResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.error, other.error) && Objects.equal(this.attributeWarnings, other.attributeWarnings) && Objects.equal(this.tagWarnings, other.tagWarnings);
    }

    public static class Builder {
        boolean ok;
        Optional<String> error;
        Optional<ImmutableList<String>> attributeWarnings;
        Optional<ImmutableList<String>> tagWarnings;


        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setError(Optional<String> string) {
            this.error = string;
            return this;
        }

        public Builder setAttributeWarnings(Optional<ImmutableList<String>> attributeWarnings) {
            this.attributeWarnings = attributeWarnings;
            return this;
        }

        public Builder setTagWarnings(Optional<ImmutableList<String>> tagWarnings) {
            this.tagWarnings = tagWarnings;
            return this;
        }

        public NamedUserUpdateResponse build() {
            return new NamedUserUpdateResponse(this);
        }
    }
}
