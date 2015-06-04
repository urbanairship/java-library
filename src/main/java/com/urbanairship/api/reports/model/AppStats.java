/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public final class AppStats {

    private final DateTime start;
    private final int iOS;
    private final int blackBerry;
    private final int c2dm;
    private final int gcm;
    private final int windows8;
    private final int windowsPhone8;

    private AppStats(DateTime start, int iOS, int blackBerry, int c2dm, int gcm, int windows8, int windowsPhone8) {
        this.start = start;
        this.iOS = iOS;
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

    public int getiOSCount() {
        return iOS;
    }

    public int getBlackBerryCount() {
        return blackBerry;
    }

    public int getC2DMCount() {
        return c2dm;
    }

    public int getGCMCount() {
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
        return Objects.hashCode(start, iOS, blackBerry, c2dm, gcm, windows8, windowsPhone8);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AppStats other = (AppStats) obj;
        return Objects.equal(this.start, other.start) && Objects.equal(this.iOS, other.iOS) && Objects.equal(this.blackBerry, other.blackBerry) && Objects.equal(this.c2dm, other.c2dm) && Objects.equal(this.gcm, other.gcm) && Objects.equal(this.windows8, other.windows8) && Objects.equal(this.windowsPhone8, other.windowsPhone8);
    }

    @Override
    public String toString() {
        return "AppStats{" +
                "start=" + start +
                ", iOS=" + iOS +
                ", blackBerry=" + blackBerry +
                ", c2dm=" + c2dm +
                ", gcm=" + gcm +
                ", windows8=" + windows8 +
                ", windowsPhone8=" + windowsPhone8 +
                '}';
    }


    public static class Builder {

        private DateTime start;
        private int iOS;
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

        public Builder setIOSCount(int value) {
            this.iOS = value;
            return this;
        }

        public Builder setBlackBerryCount(int value) {
            this.blackBerry = value;
            return this;
        }

        public Builder setC2DMCount(int value) {
            this.c2dm = value;
            return this;
        }

        public Builder setGCMCount(int value) {
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

        public AppStats build() {
            Preconditions.checkNotNull(start, "There must be a start time");
            return new AppStats(start, iOS, blackBerry, c2dm, gcm, windows8, windowsPhone8);
        }
    }
}
