/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.model;

import com.google.common.base.Objects;

import org.joda.time.DateTime;

public class ApidsFeedbackResponse {
    private String apid;
    private String gcmRegistrationId;
    private DateTime markedInactiveOn;
    private String alias;

    public static Builder newBuilder() {
        return new Builder();
    }

    public ApidsFeedbackResponse(String apid, String gcmRegistrationId, DateTime markedInactiveOn, String alias) {
        this.apid = apid;
        this.gcmRegistrationId = gcmRegistrationId;
        this.markedInactiveOn = markedInactiveOn;
        this.alias = alias;
    }

    public void setApid(String apid)
    {
        this.apid = apid;
    }

    public void setGcmRegistrationId(String gcmRegistrationId)
    {
        this.gcmRegistrationId = gcmRegistrationId;
    }

    public void setMarkedInactiveOn(DateTime markedInactiveOn)
    {
        this.markedInactiveOn = markedInactiveOn;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getApid()
    {
        return apid;
    }

    public String getGcmRegistrationId()
    {
        return gcmRegistrationId;
    }

    public DateTime getMarkedInactiveOn()
    {
        return markedInactiveOn;
    }

    public String getAlias()
    {
        return alias;
    }

    @Override
    public String toString() {
        return "ApidsFeedbackResponse{" +
                "apid=" + apid +
                ", gcmRegistrationId=" + gcmRegistrationId +
                ", markedInactiveOn=" + markedInactiveOn +
                ", alias=" + alias +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(apid, gcmRegistrationId, markedInactiveOn, alias);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ApidsFeedbackResponse other = (ApidsFeedbackResponse) obj;
        return Objects.equal(this.apid, other.apid) &&
                Objects.equal(this.gcmRegistrationId, other.gcmRegistrationId) &&
                Objects.equal(this.markedInactiveOn, other.markedInactiveOn) &&
                Objects.equal(this.alias, other.alias);
    }

    public static class Builder {

        private String apid;
        private String gcmRegistrationId;
        private DateTime markedInactiveOn;
        private String alias;

        private Builder() {
        }

        public void setApid(String apid)
        {
            this.apid = apid;
        }

        public void setGcmRegistrationId(String gcmRegistrationId)
        {
            this.gcmRegistrationId = gcmRegistrationId;
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

        public ApidsFeedbackResponse build() {
            return new ApidsFeedbackResponse(apid, gcmRegistrationId, markedInactiveOn, alias);
        }
    }

}
