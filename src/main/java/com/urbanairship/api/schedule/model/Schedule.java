/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class Schedule extends PushModelObject {

    private final DateTime scheduledTimestamp;
    private final Boolean localTimePresent;

    // TODO local, global, etc

    private Schedule(DateTime scheduledTimestamp, boolean localTimePresent) {
        this.scheduledTimestamp = scheduledTimestamp;
        this.localTimePresent = localTimePresent;
    }

    /**
     * Get the DateTime for this schedule
     * @return DateTime
     */
    public DateTime getScheduledTimestamp () {
        return scheduledTimestamp;
    }

    /**
     * Get the boolean indicating if the scheduled time is local
     * @return boolean
     */
    public Boolean getLocalTimePresent() {
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

        if (scheduledTimestamp != null ? !scheduledTimestamp.equals(that.scheduledTimestamp) : that.scheduledTimestamp != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduledTimestamp != null ? scheduledTimestamp.hashCode() : 0;
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
        private Boolean localTimePresent = false;

        private Builder() { }

        /**
         * Set the DateTime for scheduled delivery. This will be converted to
         * UTC by the server.
         * @param scheduledTimestamp DateTime.
         * @return Builder
         */
        public Builder setScheduledTimestamp(DateTime scheduledTimestamp) {
            this.scheduledTimestamp = scheduledTimestamp;
            this.localTimePresent = false;
            return this;
        }

        /**
         * Set the DateTime for local scheduled delivery. This will be converted to
         * UTC by the server.
         * @param scheduledTimestamp DateTime.

         * @return Builder
         */
        public Builder setLocalScheduledTimestamp(DateTime scheduledTimestamp) {
            this.scheduledTimestamp = scheduledTimestamp;
            this.localTimePresent = true;
            return this;
        }

        /**
         * Build the Schedule object.
         * @return Schedule
         */
        public Schedule build() {
           Preconditions.checkArgument((scheduledTimestamp != null),"" +
                "Either scheduled_time or local_scheduled_time must be set.");

            return new Schedule(scheduledTimestamp, localTimePresent);
        }
    }
}
