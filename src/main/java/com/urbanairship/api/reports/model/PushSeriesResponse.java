/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PushSeriesResponse {

    private final String appKey;
    private final UUID pushID;
    private final DateTime start;
    private final DateTime end;
    private final Precision precision;
    private final List<PlatformCounts> counts;

    private PushSeriesResponse(String appKey,
                               UUID pushID,
                               DateTime start,
                               DateTime end,
                               Precision precision,
                               List<PlatformCounts> counts) {
        this.appKey = appKey;
        this.pushID = pushID;
        this.start = start;
        this.end = end;
        this.precision = precision;
        this.counts = counts;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAppKey() {
        return appKey;
    }

    public UUID getPushID() {
        return pushID;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public Precision getPrecision() {
        return precision;
    }

    public List<PlatformCounts> getCounts() {
        return counts;
    }

    @Override
    public String toString() {
        return "PushSeriesResponse{" +
                "appKey='" + appKey + '\'' +
                ", pushID=" + pushID +
                ", start=" + start +
                ", end=" + end +
                ", precision='" + precision + '\'' +
                ", counts=" + counts +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(appKey, pushID, start, end, precision, counts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PushSeriesResponse other = (PushSeriesResponse) obj;
        return Objects.equal(this.appKey, other.appKey) && Objects.equal(this.pushID, other.pushID) && Objects.equal(this.start, other.start) && Objects.equal(this.end, other.end) && Objects.equal(this.precision, other.precision) && Objects.equal(this.counts, other.counts);
    }

    public static class Builder {
        private String appKey;
        private UUID pushID;
        private DateTime start;
        private DateTime end;
        private Precision precision;
        private List<PlatformCounts> counts = new ArrayList<PlatformCounts>();

        private Builder() {
        }

        public Builder setAppKey(String value) {
            this.appKey = value;
            return this;
        }

        public Builder setPushID(UUID value) {
            this.pushID = value;
            return this;
        }

        public Builder setStart(DateTime value) {
            this.start = value;
            return this;
        }

        public Builder setEnd(DateTime value) {
            this.end = value;
            return this;
        }

        public Builder setPrecision(Precision value) {
            this.precision = value;
            return this;
        }

        public Builder addPlatformCount(PlatformCounts value) {
            this.counts.add(value);
            return this;
        }

        public Builder addAllPlatformCounts(List<PlatformCounts> value) {
            this.counts.addAll(value);
            return this;
        }

        public PushSeriesResponse build() {
            return new PushSeriesResponse(appKey, pushID, start, end, precision, counts);
        }

    }

}
