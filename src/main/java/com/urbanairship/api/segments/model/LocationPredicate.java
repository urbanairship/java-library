/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

/**
 * Represents a segments predicate that identifies a location with a date range.
 * <p/>
 * Note the date range may be specified via a "recent" date range or a static date range.  Use the {@link #isRecentDateRangeBounded()} method
 * to determine which is used for an instance of a LocationPredicate.  If {@link #isRecentDateRangeBounded()} returns true, then the {@link #getRecentDateRange()}
 * method is valid and should be used.  If {@link #isRecentDateRangeBounded()} returns false, then the {@link #getDateRange()} method is valid
 * and should be used.
 */
public final class LocationPredicate implements Predicate {

    private final LocationIdentifier locationIdentifier;
    private final boolean recentDateRangeBounded;
    private final DateRange dateRange;
    private final RecentDateRange recentDateRange;
    private final PresenceTimeframe presenceTimeframe;

    public LocationPredicate(LocationIdentifier locationIdentifier, DateRange dateRange, PresenceTimeframe presenceTimeframe) {
        this(locationIdentifier, dateRange, false, null, presenceTimeframe);
    }

    public LocationPredicate(LocationIdentifier locationIdentifier, RecentDateRange recentDateRange, PresenceTimeframe presenceTimeframe) {
        this(locationIdentifier, null, true, recentDateRange, presenceTimeframe);
    }

    private LocationPredicate(LocationIdentifier locationIdentifier, DateRange dateRange, boolean recentDateRangeBounded, RecentDateRange recentDateRange, PresenceTimeframe presenceTimeframe) {
        this.dateRange = dateRange;
        this.locationIdentifier = locationIdentifier;
        this.recentDateRangeBounded = recentDateRangeBounded;
        this.recentDateRange = recentDateRange;
        this.presenceTimeframe = presenceTimeframe;
    }

    @Override
    public String getName() {
        return "location";
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    public LocationIdentifier getLocationIdentifier() {
        return locationIdentifier;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public boolean isRecentDateRangeBounded() {
        return recentDateRangeBounded;
    }

    public RecentDateRange getRecentDateRange() {
        return recentDateRange;
    }

    public PresenceTimeframe getPresenceTimeframe() {
        return presenceTimeframe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationPredicate predicate = (LocationPredicate) o;

        if (recentDateRangeBounded != predicate.recentDateRangeBounded) {
            return false;
        }
        if (dateRange != null ? !dateRange.equals(predicate.dateRange) : predicate.dateRange != null) {
            return false;
        }
        if (locationIdentifier != null ? !locationIdentifier.equals(predicate.locationIdentifier)
                : predicate.locationIdentifier != null) {
            return false;
        }
        if (presenceTimeframe != predicate.presenceTimeframe) {
            return false;
        }
        if (recentDateRange != null ? !recentDateRange.equals(predicate.recentDateRange)
                : predicate.recentDateRange != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationIdentifier != null ? locationIdentifier.hashCode() : 0;
        result = 31 * result + (recentDateRangeBounded ? 1 : 0);
        result = 31 * result + (dateRange != null ? dateRange.hashCode() : 0);
        result = 31 * result + (recentDateRange != null ? recentDateRange.hashCode() : 0);
        result = 31 * result + (presenceTimeframe != null ? presenceTimeframe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationPredicate{" +
                "dateRange=" + dateRange +
                ", locationIdentifier=" + locationIdentifier +
                ", recentDateRangeBounded=" + recentDateRangeBounded +
                ", recentDateRange=" + recentDateRange +
                ", presenceTimeframe=" + presenceTimeframe +
                '}';
    }
}
