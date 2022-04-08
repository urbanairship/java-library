package com.urbanairship.api.subscriptionlists.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Objects;

public class SubscriptionListListingResponse {
    private final Boolean ok;
    private final ImmutableList<SubscriptionListView> subscriptionListView;
    private final String error;
    private final ErrorDetails errorDetails;

    public SubscriptionListListingResponse(
            @JsonProperty("ok") Boolean ok,
            @JsonProperty("subscription_lists") ImmutableList<SubscriptionListView> subscriptionListView,
            @JsonProperty("error") String error,
            @JsonProperty("details") ErrorDetails errorDetails) {
        this.ok = ok;
        this.subscriptionListView = subscriptionListView;
        this.error = error;
        this.errorDetails = errorDetails;
    }

    public Boolean getOk() {
        return ok;
    }

    public ImmutableList<SubscriptionListView> getSubscriptionListView() {
        return subscriptionListView;
    }

    public String getError() {
        return error;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionListListingResponse that = (SubscriptionListListingResponse) o;
        return Objects.equals(ok, that.ok) &&
                Objects.equals(subscriptionListView, that.subscriptionListView) &&
                Objects.equals(error, that.error) &&
                Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ok, subscriptionListView, error, errorDetails);
    }

    @Override
    public String toString() {
        return "SubscriptionListsListingRequest{" +
                "ok=" + ok +
                ", subscriptionListView=" + subscriptionListView +
                ", error='" + error + '\'' +
                ", errorDetails=" + errorDetails +
                '}';
    }
}
