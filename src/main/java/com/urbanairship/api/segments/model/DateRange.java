/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.urbanairship.api.push.model.audience.location.DateRangeUnit;

public final class DateRange {

    private final DateRangeUnit dateRangeUnit;
    private final String rangeStart;
    private final String rangeEnd;

    public DateRange(DateRangeUnit dateRangeUnit, String rangeStart, String rangeEnd) {
        this.dateRangeUnit = dateRangeUnit;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public DateRangeUnit getDateRangeUnit() {
        return dateRangeUnit;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateRange dateRange = (DateRange) o;

        if (dateRangeUnit != dateRange.dateRangeUnit) {
            return false;
        }
        if (rangeEnd != null ? !rangeEnd.equals(dateRange.rangeEnd) : dateRange.rangeEnd != null) {
            return false;
        }
        if (rangeStart != null ? !rangeStart.equals(dateRange.rangeStart) : dateRange.rangeStart != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateRangeUnit != null ? dateRangeUnit.hashCode() : 0;
        result = 31 * result + (rangeStart != null ? rangeStart.hashCode() : 0);
        result = 31 * result + (rangeEnd != null ? rangeEnd.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "dateRangeUnit=" + dateRangeUnit +
                ", rangeStart='" + rangeStart + '\'' +
                ", rangeEnd='" + rangeEnd + '\'' +
                '}';
    }
}
