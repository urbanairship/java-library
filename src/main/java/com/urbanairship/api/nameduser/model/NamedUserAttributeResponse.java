package com.urbanairship.api.nameduser.model;

public class NamedUserAttributeResponse {
    private final boolean ok;

    private NamedUserAttributeResponse(Builder builder) {
        this.ok = builder.ok;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isOk() {
        return ok;
    }

    public static class Builder {
        boolean ok;

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public NamedUserAttributeResponse build() {
            return new NamedUserAttributeResponse(this);
        }
    }
}
