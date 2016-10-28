/**
 * Copyright (c) 2016 Zillow, Inc
 */
package com.urbanairship.api.feedback.model;

import com.google.common.base.Preconditions;

import com.urbanairship.api.common.model.APIModelObject;
import com.urbanairship.api.feedback.parse.FeedbackObjectMapper;

import org.joda.time.DateTime;

/**
 * @author hongbinl
 */
public class FeedbackPayload extends APIModelObject {
    private final DateTime since;

    public FeedbackPayload(DateTime time) {
        since = time;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public DateTime getSince() {
        return since;
    }

    @Override
    public String toString() {
        return "FeedbackPayload{since=" + since.toString() + "}";
    }

    @Override
    public int hashCode() {
        return since.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        FeedbackPayload that = (FeedbackPayload)o;
        return this.getSince().equals(that.getSince());
    }

    @Override
    public String toJSON() {
        try {
            return FeedbackObjectMapper.getInstance().writeValueAsString(this);
        } catch ( Exception e ) {
            return toJSON(e);
        }
    }

    public static class Builder {
        private DateTime since = null;

        private Builder() { }

        public Builder setSince(DateTime since) {
            this.since = since;
            return this;
        }

        public FeedbackPayload build() {
            Preconditions.checkArgument(since != null);

            // This value must be less than one month from the current date.
            Preconditions.checkArgument(DateTime.now().isBefore(since.plusMonths(1)));

            return new FeedbackPayload(since);
        }
    }
}
