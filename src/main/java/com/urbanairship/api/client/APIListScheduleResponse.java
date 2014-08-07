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
