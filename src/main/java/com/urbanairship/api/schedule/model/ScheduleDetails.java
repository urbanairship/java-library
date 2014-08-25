/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public final class ScheduleDetails extends PushModelObject {

    private final String jobId;
    private final SchedulePayload schedulePayload;

    public static Builder newBuilder() {
        return new Builder();
    }

    private ScheduleDetails(String jobId, SchedulePayload schedulePayload) {
        this.schedulePayload = schedulePayload;
        this.jobId = jobId;
    }

    public SchedulePayload getSchedulePayload() {
        return schedulePayload;
    }

    public String getJobId() {
        return jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleDetails scheduleDetails = (ScheduleDetails) o;

        if (schedulePayload != null ? !schedulePayload.equals(scheduleDetails.schedulePayload) : scheduleDetails.schedulePayload != null) {
            return false;
        }
        if (jobId != null ? !jobId.equals(scheduleDetails.jobId) : scheduleDetails.jobId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = schedulePayload != null ? schedulePayload.hashCode() : 0;
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScheduleDetails{" +
                "jobId='" + jobId + '\'' +
                ", schedulePayload='" + schedulePayload + '\'' +
                '}';
    }

    public static final class Builder {

        private String jobId = null;
        private SchedulePayload schedulePayload = null;

        private Builder() { }

        public Builder setSchedulePayload(SchedulePayload schedulePayload) {
            this.schedulePayload = schedulePayload;
            return this;
        }

        public Builder setJobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public ScheduleDetails build() {
            Preconditions.checkNotNull(schedulePayload, "schedule payload must be provided");
            Preconditions.checkNotNull(jobId, "job id must be provided");

            return new ScheduleDetails(jobId, schedulePayload);
        }
    }
}
