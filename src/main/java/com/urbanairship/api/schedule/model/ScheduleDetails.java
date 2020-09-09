/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class ScheduleDetails extends ScheduleModelObject {

    private final String jobId;
    private final SchedulePayloadResponse schedulePayloadResponse;

    public static Builder newBuilder() {
        return new Builder();
    }

    private ScheduleDetails(String jobId, SchedulePayloadResponse schedulePayloadResponse) {
        this.schedulePayloadResponse = schedulePayloadResponse;
        this.jobId = jobId;
    }

    public SchedulePayloadResponse getSchedulePayloadResponse() {
        return schedulePayloadResponse;
    }

    public String getJobId() {
        return jobId;
    }

    @Override
    public String toString() {
        return "ScheduleDetails{" +
                "jobId='" + jobId + '\'' +
                ", schedulePayloadResponse=" + schedulePayloadResponse +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobId, schedulePayloadResponse);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ScheduleDetails other = (ScheduleDetails) obj;
        return Objects.equal(this.jobId, other.jobId)
                && Objects.equal(this.schedulePayloadResponse, other.schedulePayloadResponse);
    }

    public static final class Builder {

        private String jobId = null;
        private SchedulePayloadResponse schedulePayloadResponse = null;

        private Builder() { }

        public Builder setSchedulePayloadResponse(SchedulePayloadResponse schedulePayloadResponse) {
            this.schedulePayloadResponse = schedulePayloadResponse;
            return this;
        }

        public Builder setJobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public ScheduleDetails build() {
            Preconditions.checkNotNull(schedulePayloadResponse, "schedule payload must be provided");
            Preconditions.checkNotNull(jobId, "job id must be provided");

            return new ScheduleDetails(jobId, schedulePayloadResponse);
        }
    }
}
