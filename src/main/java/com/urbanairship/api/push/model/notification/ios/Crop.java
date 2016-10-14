/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.google.common.primitives.Floats;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Crop for iOS specific media push messages.
 */
public final class Crop extends PushModelObject{

    private final Optional<Float> x;
    private final Optional<Float> y;
    private final Optional<Float> height;
    private final Optional<Float> width;

    private Crop(Optional<Float> x, Optional<Float> y, Optional<Float> height, Optional<Float> width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Get an CropBuilder
     * @return CropBuilder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the x float value.
     * @return x
     */
    public Optional<Float> getX() {
        return x;
    }

    /**
     * Get the y float value.
     * @return y
     */
    public Optional<Float> getY() {
        return y;
    }

    /**
     * Get the height float value.
     * @return height
     */
    public Optional<Float> getHeight() {
        return height;
    }

    /**
     * Get the width float value.
     * @return width
     */
    public Optional<Float> getWidth() {
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
        private Float x = null;
        private Float y = null;
        private Float height = null;
        private Float width = null;

        private Builder() { }

        /**
         * Set the x value
         * @param x Float
         * @return Builder
         **/
        public Builder setX(Float x) {
            this.x = x;
            return this;
        }

        /**
         * Set the y value
         * @param y Float
         * @return Builder
         **/
        public Builder setY(Float y) {
            this.y = y;
            return this;
        }

        /**
         * Set the height
         * @param height Float
         * @return Builder
         **/
        public Builder setHeight(Float height) {
            this.height = height;
            return this;
        }

        /**
         * Set the width
         * @param width Float
         * @return Builder
         **/
        public Builder setWidth(Float width) {
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