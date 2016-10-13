package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

public class Options extends PushModelObject{

    private final Optional<Integer> time;
    private final Optional<Crop> crop;

    private Options(Optional<Integer> time, Optional<Crop> crop) {
        this.time = time;
        this.crop = crop;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Integer> getTime() {
        return time;
    }

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

        public Builder setTime(Integer time) {
            this.time = time;
            return this;
        }

        public Builder setCrop(Crop crop) {
            this.crop = crop;
            return this;
        }

        public Options build() {
            return new Options(Optional.fromNullable(time), Optional.fromNullable(crop));
        }
    }
}

/*
For gifs, this value should be the frame number (an integer, with the first frame being frame 1) to show in the thumbnail

For movies, the value should be the time (in seconds) into the movie from which to grab the thumbnail

This value should not be set for static resources like JPGs.

If the time does not exist in the resource, either because it is out of bounds or because the resource is static,
the thumbnail will not show.
 */