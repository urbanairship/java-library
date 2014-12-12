/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

public final class LocationIdentifier {

    private final boolean alias;
    private final LocationAlias locationAlias;
    private final String id;

    public LocationIdentifier(LocationAlias locationAlias) {
        this(true, locationAlias, null);
    }

    public LocationIdentifier(String id) {
        this(false, null, id);
    }

    private LocationIdentifier(boolean alias, LocationAlias locationAlias, String id) {
        this.alias = alias;
        this.locationAlias = locationAlias;
        this.id = id;
    }

    public boolean isAlias() {
        return alias;
    }

    public LocationAlias getAlias() {
        return locationAlias;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationIdentifier that = (LocationIdentifier) o;

        if (alias != that.alias) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (locationAlias != null ? !locationAlias.equals(that.locationAlias) : that.locationAlias != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (alias ? 1 : 0);
        result = 31 * result + (locationAlias != null ? locationAlias.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationIdentifier{" +
                "alias=" + alias +
                ", locationAlias=" + locationAlias +
                ", id='" + id + '\'' +
                '}';
    }
}
