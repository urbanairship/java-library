package com.urbanairship.api.subscriptionlists.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Objects;
import java.util.Optional;

public class SubscriptionListView {
    private final String listId;
    private final String name;
    private final String description;
    private final ImmutableList<String> scopes;
    private final Boolean defaultOptedIn;
    private final String messagingType;

    public SubscriptionListView(
            @JsonProperty("list_id") String listId,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("scopes") ImmutableList<String> scopes,
            @JsonProperty("default_opted_in") Boolean defaultOptedIn,
            @JsonProperty("messaging_type") String messagingType) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.scopes = scopes;
        this.defaultOptedIn = defaultOptedIn;
        this.messagingType = messagingType;

    }

    public String getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImmutableList<String> getScopes() {
        return scopes;
    }

    public Optional<Boolean> getDefaultOptedIn() {return Optional.ofNullable(defaultOptedIn);}

    public Optional<String> getMessagingType() {return Optional.ofNullable(messagingType);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionListView that = (SubscriptionListView) o;
        return Objects.equals(listId, that.listId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(scopes, that.scopes) &&
                Objects.equals(defaultOptedIn, that.defaultOptedIn) &&
                Objects.equals(messagingType, that.messagingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, name, description, scopes, defaultOptedIn, messagingType);
    }

    @Override
    public String toString() {
        return "SubscriptionListView{" +
                "listId='" + listId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", scopes=" + scopes +
                ", defaultOptedIn=" + defaultOptedIn +
                ", messagingType=" + messagingType +
                '}';
    }
}
