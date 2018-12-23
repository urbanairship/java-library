/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

import java.util.Optional;


/**
 * Represents the schedule details for a push. A Schedule defines when a push will be sent.
 */
public final class Schedule extends ScheduleModelObject {

    private final DateTime scheduledTimestamp;
    private final Boolean localTimePresent;
    private final Optional<BestTime> bestTime;

    // TODO local, global, etc

    private Schedule(Builder builder) {
        this.scheduledTimestamp = builder.scheduledTimestamp;
        this.localTimePresent = builder.localTimePresent;
        this.bestTime = Optional.ofNullable(builder.bestTime);
    }

    /**
     * Get the DateTime for this schedule.
     * @return DateTime
     */
    public DateTime getScheduledTimestamp () {
        return scheduledTimestamp;
    }

    /**
     * Get the boolean indicating if the scheduled time is in local time.
     * @return boolean
     */
    public Boolean getLocalTimePresent() {
        return localTimePresent;
    }

    /**
     * Get the BestTime for this schedule.
     * @return Optional BestTime
     */
    public Optional<BestTime> getBestTime() {
        return bestTime;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduledTimestamp=" + scheduledTimestamp +
                ", localTimePresent=" + localTimePresent +
                ", scheduledBestTime=" + bestTime +
                '}';
    }

    /**
     * Get new Schedule Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scheduledTimestamp, localTimePresent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Schedule other = (Schedule) obj;
        return Objects.equal(this.scheduledTimestamp, other.scheduledTimestamp)
                && Objects.equal(this.localTimePresent, other.localTimePresent);
    }

    /**
     * Schedule Builder
     */
    public static class Builder {
        private DateTime scheduledTimestamp = null;
        private Boolean localTimePresent = false;
        private BestTime bestTime = null;

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
         * Set the best time ( aka Optimal Time) which is one of our predictive features. This sends push on a specified
         * date for the optimal time for the user to receive.
         * @param bestTime BestTime
         * @return Builder
         */
        public Builder setBestTime(BestTime bestTime) {
            this.bestTime = bestTime;
            return this;
        }

        /**
         * Build the Schedule object.
         * @return Schedule
         */
        public Schedule build() {

            Preconditions.checkArgument(argumentValidator(scheduledTimestamp,bestTime),
                    "Either scheduled_time or best_time must be set.");

            return new Schedule(this);
        }

        // ensure that exactly one argument is set
        private boolean argumentValidator(DateTime scheduledTimestamp, BestTime bestTime) {
            int argumentSet = 0;
            if (scheduledTimestamp != null) {
                argumentSet++;
            }

            if (bestTime != null) {
                argumentSet++;
            }
            return argumentSet == 1;
        }

    }

}
