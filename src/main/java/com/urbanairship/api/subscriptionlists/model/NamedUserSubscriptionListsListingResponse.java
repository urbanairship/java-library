package com.urbanairship.api.subscriptionlists.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.model.GenericResponse;

import java.util.Objects;

public class NamedUserSubscriptionListsListingResponse extends GenericResponse {
    private final ImmutableList<ContactSubscriptionListView> namedUserSubscriptionListsView;

    public NamedUserSubscriptionListsListingResponse(
            @JsonProperty("ok") Boolean ok,
            @JsonProperty("subscription_lists") ImmutableList<ContactSubscriptionListView> namedUserSubscriptionListsView,
            @JsonProperty("operation_id") String operationId,
            @JsonProperty("error") String error,
            @JsonProperty("details") ErrorDetails errorDetails,
            @JsonProperty("error_code") Integer errorCode,
            @JsonProperty("warning") String warning)
    {
        super(ok, operationId, error, errorDetails, errorCode, warning);
        this.namedUserSubscriptionListsView = namedUserSubscriptionListsView;
    }


    public ImmutableList<ContactSubscriptionListView> getNamedUserSubscriptionListsView() {
        return namedUserSubscriptionListsView;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedUserSubscriptionListsListingResponse that = (NamedUserSubscriptionListsListingResponse) o;
        return Objects.equals(namedUserSubscriptionListsView, that.namedUserSubscriptionListsView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namedUserSubscriptionListsView);
    }

    @Override
    public String toString() {
        return "SubscriptionListsListingRequest{" +
                ", subscriptionListView=" + namedUserSubscriptionListsView +
                '}';
    }
}
