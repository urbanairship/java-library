/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public final class TimeInApp {

    private final float android;
    private final float ios;
    private final DateTime date;

    private TimeInApp(float android, float ios, DateTime date) {
        this.android = android;
        this.ios = ios;
        this.date = date;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public float getAndroid() {
        return android;
    }

    public float getIos() {
        return ios;
    }

    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "TimeInApp{" +
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
        final TimeInApp other = (TimeInApp) obj;
        return Objects.equal(this.android, other.android) && Objects.equal(this.ios, other.ios) && Objects.equal(this.date, other.date);
    }

    public static class Builder {

        private float android;
        private float ios;
        private DateTime date;

        private Builder() {
        }

        public Builder setAndroid(float value) {
            this.android = value;
            return this;
        }

        public Builder setIOS(float value) {
            this.ios = value;
            return this;
        }

        public Builder setDate(DateTime value) {
            this.date = value;
            return this;
        }

        public TimeInApp build() {
            Preconditions.checkNotNull(android);
            Preconditions.checkNotNull(ios);
            Preconditions.checkNotNull(date);

            return new TimeInApp(android, ios, date);
        }
    }
}
