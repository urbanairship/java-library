package com.urbanairship.api.schedule.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

public final class AppJob {

    private final String appKey;
    private final String jobId;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AppJob(String appKey, String jobId) {
        this.appKey = appKey;
        this.jobId = jobId;
    }

    public String getAppKey() {
        return appKey;
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

        AppJob appJob = (AppJob) o;

        if (appKey != null ? !appKey.equals(appJob.appKey) : appJob.appKey != null) {
            return false;
        }
        if (jobId != null ? !jobId.equals(appJob.jobId) : appJob.jobId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = appKey != null ? appKey.hashCode() : 0;
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppJob{" +
                "appKey='" + appKey + '\'' +
                ", jobId='" + jobId + '\'' +
                '}';
    }

    public static final class Builder {

        private String appKey = null;
        private String jobId = null;

        private Builder() { }

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setJobId(String jobId) {
            this.jobId = jobId;
            return this;
        }

        public AppJob build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(appKey), "App key must be provided");
            Preconditions.checkArgument(StringUtils.isNotBlank(jobId), "Job id must be provided");

            return new AppJob(appKey, jobId);
        }
    }
}