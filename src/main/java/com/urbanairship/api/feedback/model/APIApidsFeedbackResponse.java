/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.model;

import com.google.common.base.Objects;

public class APIApidsFeedbackResponse
{
    private String apid;
    private String gcmRegistrationId;
    private String markedInactiveOn;
    private String alias;

    public static Builder newBuilder() {
        return new Builder();
    }

    public APIApidsFeedbackResponse(String apid, String gcmRegistrationId, String markedInactiveOn, String alias) {
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

    public void setMarkedInactiveOn(String markedInactiveOn)
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

    public String getMarkedInactiveOn()
    {
        return markedInactiveOn;
    }

    public String getAlias()
    {
        return alias;
    }

    @Override
    public String toString() {
        return "APIApidsFeedbackResponse{" +
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
        final APIApidsFeedbackResponse other = (APIApidsFeedbackResponse) obj;
        return Objects.equal(this.apid, other.apid) &&
                Objects.equal(this.gcmRegistrationId, other.gcmRegistrationId) &&
                Objects.equal(this.markedInactiveOn, other.markedInactiveOn) &&
                Objects.equal(this.alias, other.alias);
    }

    public static class Builder {

        private String apid;
        private String gcmRegistrationId;
        private String markedInactiveOn;
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

        public Builder setMarkedInactiveOn(String markedInactiveOn)
        {
            this.markedInactiveOn = markedInactiveOn;
            return this;
        }

        public Builder setAlias(String alias)
        {
            this.alias = alias;
            return this;
        }

        public APIApidsFeedbackResponse build() {
            return new APIApidsFeedbackResponse(apid, gcmRegistrationId, markedInactiveOn, alias);
        }
    }

}
