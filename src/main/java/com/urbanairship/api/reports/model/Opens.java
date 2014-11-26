/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public final class Opens {

    private final long android;
    private final long ios;
    private final DateTime date;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Opens(long android, long ios, DateTime date) {
        this.android = android;
        this.ios = ios;
        this.date = date;
    }

    public long getAndroid() {
        return android;
    }

    public long getIos() {
        return ios;
    }

    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Opens{" +
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
        final Opens other = (Opens) obj;
        return Objects.equal(this.android, other.android) && Objects.equal(this.ios, other.ios) && Objects.equal(this.date, other.date);
    }

    public static class Builder {

        private long android;
        private long ios;
        private DateTime date;

        private Builder() { }

        public Builder setAndroid(long value) {
            this.android = value;
            return this;
        }

        public Builder setIOS(long value) {
            this.ios = value;
            return this;
        }

        public Builder setDate(DateTime value) {
            this.date = value;
            return this;
        }

        public Opens build() {
            Preconditions.checkNotNull(android);
            Preconditions.checkNotNull(ios);
            Preconditions.checkNotNull(date);

            return new Opens(android, ios, date);
        }
    }


}
