/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.urbanairship.api.push.model.audience.location.DateRangeUnit;

public final class RecentDateRange {

    private final DateRangeUnit dateRangeUnit;
    private final int units;

    public RecentDateRange(DateRangeUnit dateRangeUnit, int units) {
        this.dateRangeUnit = dateRangeUnit;
        this.units = units;
    }

    public DateRangeUnit getDateRangeUnit() {
        return dateRangeUnit;
    }

    public int getUnits() {
        return units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecentDateRange that = (RecentDateRange) o;

        if (units != that.units) {
            return false;
        }
        if (dateRangeUnit != that.dateRangeUnit) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateRangeUnit != null ? dateRangeUnit.hashCode() : 0;
        result = 31 * result + units;
        return result;
    }

    @Override
    public String toString() {
        return "RecentDateRange{" +
                "dateRangeUnit=" + dateRangeUnit +
                ", units=" + units +
                '}';
    }
}
