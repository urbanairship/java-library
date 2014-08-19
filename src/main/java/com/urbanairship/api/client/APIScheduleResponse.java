/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.schedule.model.SchedulePayload;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public final class APIScheduleResponse {

    private final String operationId;
    private final ImmutableList<String> scheduleUrls;
    private final ImmutableList<SchedulePayload> schedulePayloads;

    /**
     * New APIScheduleResponse builder
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private APIScheduleResponse(String operationId, ImmutableList<String> scheduleUrls, ImmutableList<SchedulePayload> schedulePayloads){
        this.operationId = operationId;
        this.scheduleUrls = scheduleUrls;
        this.schedulePayloads = schedulePayloads;
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
     * List of schedule urls, one for every scheduled push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     * @return List of schedule urls.
     */
    public ImmutableList<String> getScheduleUrls() {
        return scheduleUrls;
    }

    public ImmutableList<SchedulePayload> getSchedulePayloads() {
        return schedulePayloads;
    }

    @Override
    public String toString() {
        return "APIScheduleResponse{" +
                "operationId='" + operationId + '\'' +
                ", scheduleUrls=" + scheduleUrls +
                ", schedulePayloads=" + schedulePayloads +
                '}';
    }

    /**
     * APIScheduleResponse Builder
     */
    public static class Builder {

        private String operationId;
        private ImmutableList.Builder<String> scheduleUrls = ImmutableList.builder();
        private ImmutableList.Builder<SchedulePayload> schedulePayloads = ImmutableList.builder();

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

        public Builder addSchedulePayload(SchedulePayload schedulePayload) {
            this.schedulePayloads.add(schedulePayload);
            return this;
        }

        public Builder addAllSchedulePayload(Iterable<? extends SchedulePayload> schedulePayloads) {
            this.schedulePayloads.addAll(schedulePayloads);
            return this;
        }

        public APIScheduleResponse build(){
            Preconditions.checkNotNull(operationId, "Operation ID must be set in order to build APIScheduleResponse");
            Preconditions.checkNotNull(scheduleUrls, "ScheduleUrls must be set in order to build APIScheduleResponse");
            Preconditions.checkNotNull(schedulePayloads, "SchedulePayloads must be set in order to build APIScheduleResponse");
            return new APIScheduleResponse(operationId, scheduleUrls.build(), schedulePayloads.build());
        }
    }
}