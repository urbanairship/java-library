/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class RichPerPushCounts {

    private long responses;
    private long sends;

    private RichPerPushCounts(long responses, long sends) {
        this.responses = responses;
        this.sends = sends;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getResponses() {
        return responses;
    }

    public long getSends() {
        return sends;
    }

    @Override
    public String toString() {
        return "RichPerPushCounts{" +
                "responses=" + responses +
                ", sends=" + sends +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(responses, sends);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final RichPerPushCounts other = (RichPerPushCounts) obj;
        return Objects.equal(this.responses, other.responses) && Objects.equal(this.sends, other.sends);
    }

    public static class Builder {
        private long responses;
        private long sends;

        private Builder() {
        }

        public Builder setResponses(long value) {
            this.responses = value;
            return this;
        }

        public Builder setSends(long value) {
            this.sends = value;
            return this;
        }

        public RichPerPushCounts build() {
            Preconditions.checkNotNull(responses, "Counts cannot be null");
            Preconditions.checkNotNull(sends, "Counts cannot be null");

            return new RichPerPushCounts(responses, sends);
        }


    }

}
