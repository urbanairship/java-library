package com.urbanairship.api.nameduser.model;

import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Supports multiple operations on a named user within a single request for a specified scope.
 * The supported operation is subscription_lists.
 * The behavior of each of these operations are the same as their individual request counterpart.
 */
public class NamedUserScope {

    private Set<NamedUserScopeType> scopeTypes;
    private Set<String> subscribeLists;
    private Set<String> unsubscribeLists;

    private NamedUserScope(Set<NamedUserScopeType> scopeTypes, Set<String> subscribeLists, Set<String> unsubscribeLists) {
        this.scopeTypes = scopeTypes;
        this.subscribeLists = subscribeLists;
        this.unsubscribeLists = unsubscribeLists;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    public Set<NamedUserScopeType> getScope() {
        return scopeTypes;
    }

    public Set<String> getSubscribeLists() {
        return subscribeLists;
    }

    public Set<String> getUnsubscribeLists() {
        return unsubscribeLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedUserScope namedUserScope1 = (NamedUserScope) o;
        return Objects.equals(scopeTypes, namedUserScope1.scopeTypes) && Objects.equals(subscribeLists, namedUserScope1.subscribeLists) && Objects.equals(unsubscribeLists, namedUserScope1.unsubscribeLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scopeTypes, subscribeLists, unsubscribeLists);
    }

    @Override
    public String toString() {
        return "Scope{" +
                "scopeTypes=" + scopeTypes +
                ", subscribe=" + subscribeLists +
                ", unsubscribe=" + unsubscribeLists +
                '}';
    }

    /**
     * Scope Builder
     */
    public static class Builder {

        private Set<NamedUserScopeType> scopeTypes;
        private Set<String> subscribeLists = new HashSet<>();
        private Set<String> unsubscribeLists = new HashSet<>();;

        /**
         * Set the scope list.
         * @param scopeTypes List of String
         * @return Builder
         */
        public Builder setScopes(Set<NamedUserScopeType> scopeTypes) {
            this.scopeTypes = scopeTypes;
            return this;
        }

        /**
         * Set the subscribe list.
         * @param subscribeLists List of String
         * @return Builder
         */
        public Builder setSubscribeLists(Set<String> subscribeLists) {
            this.subscribeLists = subscribeLists;
            return this;
        }

        /**
         * Set to unsubscribe list.
         * @param unsubscribeLists List of String
         * @return Builder
         */
        public Builder setUnsubscribeLists(Set<String> unsubscribeLists) {
            this.unsubscribeLists = unsubscribeLists;
            return this;
        }

        public NamedUserScope build() {
            Preconditions.checkNotNull(scopeTypes, "scopeTypes must be provided.");
            Preconditions.checkArgument(!subscribeLists.isEmpty() || !unsubscribeLists.isEmpty(), "You must provide at least subscribeLists or unsubscribeLists");

            return new NamedUserScope(scopeTypes, subscribeLists, unsubscribeLists);
        }
    }
}
