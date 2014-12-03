package com.urbanairship.api.location.model;

import com.google.common.base.Preconditions;

import java.util.List;

public final class Point {

    private final double latitude;
    private final double longitude;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isValid() {
        return latitude >= -90d && latitude <= 90d &&
                longitude >= -180d && longitude <= 180d;
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

        private Builder() { }

        public Builder setLatitude(double value) {
            this.latitude = value;
            return this;
        }

        public Builder setLongitude(double value) {
            this.longitude = value;
            return this;
        }

        public Builder setPoint(List<Double> valueList) {
            this.latitude = valueList.get(0);
            this.latitude = valueList.get(1);
            return this;
        }

        public Point build() {
            Preconditions.checkNotNull(latitude, "latitude must be set");
            Preconditions.checkNotNull(longitude, "longitude must be set");

            return new Point(latitude, longitude);
        }
    }
}