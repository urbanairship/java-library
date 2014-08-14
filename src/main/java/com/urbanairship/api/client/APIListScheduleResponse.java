package com.urbanairship.api.client;


import com.urbanairship.api.schedule.model.SchedulePayload;

import java.util.List;

public final class APIListScheduleResponse {

    private final int count;
    private final int totalCount;
    private final String nextPage;
    private final List<SchedulePayload> scheduleObjects;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListScheduleResponse(int count, int totalCount, String nextPage, List<SchedulePayload> response){
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

        APIListScheduleResponse that = (APIListScheduleResponse) o;

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
        private List<SchedulePayload> scheduleresponse;

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

        public Builder setSchedule(List<SchedulePayload> schedules){
            this.scheduleresponse = schedules;
            return this;
        }

        public APIListScheduleResponse build(){
            return new APIListScheduleResponse(count, total_count, next_page, scheduleresponse);
        }
    }
}
