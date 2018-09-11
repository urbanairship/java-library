package com.urbanairship.api.schedule.model;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Represents that a push will be sent on the scheduled day, at the best time determined by optimization.
 */
public final class BestTime {

    private DateTime sendDate;

    private BestTime() {}

    private BestTime(DateTime sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BestTime)) return false;
        BestTime bestTime = (BestTime) o;
        return Objects.equals(getSendDate(), bestTime.getSendDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSendDate());
    }

    @Override
    public String toString() {
        return "BestTime{" +
                ", sendDate=" + sendDate +
                '}';
    }

    /**
     * BestTime builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the scheduled day that push will be sent.
     * @return DateTime
     * */
    public DateTime getSendDate() {
        return sendDate;
    }

    /**
     * BestTime Builder
     */
    public static class Builder {

        private DateTime sendDate = null;

        private Builder() { }

        /**
         * Set the day to send the push
         * @param sendDate DateTime
         * @return BestTime Builder
         */
        public Builder setSendDate(DateTime sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        /**
         * Build the Schedule object.
         * @return Schedule
         */
        public BestTime build() {
            Preconditions.checkArgument((sendDate != null),"sendDate cannot be null");
            return new BestTime(sendDate);
        }
    }
}
