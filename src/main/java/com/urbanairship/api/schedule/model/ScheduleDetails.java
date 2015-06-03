/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

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
    public String toString() {
        return "ScheduleDetails{" +
                "jobId='" + jobId + '\'' +
                ", schedulePayload='" + schedulePayload + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobId, schedulePayload);
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
        return Objects.equal(this.jobId, other.jobId) && Objects.equal(this.schedulePayload, other.schedulePayload);
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
