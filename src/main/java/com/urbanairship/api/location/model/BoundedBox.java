/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.model;

/**
 * Represents the location bounds.
 */
public final class BoundedBox {

    private final Point cornerOne;
    private final Point cornerTwo;

    private BoundedBox(Point cornerOne, Point cornerTwo) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the first corner.
     *
     * @return The first corner as a Point object.
     */
    public Point getCornerOne() {
        return cornerOne;
    }

    /**
     * Get the second corner.
     *
     * @return The second corner as a Point object.
     */
    public Point getCornerTwo() {
        return cornerTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoundedBox that = (BoundedBox) o;

        if (cornerOne != null ? !cornerOne.equals(that.cornerOne) : that.cornerOne != null) {
            return false;
        }
        if (cornerTwo != null ? !cornerTwo.equals(that.cornerTwo) : that.cornerTwo != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = cornerOne != null ? cornerOne.hashCode() : 0;
        result = 31 * result + (cornerTwo != null ? cornerTwo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoundedBox{" +
                "cornerOne=" + cornerOne +
                ", cornerTwo=" + cornerTwo +
                '}';
    }

    public static class Builder {
        private Point cornerOne;
        private Point cornerTwo;

        private Builder() {
        }

        /**
         * Set the first corner.
         *
         * @param value The first corner as a Point.
         * @return Builder
         */
        public Builder setCornerOne(Point value) {
            this.cornerOne = value;
            return this;
        }

        /**
         * Set the second corner.
         *
         * @param value The second corner as a Point.
         * @return Builder
         */
        public Builder setCornerTwo(Point value) {
            this.cornerTwo = value;
            return this;
        }

        /**
         * Build the BoundBox object.
         *
         * @return Point
         */
        public BoundedBox build() {
            return new BoundedBox(cornerOne, cornerTwo);
        }
    }
}