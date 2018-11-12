/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience.location;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.SelectorVisitor;

/**
 * API model object for all "location" selector expressions. They can
 * appear in an API payload's audience selector expression, or in a
 * segment definition.
 */
public final class LocationSelector extends PushModelObject implements Selector {

    private final LocationIdentifier locationIdentifier;
    private final DateRange dateRange;

    private LocationSelector(LocationIdentifier locationIdentifier, DateRange dateRange) {
        this.locationIdentifier = locationIdentifier;
        this.dateRange = dateRange;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public SelectorType getType() {
        return SelectorType.LOCATION;
    }

    @Override
    public DeviceTypeData getApplicableDeviceTypes() {
        //return DeviceTypeData.all();
        return DeviceTypeData.of(getType().getPlatform().get());
    }

    @Override
    public void accept(SelectorVisitor visitor) {
        visitor.enter(this);
        visitor.exit(this);
    }

    public LocationIdentifier getLocationIdentifier() {
        return locationIdentifier;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationSelector that = (LocationSelector) o;

        if (dateRange != null ? !dateRange.equals(that.dateRange) : that.dateRange != null) {
            return false;
        }
        if (locationIdentifier != null ? !locationIdentifier.equals(that.locationIdentifier)
                : that.locationIdentifier != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = locationIdentifier != null ? locationIdentifier.hashCode() : 0;
        result = 31 * result + (dateRange != null ? dateRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationSelector{" +
            "dateRange=" + dateRange +
            ", locationIdentifier=" + locationIdentifier +
            '}';
    }

    public static class Builder {
        private LocationIdentifier id = null;
        private DateRange range = null;

        private Builder() { }

        public Builder setId(LocationIdentifier value) {
            this.id = value;
            return this;
        }

        public Builder setDateRange(DateRange value) {
            this.range = value;
            return this;
        }

        public LocationSelector build() {
            Preconditions.checkNotNull(id);
            Preconditions.checkNotNull(range);
            return new LocationSelector(id, range);
        }
    }
}
