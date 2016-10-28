/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.model;

import com.google.common.base.Objects;

import org.joda.time.DateTime;

public class DeviceTokensFeedbackResponse
{
    private String deviceToken;
    private DateTime markedInactiveOn;
    private String alias;

    public static Builder newBuilder() {
        return new Builder();
    }

    public DeviceTokensFeedbackResponse(String device_token, DateTime marked_inactive_on, String alias) {
        this.deviceToken = device_token;
        this.markedInactiveOn = marked_inactive_on;
        this.alias = alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public void setMarkedInactiveOn(DateTime markedInactiveOn)
    {
        this.markedInactiveOn = markedInactiveOn;
    }

    public String getAlias()
    {
        return alias;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public DateTime getMarkedInactiveOn()
    {
        return markedInactiveOn;
    }

    @Override
    public String toString() {
        return "APIApidsFeedbackResponse{" +
                "deviceToken=" + deviceToken +
                ", markedInactiveOn=" + markedInactiveOn +
                ", alias=" + alias +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(deviceToken, markedInactiveOn, alias);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DeviceTokensFeedbackResponse other = (DeviceTokensFeedbackResponse) obj;
        return Objects.equal(this.deviceToken, other.deviceToken) &&
                Objects.equal(this.markedInactiveOn, other.markedInactiveOn) &&
                Objects.equal(this.alias, other.alias);
    }

    public static class Builder {

        private String deviceToken;
        private DateTime markedInactiveOn;
        private String alias;

        private Builder() {
        }

        public Builder setMarkedInactiveOn(DateTime markedInactiveOn)
        {
            this.markedInactiveOn = markedInactiveOn;
            return this;
        }

        public Builder setAlias(String alias)
        {
            this.alias = alias;
            return this;
        }

        public Builder setDeviceToken(String deviceToken)
        {
            this.deviceToken = deviceToken;
            return this;
        }

        public DeviceTokensFeedbackResponse build() {
            return new DeviceTokensFeedbackResponse(deviceToken, markedInactiveOn, alias);
        }
    }
}
