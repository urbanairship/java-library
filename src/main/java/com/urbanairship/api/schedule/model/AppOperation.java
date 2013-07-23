package com.urbanairship.api.schedule.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

public final class AppOperation {

    private final String appKey;
    private final String secret;
    private final String operationId;

    public static Builder newBuilder() {
        return new Builder();
    }

    private AppOperation(String appKey, String secret, String jobId) {
        this.appKey = appKey;
        this.secret = secret;
        this.operationId = jobId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSecret() {
        return secret;
    }

    public String getOperationId() {
        return operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppOperation appOperation = (AppOperation) o;

        if (appKey != null ? !appKey.equals(appOperation.appKey) : appOperation.appKey != null) {
            return false;
        }
        if (secret != null ? !secret.equals(appOperation.secret) : appOperation.secret != null) {
            return false;
        }
        if (operationId != null ? !operationId.equals(appOperation.operationId) : appOperation.operationId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = appKey != null ? appKey.hashCode() : 0;
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppJob{" +
                "appKey='" + appKey + '\'' +
                "secret='" + secret + '\'' +
                ", operationId='" + operationId + '\'' +
                '}';
    }

    public static final class Builder {

        private String appKey = null;
        private String secret = null;
        private String operationId = null;

        private Builder() { }

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public AppOperation build() {
            Preconditions.checkArgument(StringUtils.isNotBlank(appKey), "App key must be provided");
            Preconditions.checkArgument(StringUtils.isNotBlank(secret), "App secret must be provided");
            Preconditions.checkArgument(StringUtils.isNotBlank(operationId), "OperationId id must be provided");

            return new AppOperation(appKey, secret, operationId);
        }
    }
}
