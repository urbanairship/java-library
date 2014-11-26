/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;

import java.util.List;

public class ReportsAPITimeInAppResponse {

    private final List<TimeInApp> object;

    private ReportsAPITimeInAppResponse(List<TimeInApp> object) {
        this.object = object;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<TimeInApp> getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "ReportsAPITimeInAppResponse{" +
                "object=" + object +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ReportsAPITimeInAppResponse other = (ReportsAPITimeInAppResponse) obj;
        return Objects.equal(this.object, other.object);
    }

    public static class Builder {
        private List<TimeInApp> object;

        private Builder() { }

        public Builder setObject(List<TimeInApp> value) {
            this.object = value;
            return this;
        }

        public ReportsAPITimeInAppResponse build() {
            return new ReportsAPITimeInAppResponse(object);
        }
    }
}
