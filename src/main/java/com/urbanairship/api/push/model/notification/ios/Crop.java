/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.google.common.primitives.Floats;
import com.urbanairship.api.push.model.PushModelObject;

import java.math.BigDecimal;

/**
 * Crop for iOS specific media push messages.
 */
public final class Crop extends PushModelObject{

    private final Optional<BigDecimal> x;
    private final Optional<BigDecimal> y;
    private final Optional<BigDecimal> height;
    private final Optional<BigDecimal> width;

    private Crop(Optional<BigDecimal> x, Optional<BigDecimal> y, Optional<BigDecimal> height, Optional<BigDecimal> width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Get a Crop builder object that describes the crop parameters to be used in the thumbnail.
     * Each field is a decimal, normalized from 0 to 1
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the x BigDecimal offset value where the crop begins.
     * @return Optional BigDecimal representation of x
     */
    public Optional<BigDecimal> getX() {
        return x;
    }

    /**
     * Get the y BigDecimal offset value where the crop begins.
     * @return Optional BigDecimal representation of y
     */
    public Optional<BigDecimal> getY() {
        return y;
    }

    /**
     * Get the BigDecimal height of the final crop.
     * @return Optional BigDecimal representation of the height
     */
    public Optional<BigDecimal> getHeight() {
        return height;
    }

    /**
     * Get the BigDecimal width of the final crop.
     * @return Optional BigDecimal representation of the width
     */
    public Optional<BigDecimal> getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Crop that = (Crop)o;
        if (x != null ? !x.equals(that.x) : that.x != null) {
            return false;
        }
        if (y != null ? !y.equals(that.y) : that.y != null) {
            return false;
        }
        if (height != null ? !height.equals(that.height) : that.height != null) {
            return false;
        }
        if (width != null ? !width.equals(that.width) : that.width != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        return  result;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    public static class Builder {
        private BigDecimal x = null;
        private BigDecimal y = null;
        private BigDecimal height = null;
        private BigDecimal width = null;

        private Builder() { }

        /**
         * Set the x value where offset crop begins
         * @param x BigDecimal
         * @return Builder
         **/
        public Builder setX(BigDecimal x) {
            this.x = x;
            return this;
        }

        /**
         * Set the y value where offset crop begins
         * @param y BigDecimal
         * @return Builder
         **/
        public Builder setY(BigDecimal y) {
            this.y = y;
            return this;
        }

        /**
         * Set the height of the final crop
         * @param height BigDecimal
         * @return Builder
         **/
        public Builder setHeight(BigDecimal height) {
            this.height = height;
            return this;
        }

        /**
         * Set the width of the final crop
         * @param width BigDecimal
         * @return Builder
         **/
        public Builder setWidth(BigDecimal width) {
            this.width = width;
            return this;
        }

        /**
         * Build Crop
         * @return Crop
         */
        public Crop build() {
            return new Crop(Optional.fromNullable(x),
                            Optional.fromNullable(y),
                            Optional.fromNullable(height),
                            Optional.fromNullable(width));
        }
    }
}