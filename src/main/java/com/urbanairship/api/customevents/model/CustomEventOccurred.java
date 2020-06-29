package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;


public class CustomEventOccurred {

    private final DateTime occurred;

    private CustomEventOccurred(Builder builder) {
        this.occurred = builder.occurred;
    }

    /**
     * New CustomEventOccurred Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the occurred datetime.
     *
     * @return occurred
     */
    public DateTime getOccurred() {
        return occurred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomEventOccurred that = (CustomEventOccurred) o;

        return occurred == that.occurred &&
                Objects.equal(occurred, that.occurred);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(occurred);
    }


    /**
     * CustomEventUser Builder
     */
    public static class Builder {
        private DateTime occurred = null;

        /**
         * Set the Urban Airship occurred identifier for the time the event was triggered.
         *
         * @param occurredString String
         * @return CustomEventOccurred Builder
         */
        public Builder setOccurred(String occurredString) {
            DateTime occurredDatetime = new DateTime(occurredString);
            this.occurred = occurredDatetime;
            return this;
        }

        public CustomEventOccurred build() {
            Preconditions.checkNotNull(occurred, "Must contain occurred'");

            return new CustomEventOccurred(this);
        }
    }
}

