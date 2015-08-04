/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class ListAllSchedulesResponse {

    private final boolean ok;
    private final int count;
    private final int totalCount;
    private final String nextPage;
    private final ImmutableList<SchedulePayload> scheduleObjects;

    private ListAllSchedulesResponse(boolean ok, int count, int totalCount, String nextPage, ImmutableList<SchedulePayload> response) {
        this.ok = ok;
        this.count = count;
        this.totalCount = totalCount;
        this.nextPage = nextPage;
        this.scheduleObjects = response;
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

    public String getNext_Page() {
        return nextPage;
    }

    public List<SchedulePayload> getSchedules() {
        return scheduleObjects;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, count, totalCount, nextPage, scheduleObjects);
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
        return  Objects.equal(this.ok, other.ok) && Objects.equal(this.count, other.count) && Objects.equal(this.totalCount, other.totalCount) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.scheduleObjects, other.scheduleObjects);
    }

    @Override
    public String toString() {

        return "APIListScheduleResponse{" +
                "ok=" + ok +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", nextPage=" + nextPage +
                ", scheduleObjects=" + scheduleObjects +
                '}';
    }

    /**
     * APIListScheduleResponse Builder
     */
    public static class Builder {

        private boolean ok = false;
        private int count;
        private int total_count;
        private String next_page;
        private ImmutableList.Builder<SchedulePayload> scheduleresponse = ImmutableList.builder();

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

        public Builder addSchedule(SchedulePayload schedule) {
            this.scheduleresponse.add(schedule);
            return this;
        }

        public Builder addAllSchedule(Iterable<? extends SchedulePayload> schedulelist) {
            this.scheduleresponse.addAll(schedulelist);
            return this;
        }

        public ListAllSchedulesResponse build() {
            Preconditions.checkNotNull(count, "count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(total_count, "total count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(scheduleresponse, "sch must be set to build APIListScheduleResponse");
            return new ListAllSchedulesResponse(ok, count, total_count, next_page, scheduleresponse.build());
        }
    }
}
