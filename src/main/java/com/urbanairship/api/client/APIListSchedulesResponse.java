/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import java.util.List;
import java.util.Map;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public class APIListSchedulesResponse {

    private final Boolean ok;
    private final Integer count;
    private final Integer totalCount;
    private final String nextPage;
    private final List<Map> schedules;

    /**
     * New APIScheduleListResponse builder
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private APIListSchedulesResponse(boolean ok, Integer count, Integer totalCount, String nextPage,
                                     List<Map> schedules){
        this.ok = ok;
        this.count = count;
        this.totalCount = totalCount;
        this.nextPage = nextPage;
        this.schedules = schedules;
    }

    /**
     * Ok status for this request
     *
     * @return OK status for this request
     */
    public Boolean isOk() {
        return ok;
    }

    /**
     * Get the count.
     *
     * @return Count the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Get the total count
     *
     * @return TotalCount the total count
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Get the next page url. This is used in pagination.
     *
     * @return Next page
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     * Get the list of schedule objects.
     *
     * @return Schedule objects
     */
    public List<Map> getSchedules() {
        return schedules;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("APIScheduleListResponse:");
        stringBuilder.append(String.format("\nOk:%s", ok));
        stringBuilder.append(String.format("\nCount:%s", count));
        stringBuilder.append(String.format("\nTotal Count:%s", totalCount));
        stringBuilder.append(String.format("\nNext Page:%s", nextPage));
        stringBuilder.append(String.format("\nSchedules:%s", schedules));
        return stringBuilder.toString();
    }

    /**
     * APIScheduleListResponse Builder
     */
    public static class Builder {

        private Boolean ok;
        private Integer count;
        private Integer totalCount;
        private String nextPage;
        private List<Map> schedules;

        private Builder() {}

        public Builder setOk(boolean ok){
            this.ok = ok;
            return this;
        }

        public Builder setCount(Integer count) {
            this.count = count;
            return this;
        }

        public Builder setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder setNextPage(String nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        public Builder setSchedules(List<Map> schedules) {
            this.schedules = schedules;
            return this;
        }

        public APIListSchedulesResponse build(){
            return new APIListSchedulesResponse(this.ok, this.count, this.totalCount, this.nextPage, this.schedules);
        }
    }
}
