/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Options for iOS specific MediaAttachment push messages.
 */
public final class Options extends PushModelObject{

    private final Optional<Integer> time;
    private final Optional<Crop> crop;

    private Options(Optional<Integer> time, Optional<Crop> crop) {
        this.time = time;
        this.crop = crop;
    }

    /**
     * Get an OptionsBuilder
     * @return OptionsBuilder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the time.
     * @return time
     */
    public Optional<Integer> getTime() {
        return time;
    }

    /**
     * Get the Crop.
     * @return Crop
     */
    public Optional<Crop> getCrop() {
        return crop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Options that = (Options)o;
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
        return "Options{" +
                "time=" + time +
                ", crop=" + crop +
                '}';
    }

    public static class Builder {
        private Integer time;
        private Crop crop;

        private Builder() { }

        /**
         * Set the time Integer.
         * @param time Integer
         * @return Builder
         */
        public Builder setTime(Integer time) {
            this.time = time;
            return this;
        }

        /**
         * Set the Crop object.
         * @param crop Crop
         * @return Builder
         */
        public Builder setCrop(Crop crop) {
            this.crop = crop;
            return this;
        }

        /**
         * Build Options
         * @return Options
         */
        public Options build() {
            return new Options(Optional.fromNullable(time), Optional.fromNullable(crop));
        }
    }
}