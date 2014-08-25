/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.schedule.model.SchedulePayload;

import java.util.List;

public final class APIListAllSchedulesResponse {

    private final int count;
    private final int totalCount;
    private final String nextPage;
    private final ImmutableList<SchedulePayload> scheduleObjects;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListAllSchedulesResponse(int count, int totalCount, String nextPage, ImmutableList<SchedulePayload> response){
        this.count = count;
        this.totalCount = totalCount;
        this.nextPage = nextPage;
        this.scheduleObjects = response;
    }

    public int getCount() { return count; }
    public int getTotal_Count() { return totalCount; }
    public String getNext_Page() { return nextPage; }
    public List<SchedulePayload> getSchedules() { return scheduleObjects; }

    @Override
    public String toString() {
        return "APIListScheduleResponse{" +
                "count=" + count +
                ", totalCount=" + totalCount +
                ", nextPage=" + nextPage +
                ", scheduleObjects=" + scheduleObjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass())  { return false; }

        APIListAllSchedulesResponse that = (APIListAllSchedulesResponse) o;

        if (count != that.count) { return false; }
        if (totalCount != that.totalCount) { return false; }
        if (nextPage != null ? !nextPage.equals(that.nextPage) : that.nextPage != null)  { return false; }
        if (scheduleObjects != null ? !scheduleObjects.equals(that.scheduleObjects) : that.scheduleObjects != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + totalCount;
        result = 31 * result + (nextPage != null ? nextPage.hashCode() : 0);
        result = 31 * result + (scheduleObjects != null ? scheduleObjects.hashCode() : 0);
        return result;
    }

    /**
     * APIListScheduleResponse Builder
     */
    public static class Builder {

        private int count;
        private int total_count;
        private String next_page;
        private ImmutableList.Builder<SchedulePayload> scheduleresponse = ImmutableList.builder();

        private Builder() { }

        public Builder setCount(int count){
            this.count = count;
            return this;
        }

        public Builder setTotalCount(int total_count){
            this.total_count = total_count;
            return this;
        }

        public Builder setNextPage(String next_page){
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

        public APIListAllSchedulesResponse build(){
            Preconditions.checkNotNull(count, "count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(total_count, "total count must be set to build APIListScheduleResponse");
            Preconditions.checkNotNull(scheduleresponse, "sch must be set to build APIListScheduleResponse");
            return new APIListAllSchedulesResponse(count, total_count, next_page, scheduleresponse.build());
        }
    }
}
