package com.urbanairship.api.schedule.model;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class Schedule extends PushModelObject {

    private DateTime scheduledTimestamp;

    // TODO local, global, etc

    private Schedule(DateTime scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }

    public DateTime getScheduledTimestamp () {
        return scheduledTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Schedule that = (Schedule) o;

        if (scheduledTimestamp != null ? !scheduledTimestamp.isEqual(that.scheduledTimestamp) : that.scheduledTimestamp != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduledTimestamp != null ? scheduledTimestamp.hashCode() : 0;
        result = 31 * result + (scheduledTimestamp != null ? scheduledTimestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduledTimestamp=" + scheduledTimestamp +
                '}';
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private DateTime scheduledTimestamp = null;

        private Builder() { }

        public Builder setScheduledTimestamp(DateTime scheduledTimestamp) {
            this.scheduledTimestamp = scheduledTimestamp;
            return this;
        }

        public Schedule build() {
            Preconditions.checkNotNull(scheduledTimestamp, "'schedule_time' must be set");

            return new Schedule(scheduledTimestamp);
        }
    }
}
