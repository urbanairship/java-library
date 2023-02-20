package com.urbanairship.api.subscriptionlists.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.nameduser.model.NamedUserScopeType;

import java.util.Objects;

public class ContactSubscriptionListView {
    private final ImmutableList<String> listIds;
    private final String scope;

    public ContactSubscriptionListView(
            @JsonProperty("list_ids") ImmutableList<String> listIds,
            @JsonProperty("scope") String scope){
        this.listIds = listIds;
        this.scope = scope;

    }

    public ImmutableList<String> getListIds() {
        return listIds;
    }

    public String getName() {
        return scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactSubscriptionListView that = (ContactSubscriptionListView) o;
        return Objects.equals(listIds, that.listIds) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listIds, scope);
    }

    @Override
    public String toString() {
        return "NamedUserSubscriptionListView{" +
                "listIds='" + listIds + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
