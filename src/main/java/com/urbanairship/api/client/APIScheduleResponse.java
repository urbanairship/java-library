/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import com.google.common.collect.ImmutableList;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public final class APIScheduleResponse {

    private final String operationId;
    private final ImmutableList<String> scheduleUrls;

    /**
     * New APIScheduleResponse builder
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private APIScheduleResponse(String operationId, ImmutableList<String> scheduleUrls){
        this.operationId = operationId;
        this.scheduleUrls = scheduleUrls;
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     * @return Operation id for this API request
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * List of push id's, one for every actual push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     * @return List of push ids.
     */
    public ImmutableList<String> getScheduleUrls() {
        return scheduleUrls;
    }

    @Override
    public String toString() {
        return "APIScheduleResponse{" +
                "operationId='" + operationId + '\'' +
                ", scheduleUrls=" + scheduleUrls +
                '}';
    }

    /**
     * APIScheduleResponse Builder
     */
    public static class Builder {

        private String operationId;
        private ImmutableList.Builder<String> scheduleUrls = ImmutableList.builder();

        private Builder() {}

        public Builder setOperationId(String operationId){
            this.operationId = operationId;
            return this;
        }

        public Builder addScheduleUrl(String scheduleurl) {
            this.scheduleUrls.add(scheduleurl);
            return this;
        }

        public Builder addAllScheduleUrls(Iterable<? extends String> scheduleUrls){
            this.scheduleUrls.addAll(scheduleUrls);
            return this;
        }

        public APIScheduleResponse build(){
            return new APIScheduleResponse(operationId, scheduleUrls.build());
        }
    }
}
