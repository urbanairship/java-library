/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

/**
 * Represent a single PlatformStats object
 */
public final class PlatformStats {

    private final int android;
    private final int ios;
    private final DateTime date;

    private PlatformStats(int android, int ios, DateTime date) {
        this.android = android;
        this.ios = ios;
        this.date = date;
    }

    /**
     * New PlatformStats builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the Android quantity.
     *
     * @return int
     */
    public int getAndroid() {
        return android;
    }

    /**
     * Get the iOS quantity.
     *
     * @return int
     */
    public int getIos() {
        return ios;
    }

    /**
     * Get the time interval represented by the PlatformStats object.
     *
     * @return DateTime
     */
    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PlatformStats{" +
                "android=" + android +
                ", ios=" + ios +
                ", date=" + date +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(android, ios, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlatformStats other = (PlatformStats) obj;
        return Objects.equal(this.android, other.android) && Objects.equal(this.ios, other.ios) && Objects.equal(this.date, other.date);
    }

    public static class Builder {

        private int android;
        private int ios;
        private DateTime date;

        private Builder() {
        }

        /**
         * Set the Android quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setAndroid(int value) {
            this.android = value;
            return this;
        }

        /**
         * Set the iOS quantity.
         *
         * @param value int
         * @return Builder
         */
        public Builder setIOS(int value) {
            this.ios = value;
            return this;
        }

        /**
         * Set the date.
         *
         * @param value DateTime
         * @return
         */
        public Builder setDate(DateTime value) {
            this.date = value;
            return this;
        }

        /**
         * Build the PlatformStats object.
         *
         * @return PlatformStats
         */
        public PlatformStats build() {
            return new PlatformStats(android, ios, date);
        }
    }


}
