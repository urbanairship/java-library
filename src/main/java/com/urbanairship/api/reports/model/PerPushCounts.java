/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;

public final class PerPushCounts {

    private long directResponses;
    private long influencedResponses;
    private long sends;

    private PerPushCounts(long directResponses, long influencedResponses, long sends) {
        this.directResponses = directResponses;
        this.influencedResponses = influencedResponses;
        this.sends = sends;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getDirectResponses() {
        return directResponses;
    }

    public long getInfluencedResponses() {
        return influencedResponses;
    }

    public long getSends() {
        return sends;
    }

    @Override
    public String toString() {
        return "PerPushCounts{" +
                "directResponses=" + directResponses +
                ", influencedResponses=" + influencedResponses +
                ", sends=" + sends +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directResponses, influencedResponses, sends);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PerPushCounts other = (PerPushCounts) obj;
        return Objects.equal(this.directResponses, other.directResponses) && Objects.equal(this.influencedResponses, other.influencedResponses) && Objects.equal(this.sends, other.sends);
    }

    public static class Builder {
        private long directResponses;
        private long influencedResponses;
        private long sends;

        private Builder() {
        }

        public Builder setDirectResponses(long value) {
            this.directResponses = value;
            return this;
        }

        public Builder setInfluencedResponses(long value) {
            this.influencedResponses = value;
            return this;
        }

        public Builder setSends(long value) {
            this.sends = value;
            return this;
        }

        public PerPushCounts build() {
            return new PerPushCounts(directResponses, influencedResponses, sends);
        }
    }
}
