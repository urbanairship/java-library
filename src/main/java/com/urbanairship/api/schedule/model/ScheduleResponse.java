/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Represents a response from the Urban Airship API for Scheduled Notifications.
 */
public final class ScheduleResponse {

    private final boolean ok;
    private final String operationId;
    private final ImmutableList<String> scheduleUrls;
    private final ImmutableList<String> scheduleIds;
    private final ImmutableList<SchedulePayloadResponse> schedulePayloadResponses;

    private ScheduleResponse(boolean ok, String operationId, ImmutableList<String> scheduleUrls, ImmutableList<String> scheduleIds, ImmutableList<SchedulePayloadResponse> schedulePayloadResponses) {
        this.ok = ok;
        this.operationId = operationId;
        this.scheduleUrls = scheduleUrls;
        this.scheduleIds = scheduleIds;
        this.schedulePayloadResponses = schedulePayloadResponses;
    }

    /**
     * New ScheduleResponse builder
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

    public ImmutableList<SchedulePayloadResponse> getSchedulePayloadResponses() {
        return schedulePayloadResponses;
    }

    @Override
    public String toString() {
        return "ScheduleResponse{" +
                "ok=" + ok +
                ", operationId='" + operationId + '\'' +
                ", scheduleUrls=" + scheduleUrls +
                ", scheduleIds=" + scheduleIds +
                ", schedulePayloadResponses=" + schedulePayloadResponses +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, operationId, scheduleUrls, scheduleIds, schedulePayloadResponses);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ScheduleResponse other = (ScheduleResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.operationId, other.operationId) && Objects.equal(this.scheduleUrls, other.scheduleUrls) && Objects.equal(this.scheduleIds, other.scheduleIds) && Objects.equal(this.schedulePayloadResponses, other.schedulePayloadResponses);
    }

    /**
     * ScheduleResponse Builder
     */
    public static class Builder {

        private boolean ok;
        private String operationId;
        private ImmutableList.Builder<String> scheduleUrls = ImmutableList.builder();
        private ImmutableList.Builder<String> scheduleIds = ImmutableList.builder();
        private ImmutableList.Builder<SchedulePayloadResponse> schedulePayloads = ImmutableList.builder();

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

        public Builder addSchedulePayload(SchedulePayloadResponse schedulePayloadResponse) {
            this.schedulePayloads.add(schedulePayloadResponse);
            return this;
        }

        public Builder addAllSchedulePayload(Iterable<? extends SchedulePayloadResponse> schedulePayloads) {
            this.schedulePayloads.addAll(schedulePayloads);
            return this;
        }

        public ScheduleResponse build() {
            Preconditions.checkNotNull(ok, "The ok attribute must be set in order to build ScheduleResponse");
            Preconditions.checkNotNull(operationId, "Operation ID must be set in order to build ScheduleResponse");
            return new ScheduleResponse(ok, operationId, scheduleUrls.build(), scheduleIds.build(), schedulePayloads.build());
        }
    }
}
