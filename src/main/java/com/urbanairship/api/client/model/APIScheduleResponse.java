/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.schedule.model.SchedulePayload;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public final class APIScheduleResponse {

    private final boolean ok;
    private final String operationId;
    private final ImmutableList<String> scheduleUrls;
    private final ImmutableList<String> scheduleIds;
    private final ImmutableList<SchedulePayload> schedulePayloads;

    private APIScheduleResponse(boolean ok, String operationId, ImmutableList<String> scheduleUrls, ImmutableList<String> scheduleIds, ImmutableList<SchedulePayload> schedulePayloads) {
        this.ok = ok;
        this.operationId = operationId;
        this.scheduleUrls = scheduleUrls;
        this.scheduleIds = scheduleIds;
        this.schedulePayloads = schedulePayloads;
    }

    /**
     * New APIScheduleResponse builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the response status as a boolean.
     *
     * @return Response status.
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     *
     * @return Operation id for this API request
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * List of schedule urls, one for every scheduled push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return List of schedule urls.
     */
    public ImmutableList<String> getScheduleUrls() {
        return scheduleUrls;
    }

    /**
     * List of schedule ids, one for every scheduled push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return List of schedule ids.
     */
    public ImmutableList<String> getScheduleIds() {
        return scheduleIds;
    }

    public ImmutableList<SchedulePayload> getSchedulePayloads() {
        return schedulePayloads;
    }

    @Override
    public String toString() {
        return "APIScheduleResponse{" +
                "ok=" + ok +
                ", operationId='" + operationId + '\'' +
                ", scheduleUrls=" + scheduleUrls +
                ", scheduleIds=" + scheduleIds +
                ", schedulePayloads=" + schedulePayloads +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, operationId, scheduleUrls, scheduleIds, schedulePayloads);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIScheduleResponse other = (APIScheduleResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.operationId, other.operationId) && Objects.equal(this.scheduleUrls, other.scheduleUrls) && Objects.equal(this.scheduleIds, other.scheduleIds) && Objects.equal(this.schedulePayloads, other.schedulePayloads);
    }

    /**
     * APIScheduleResponse Builder
     */
    public static class Builder {

        private boolean ok;
        private String operationId;
        private ImmutableList.Builder<String> scheduleUrls = ImmutableList.builder();
        private ImmutableList.Builder<String> scheduleIds = ImmutableList.builder();
        private ImmutableList.Builder<SchedulePayload> schedulePayloads = ImmutableList.builder();

        private Builder() {
        }

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder addScheduleUrl(String scheduleurl) {
            this.scheduleUrls.add(scheduleurl);
            return this;
        }

        public Builder addAllScheduleUrls(Iterable<? extends String> scheduleUrls) {
            this.scheduleUrls.addAll(scheduleUrls);
            return this;
        }

        public Builder addScheduleId(String scheduleId) {
            this.scheduleIds.add(scheduleId);
            return this;
        }

        public Builder addAllScheduleIds(Iterable<? extends String> scheduleIds) {
            this.scheduleIds.addAll(scheduleIds);
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

        public APIScheduleResponse build() {
            Preconditions.checkNotNull(ok, "The ok attribute must be set in order to build APIScheduleResponse");
            Preconditions.checkNotNull(operationId, "Operation ID must be set in order to build APIScheduleResponse");
            return new APIScheduleResponse(ok, operationId, scheduleUrls.build(), scheduleIds.build(), schedulePayloads.build());
        }
    }
}