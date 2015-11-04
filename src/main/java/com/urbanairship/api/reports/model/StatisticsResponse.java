/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public final class StatisticsResponse {

    private final DateTime start;
    private final int ios;
    private final int blackBerry;
    private final int c2dm;
    private final int gcm;
    private final int windows8;
    private final int windowsPhone8;

    private StatisticsResponse(DateTime start, int ios, int blackBerry, int c2dm, int gcm, int windows8, int windowsPhone8) {
        this.start = start;
        this.ios = ios;
        this.blackBerry = blackBerry;
        this.c2dm = c2dm;
        this.gcm = gcm;
        this.windows8 = windows8;
        this.windowsPhone8 = windowsPhone8;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public DateTime getStart() {
        return start;
    }

    public int getIosCount() {
        return ios;
    }

    public int getBlackBerryCount() {
        return blackBerry;
    }

    public int getC2dmCount() {
        return c2dm;
    }

    public int getGcmCount() {
        return gcm;
    }

    public int getWindows8Count() {
        return windows8;
    }

    public int getWindowsPhone8Count() {
        return windowsPhone8;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(start, ios, blackBerry, c2dm, gcm, windows8, windowsPhone8);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final StatisticsResponse other = (StatisticsResponse) obj;
        return Objects.equal(this.start, other.start) && Objects.equal(this.ios, other.ios) && Objects.equal(this.blackBerry, other.blackBerry) && Objects.equal(this.c2dm, other.c2dm) && Objects.equal(this.gcm, other.gcm) && Objects.equal(this.windows8, other.windows8) && Objects.equal(this.windowsPhone8, other.windowsPhone8);
    }

    @Override
    public String toString() {
        return "AppStats{" +
                "start=" + start +
                ", ios=" + ios +
                ", blackBerry=" + blackBerry +
                ", c2dm=" + c2dm +
                ", gcm=" + gcm +
                ", windows8=" + windows8 +
                ", windowsPhone8=" + windowsPhone8 +
                '}';
    }


    public static class Builder {

        private DateTime start;
        private int ios;
        private int blackBerry;
        private int c2dm;
        private int gcm;
        private int windows8;
        private int windowsPhone8;

        private Builder() {
        }

        public Builder setStartTime(DateTime value) {
            this.start = value;
            return this;
        }

        public Builder setIosCount(int value) {
            this.ios = value;
            return this;
        }

        public Builder setBlackBerryCount(int value) {
            this.blackBerry = value;
            return this;
        }

        public Builder setC2dmCount(int value) {
            this.c2dm = value;
            return this;
        }

        public Builder setGcmCount(int value) {
            this.gcm = value;
            return this;
        }

        public Builder setWindows8Count(int value) {
            this.windows8 = value;
            return this;
        }

        public Builder setWindowsPhone8Count(int value) {
            this.windowsPhone8 = value;
            return this;
        }

        public StatisticsResponse build() {
            Preconditions.checkNotNull(start, "There must be a start time");
            return new StatisticsResponse(start, ios, blackBerry, c2dm, gcm, windows8, windowsPhone8);
        }
    }
}
