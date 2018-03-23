/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.model;

import com.google.common.base.Preconditions;

/**
 * Represents a location centroid.
 */
public final class Point {

    private final double latitude;
    private final double longitude;

    private Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * New Point builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the latitude.
     *
     * @return The latitude as a double.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude.
     *
     * @return The longitude as a double.
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        if (Double.compare(point.latitude, latitude) != 0) {
            return false;
        }
        if (Double.compare(point.longitude, longitude) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = latitude != +0.0d ? Double.doubleToLongBits(latitude) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = longitude != +0.0d ? Double.doubleToLongBits(longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public static class Builder {
        private double latitude;
        private double longitude;

        private Builder() {
        }

        /**
         * Set the latitude.
         *
         * @param value The latitude as a double.
         * @return Builder
         */
        public Builder setLatitude(double value) {
            this.latitude = value;
            return this;
        }

        /**
         * Set the longitude.
         *
         * @param value The longitude as a double.
         * @return Builder
         */
        public Builder setLongitude(double value) {
            this.longitude = value;
            return this;
        }

        /**
         * Build the Point object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. latitude &gt;= -90d {@code &&} latitude &lt;= 90d.
         * 2. longitude &gt;= -180d {@code &&} longitude &lt;= 180d.
         * * </pre>
         *
         * @return Point
         */
        public Point build() {
            Preconditions.checkArgument(latitude >= -90d && latitude <= 90d, "latitude must be valid");
            Preconditions.checkArgument(longitude >= -180d && longitude <= 180d, "longitude must be valid");

            return new Point(latitude, longitude);
        }
    }
}