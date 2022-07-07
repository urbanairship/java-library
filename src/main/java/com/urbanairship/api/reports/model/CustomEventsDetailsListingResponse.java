/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Objects;
import java.util.Optional;

public class CustomEventsDetailsListingResponse {
    private final boolean ok;
    private final Float totalValue;
    private final Integer totalCount; 
    private final String next_page;
    private final ImmutableList<CustomEventsDetailResponse> events;

    public CustomEventsDetailsListingResponse(
        @JsonProperty("ok") boolean ok,
        @JsonProperty("next_page") String next_page,
        @JsonProperty("total_value") Float totalValue,
        @JsonProperty("total_count") Integer totalCount,
        @JsonProperty("events") ImmutableList<CustomEventsDetailResponse> events
    ) {
        this.ok = ok;
        this.totalValue = totalValue;
        this.totalCount = totalCount;
        this.next_page = next_page;
        if (events == null){
            this.events = ImmutableList.of();
        }
        else {
            this.events = events;
        }
        
    }

    public boolean getOk() {
        return ok;
    }

    public Optional<Float> getTotalValue() {
        return Optional.ofNullable(totalValue);
    }

    public Optional<Integer> getTotalCount() {
        return Optional.ofNullable(totalCount);
    }

    /**
     * Get the next page attribute if present for a CustomEventsDetailsListingRequest.
     *
     * @return An optional string
     */
    public Optional<String> getNextPage() {
        return Optional.ofNullable(next_page);
    }

    /**
     * Get the list of event objects for a CustomEventsDetailsListingRequest
     *
     * @return An optional immutable list of event objects
     */
    public Optional<ImmutableList<CustomEventsDetailResponse>> getEvents() {
        return Optional.ofNullable(events);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventsDetailsListingResponse that = (CustomEventsDetailsListingResponse) o;
        return Objects.equals(next_page, that.next_page) &&
                Objects.equals(totalValue, that.totalValue) &&
                Objects.equals(totalCount, that.totalCount) &&
                Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next_page, totalValue, totalCount, events);
    }

    @Override
    public String toString() {
        return "CustomEventsDetailsListingResponse{" +
                "next_page=" + next_page +
                "totalValue=" + totalValue +
                "totalCount=" + totalCount +
                ", events=" + events +
                '}';
    }
}
