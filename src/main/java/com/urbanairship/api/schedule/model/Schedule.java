package com.urbanairship.api.schedule.model;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class Schedule extends PushModelObject {

    private final DateTime scheduledTimestamp;

    // TODO local, global, etc

    private Schedule(DateTime scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }

    /**
     * Get the DateTime for this schedule
     * @return DateTime
     */
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

        return (scheduledTimestamp != null ?
                !scheduledTimestamp.isEqual(that.scheduledTimestamp) :
                that.scheduledTimestamp != null);

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


    /**
     * Get new Schedule Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Schedule Builder
     */
    public static class Builder {
        private DateTime scheduledTimestamp = null;

        private Builder() { }

        /**
         * Set the DateTime for scheduled delivery. This will be converted to
         * UTC by the server.
         * @param scheduledTimestamp Delivery time.
         * @return Builder
         */
        public Builder setScheduledTimestamp(DateTime scheduledTimestamp) {
            this.scheduledTimestamp = scheduledTimestamp;
            return this;
        }

        /**
         * Build the Schedule object.
         * @return Schedule
         */
        public Schedule build() {
            Preconditions.checkNotNull(scheduledTimestamp, "'schedule_time' must be set");

            return new Schedule(scheduledTimestamp);
        }
    }
}
