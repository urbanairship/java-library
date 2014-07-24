package com.urbanairship.api.client;


import com.urbanairship.api.schedule.model.ScheduleResponseObject;

import java.util.List;

public class APIListScheduleResponse {

    private final Number count;
    private final Number total_count;
    private final List<ScheduleResponseObject> scheduleresponse;

    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListScheduleResponse(Number count, Number total_count, List<ScheduleResponseObject> response){
        this.count = count;
        this.total_count = total_count;
        this.scheduleresponse = response;
    }

    public Number getCount() { return count; }
    public Number getTotal_Count() { return total_count; }
    public List<ScheduleResponseObject> getSchedules() { return scheduleresponse; }

    @Override
    public String toString() {
        return "APIListScheduleResponse{" +
                "count=" + count +
                ", total_count=" + total_count +
                ", scheduleresponse=" + scheduleresponse +
                '}';
    }

    /**
     * APIListScheduleResponse Builder
     */
    public static class Builder {

        private Number count;
        private Number total_count;
        private List<ScheduleResponseObject> scheduleresponse;

        private Builder() { }

        public Builder setCount(Number count){
            this.count = count;
            return this;
        }

        public Builder setTotalCount(Number total_count){
            this.total_count = total_count;
            return this;
        }

        public Builder setSchedule(List<ScheduleResponseObject> schedules){
            this.scheduleresponse = schedules;
            return this;
        }

        public APIListScheduleResponse build(){
            return new APIListScheduleResponse(count, total_count, scheduleresponse);
        }
    }
}
