/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

/**
 * IOSMediaOptions for iOS specific MediaAttachment push messages.
 */
public final class IOSMediaOptions extends PushModelObject{

    private final Optional<Integer> time;
    private final Optional<Crop> crop;
    private final Optional<Boolean> hidden;

    private IOSMediaOptions(Optional<Integer> time, Optional<Crop> crop, Optional<Boolean> hidden) {
        this.time = time;
        this.crop = crop;
        this.hidden = hidden;
    }

    /**
     * Get a IOSMediaOptions builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the time used for the thumbnail.
     * @return Optional Integer representation of the time
     */
    public Optional<Integer> getTime() {
        return time;
    }

    /**
     * Get the Crop object that describes the crop parameters to be used in the thumbnail.
     * @return Optional Crop object
     */
    public Optional<Crop> getCrop() {
        return crop;
    }

    /**
     * Get the hidden value of the thumbnail.
     * @return Optional Boolean representation of the hidden value
     */
    public Optional<Boolean> getHidden() {
        return hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSMediaOptions that = (IOSMediaOptions)o;
        if (time != null ? !time.equals(that.time) : that.time != null) {
            return false;
        }
        if (crop != null ? !crop.equals(that.crop) : that.crop != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (time != null ? time.hashCode() : 0);
        result = 31 * result + (crop != null ? crop.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSMediaOptions{" +
                "time=" + time +
                ", crop=" + crop +
                '}';
    }

    public static class Builder {
        private Integer time = null;
        private Crop crop= null;
        private Boolean hidden = null;

        private Builder() { }

        /**
         * Set the time Integer of the frame of the animated resource that should be used in the thumbnail.
         * @param time Integer
         * @return Builder
         */
        public Builder setTime(Integer time) {
            this.time = time;
            return this;
        }

        /**
         * Set the Crop object that describes the crop parameters to be used in the thumbnail. Each field is a decimal, normalized from 0 to 1.
         * @param crop Crop
         * @return Builder
         */
        public Builder setCrop(Crop crop) {
            this.crop = crop;
            return this;
        }

        /**
         * Set the hidden Boolean value, when true, the thumbnail will be hidden
         * @param hidden Boolean
         * @return Builder
         */
        public Builder setHidden(Boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        /**
         * Build IOSMediaOptions
         * @return IOSMediaOptions
         */
        public IOSMediaOptions build() {
            return new IOSMediaOptions(Optional.ofNullable(time), Optional.ofNullable(crop), Optional.ofNullable(hidden));
        }
    }
}