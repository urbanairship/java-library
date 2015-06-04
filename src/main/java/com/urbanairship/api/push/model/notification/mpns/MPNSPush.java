/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public class MPNSPush
{
    public enum Type {
        TOAST,
        TILE;

        public static Type get(String value) {
            for (Type type : values()) {
                if (value.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
            return null;
        }
    }

    public enum BatchingInterval {
        IMMEDIATE,
        SHORT,
        LONG;

        public static BatchingInterval get(String value) {
            for (BatchingInterval interval : values()) {
                if (value.equalsIgnoreCase(interval.name())) {
                    return interval;
                }
            }
            return null;
        }
    }

    private final Type type;
    // TODO: This is part of the payload now, but would be more
    // appropriate in the push options
    private final BatchingInterval interval;
    private final Optional<MPNSTileData> tile;
    private final Optional<MPNSToastData> toast;

    private MPNSPush(Type type,
                     BatchingInterval interval,
                     Optional<MPNSTileData> tile,
                     Optional<MPNSToastData> toast)
    {
        this.type = type;
        this.interval = interval;
        this.tile = tile;
        this.toast = toast;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Type getType() {
        return type;
    }

    public BatchingInterval getBatchingInterval() {
        return interval;
    }

    public Optional<MPNSTileData> getTile() {
        return tile;
    }

    public Optional<MPNSToastData> getToast() {
        return toast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPNSPush that = (MPNSPush)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (interval != null ? !interval.equals(that.interval) : that.interval != null) {
            return false;
        }
        if (tile != null ? !tile.equals(that.tile) : that.tile != null) {
            return false;
        }
        if (toast != null ? !toast.equals(that.toast) : that.toast != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (type != null ? type.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        result = 31 * result + (tile != null ? tile.hashCode() : 0);
        result = 31 * result + (toast != null ? toast.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Type type;
        private BatchingInterval interval = BatchingInterval.IMMEDIATE;
        private MPNSTileData tile;
        private MPNSToastData toast;

        private Builder() { }

        public Builder setType(Type value) {
            this.type = value;
            return this;
        }

        public Builder setBatchingInterval(BatchingInterval value) {
            if (value != null) { this.interval = value; }
            return this;
        }

        public Builder setTile(MPNSTileData value) {
            this.tile = value;
            return this;
        }

        public Builder setToast(MPNSToastData value) {
            this.toast = value;
            return this;
        }

        public MPNSPush build() {
            checkArgument(type != null && (type == Type.TOAST || type == Type.TILE), "type must be one of 'toast' or 'tile'");
            if ( type == Type.TILE ) {
                checkArgument(tile != null, "tile data was missing");
            } else if ( type == Type.TOAST ) {
                checkArgument(toast != null, "toast data was missing");
            }
            return new MPNSPush(type,
                                interval,
                                Optional.fromNullable(tile),
                                Optional.fromNullable(toast));
        }
    }
}
