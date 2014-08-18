/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class AbsoluteDateRange extends PushModelObject implements DateRange {

    private final DateTime start;
    private final DateTime end;
    private final DateRangeUnit resolution;
    private final PresenceTimeframe timeframe;

    private AbsoluteDateRange(DateTime start, DateTime end, DateRangeUnit resolution, PresenceTimeframe timeframe) {
        this.start = start;
        this.end = end;
        this.resolution = resolution;
        this.timeframe = timeframe;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DateTime getStart() {
        return start;
    }

    @Override
    public DateTime getEnd() {
        return end;
    }

    @Override
    public DateRangeUnit getResolution() {
        return resolution;
    }

    @Override
    public PresenceTimeframe getTimeframe() {
        return timeframe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbsoluteDateRange that = (AbsoluteDateRange) o;

        if (start != null ? !start.equals(that.start) : that.start != null) {
            return false;
        }
        if (end != null ? !end.equals(that.end) : that.end != null) {
            return false;
        }
        if (resolution != null ? !resolution.equals(that.resolution) : that.resolution != null) {
            return false;
        }
        if (timeframe != null ? !timeframe.equals(that.timeframe) : that.timeframe != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (resolution != null ? resolution.hashCode() : 0);
        result = 31 * result + (timeframe != null ? timeframe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AbsoluteDateRange{" +
            "start='" + start + '\'' +
            ", end='" + end + '\'' +
            ", resolution='" + resolution + '\'' +
            ", timeframe='" + timeframe + '\'' +
            '}';
    }

    public static class Builder {
        private DateTime start;
        private DateTime end;
        private DateRangeUnit resolution;
        private PresenceTimeframe timeframe;

        private Builder() { }

        public Builder setStart(DateTime value) {
            start = value;
            return this;
        }

        public Builder setEnd(DateTime value) {
            end = value;
            return this;
        }

        public Builder setResolution(DateRangeUnit value) {
            resolution = value;
            return this;
        }

        public Builder setTimeframe(PresenceTimeframe value) {
            timeframe = value;
            return this;
        }

        public AbsoluteDateRange build() {
            Preconditions.checkNotNull(start, "Missing value for 'start'");
            Preconditions.checkNotNull(end, "Missing value for 'end'");
            Preconditions.checkNotNull(end, "Missing date range resolution");
            return new AbsoluteDateRange(start, end, resolution, timeframe);
        }
    }
}
