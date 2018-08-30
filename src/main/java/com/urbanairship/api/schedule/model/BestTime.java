package com.urbanairship.api.schedule.model;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public class BestTime {

    private DateTime sendDate;

    private BestTime(DateTime sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {

        return "BestTime{" +
                ", sendDate=" + sendDate +
                '}';

    }

    public static Builder newBuilder() {
        return new Builder();
    }

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
            Preconditions.checkArgument((sendDate != null),"" +
                    "sendDate cannot be null");

            return new BestTime(sendDate);
        }
    }
}
