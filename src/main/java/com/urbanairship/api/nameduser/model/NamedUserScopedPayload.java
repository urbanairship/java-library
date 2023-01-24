package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public final class NamedUserScopedPayload extends NamedUserModelObject {

    private final List<NamedUserScope> payload;

    private NamedUserScopedPayload(List<NamedUserScope> payload) {
        this.payload = payload;
    }

    /**
     * NamedUserScopedPayload builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the scoped for the NamedUserScopedPayload.
     * @return scoped List Scope;
     */
    public List<NamedUserScope> getScoped() {
        return payload;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payload);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final NamedUserScopedPayload other = (NamedUserScopedPayload) obj;
        return Objects.equal(this.payload, other.payload);

    }

    @Override
    public String toString() {
        return "NamedUserScopedPayload{" +
                "payload=" + payload +
                '}';
    }

    /**
     * NamedUserScopedPayload Builder
     */
    public static class Builder {
        private List<NamedUserScope> payload = Lists.newArrayList();

        private Builder() { }

        /**
         * Add a scope to the NamedUserScopedPayload.
         * @param namedUserScope List Scope
         * @return Builder
         */
        public Builder addNamedUserScope(NamedUserScope namedUserScope) {
            payload.add(namedUserScope);
            return this;
        }

        public Builder addAllNamedUserScopes(List<NamedUserScope> namedUserScopes) {
            this.payload.addAll(namedUserScopes);
            return this;
        }

        public NamedUserScopedPayload build() {
            Preconditions.checkArgument(payload.size() > 0, "At least one scope must be present.");

            return new NamedUserScopedPayload(payload);
        }
    }
}