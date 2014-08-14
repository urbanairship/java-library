package com.urbanairship.api.client;


import com.urbanairship.api.schedule.model.SchedulePayload;

import java.util.List;

public final class APIListScheduleResponse {

    private final int count;
    private final int total_count;
    private final String next_page;
    private final List<SchedulePayload> scheduleobjects;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListScheduleResponse(int count, int total_count, String next_page, List<SchedulePayload> response){
        this.count = count;
        this.total_count = total_count;
        this.next_page = next_page;
        this.scheduleobjects = response;
    }

    public int getCount() { return count; }
    public int getTotal_Count() { return total_count; }
    public String getNext_Page() { return next_page; }
    public List<SchedulePayload> getSchedules() { return scheduleobjects; }

    @Override
    public String toString() {
        return "APIListScheduleResponse{" +
                "count=" + count +
                ", total_count=" + total_count +
                ", next_page=" + next_page +
                ", scheduleobjects=" + scheduleobjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass())  { return false; }

        APIListScheduleResponse that = (APIListScheduleResponse) o;

        if (count != that.count) { return false; }
        if (total_count != that.total_count) { return false; }
        if (next_page != null ? !next_page.equals(that.next_page) : that.next_page != null)  { return false; }
        if (scheduleobjects != null ? !scheduleobjects.equals(that.scheduleobjects) : that.scheduleobjects != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + total_count;
        result = 31 * result + (next_page != null ? next_page.hashCode() : 0);
        result = 31 * result + (scheduleobjects != null ? scheduleobjects.hashCode() : 0);
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
