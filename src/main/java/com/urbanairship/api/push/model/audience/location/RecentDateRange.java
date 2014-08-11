package com.urbanairship.api.push.model.audience.location;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class RecentDateRange extends PushModelObject implements DateRange {

    private final DateRangeUnit resolution;
    private final int units;
    private final PresenceTimeframe timeframe;

    private RecentDateRange(DateRangeUnit resolution, int units, PresenceTimeframe timeframe) {
        this.resolution = resolution;
        this.units = units;
        this.timeframe = timeframe;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getUnits() {
        return units;
    }

    @Override
    public DateTime getStart() {
        DateTime now = DateTime.now();
        return now;
        // TODO
        // return now.minus(resolution.getPeriod().toPeriod().multipliedBy(units));
    }

    @Override
    public DateTime getEnd() {
        return DateTime.now();
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

        RecentDateRange that = (RecentDateRange) o;

        if (units != that.units) {
            return false;
        }
        if (resolution != that.resolution) {
            return false;
        }
        if (timeframe != that.timeframe) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = resolution != null ? resolution.hashCode() : 0;
        result = 31 * result + units;
        result = 31 * result + (timeframe != null ? timeframe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RecentDateRange{" +
            "resolution=" + resolution +
            ", units=" + units +
            ", timeframe='" + timeframe + '\'' +
            '}';
    }

    public static class Builder {
        private DateRangeUnit resolution;
        private int units = 0;
        private PresenceTimeframe timeframe = PresenceTimeframe.ANYTIME;

        private Builder() { }

        public Builder setResolution(DateRangeUnit value) {
            resolution = value;
            return this;
        }

        public Builder setUnits(int value) {
            units = value;
            return this;
        }

        public Builder setTimeframe(PresenceTimeframe value) {
            timeframe = value;
            return this;
        }

        public RecentDateRange build() {
            Preconditions.checkNotNull(resolution);
            Preconditions.checkArgument(units != 0);
            return new RecentDateRange(resolution, units, timeframe);
        }
    }
}
