/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class Schedule extends PushModelObject {

    private final Optional<DateTime> scheduledTimestamp;
    private final Optional<DateTime> localScheduledTimestamp;
    private final boolean localTimePresent;

    // TODO local, global, etc

    private Schedule(Optional<DateTime> scheduledTimestamp,
                     Optional<DateTime> localScheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
        this.localScheduledTimestamp = localScheduledTimestamp;
        localTimePresent = localScheduledTimestamp.isPresent() ? true : false;
    }

    /**
     * Get the DateTime for this schedule
     * @return DateTime
     */
    public Optional<DateTime> getScheduledTimestamp () {
        return scheduledTimestamp;
    }

    /**
     * Get the DateTime for the local schedule
     * @return DateTime
     */
    public Optional<DateTime> getLocalScheduledTimestamp () {
        return localScheduledTimestamp;
    }

    /**
     * Get the boolean indicating if the scheduled time is local
     * @return boolean
     */
    public boolean getLocalTimePresent() {
        return localTimePresent;
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

        if (scheduledTimestamp != null ? !scheduledTimestamp.equals(that.scheduledTimestamp) :that.scheduledTimestamp != null) {
            return false;
        }
        if (localScheduledTimestamp != null ? ! localScheduledTimestamp.equals(that.localScheduledTimestamp) :that.localScheduledTimestamp != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduledTimestamp != null ? scheduledTimestamp.hashCode() : 0;
        result = 31 * result + (localScheduledTimestamp != null ? localScheduledTimestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduledTimestamp=" + scheduledTimestamp +
                "localScheduledTimestamp=" + localScheduledTimestamp +
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
        private DateTime localScheduledTimestamp = null;

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
         * Set the DateTime for local scheduled delivery. This will be converted to
         * UTC by the server.
         * @param localScheduledTimestamp Delivery time.
         * @return Builder
         */
        public Builder setLocalScheduledTimestamp(DateTime localScheduledTimestamp) {
            this.localScheduledTimestamp = localScheduledTimestamp;
            return this;
        }

        /**
         * Build the Schedule object.
         * @return Schedule
         */
        public Schedule build() {
           Preconditions.checkArgument((scheduledTimestamp == null || localScheduledTimestamp == null) && scheduledTimestamp != localScheduledTimestamp,"" +
                "Either scheduled_time or local_scheduled_time must be set.");

// Preconditions.checkArgument(scheduledTimestamp == null ^ localScheduledTimestamp == null,"" +
          //  "Either scheduled_time or local_scheduled_time must be set.");

            return new Schedule(Optional.fromNullable(scheduledTimestamp),
                                Optional.fromNullable(localScheduledTimestamp));
        }
    }
}
