/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.model.ios;


import com.google.common.base.Objects;

public final class QuietTime {

    private final String start;
    private final String end;

    private QuietTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "QuietTime{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(start, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final QuietTime other = (QuietTime) obj;
        return Objects.equal(this.start, other.start) && Objects.equal(this.end, other.end);
    }

    public final static class Builder {
        private String start = null;
        private String end = null;

        private Builder() {
        }

        public Builder setStart(String start) {
            this.start = start;
            return this;
        }

        public Builder setEnd(String end) {
            this.end = end;
            return this;
        }

        public QuietTime build() {
            return new QuietTime(start, end);
        }
    }
}