/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.List;
import java.util.Optional;

public final class ListAllSchedulesResponse {

    private final boolean ok;
    private final int count;
    private final int totalCount;
    private final Optional<String> nextPage;
    private final ImmutableList<SchedulePayloadResponse> scheduleObjects;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private ListAllSchedulesResponse(boolean ok, int count, int totalCount,  Optional<String> nextPage, ImmutableList<SchedulePayloadResponse> response, String error, ErrorDetails errorDetails) {
        this.ok = ok;
        this.count = count;
        this.totalCount = totalCount;
        this.nextPage = nextPage;
        this.scheduleObjects = response;
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
        return ok;
    }

    public int getCount() {
        return count;
    }

    public int getTotal_Count() {
        return totalCount;
    }

    public Optional<String> getNext_Page() {
        return nextPage;
    }

    public List<SchedulePayloadResponse> getSchedules() {
        return scheduleObjects;
    }

    public Optional<String> getError() {
        return error;
    }

    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, count, totalCount, nextPage, scheduleObjects, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ListAllSchedulesResponse other = (ListAllSchedulesResponse) obj;
        return  Objects.equal(this.ok, other.ok) && Objects.equal(this.count, other.count) && Objects.equal(this.totalCount, other.totalCount) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.scheduleObjects, other.scheduleObjects) && Objects.equal(this.error, other.error) && Objects.equal(this.errorDetails, other.errorDetails);
    }

    @Override
    public String toString() {

        return "APIListScheduleResponse{" +
                "ok=" + ok +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", nextPage=" + nextPage +
                ", scheduleObjects=" + scheduleObjects +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    /**
     * APIListScheduleResponse Builder
     */
    public static class Builder {

        private boolean ok = false;
        private int count;
        private int total_count;
        private String next_page = null;
        private ImmutableList.Builder<SchedulePayloadResponse> scheduleresponse = ImmutableList.builder();
        private String error = null;
        private ErrorDetails errorDetails = null;
        
        private Builder() {
        }

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public Builder setTotalCount(int total_count) {
            this.total_count = total_count;
            return this;
        }

        public Builder setNextPage(String next_page) {
            this.next_page = next_page;
            return this;
        }

        public Builder addSchedule(SchedulePayloadResponse schedule) {
            this.scheduleresponse.add(schedule);
            return this;
        }

        public Builder addAllSchedule(Iterable<? extends SchedulePayloadResponse> schedulelist) {
            this.scheduleresponse.addAll(schedulelist);
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

        public ListAllSchedulesResponse build() {
            Preconditions.checkNotNull(count, "count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(total_count, "total count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(scheduleresponse, "sch must be set to build APIListScheduleResponse");
            return new ListAllSchedulesResponse(ok, count, total_count, Optional.ofNullable(next_page), scheduleresponse.build(), error, errorDetails);
        }
    }
}
