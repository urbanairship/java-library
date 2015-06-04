/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public class WNSPush
{
    public enum Type {
        TOAST,
        TILE,
        BADGE;

        public String getIdentifier() {
            return name().toLowerCase();
        }

        public static Type get(String value) {
            for (Type type : values()) {
                if (value.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
            return null;
        }
    }

    public enum CachePolicy {
        CACHE,
        NO_CACHE;

        private final String id;

        CachePolicy() {
            id = name().toLowerCase().replace('_', '-');
        }

        public String getIdentifier() {
            return id;
        }

        public static CachePolicy get(String value) {
            for (CachePolicy policy : values()) {
                if (value.equalsIgnoreCase(policy.getIdentifier())) {
                    return policy;
                }
            }
            return null;
        }
    }

    private final Type type;
    private final Optional<WNSToastData> toast;
    private final Optional<WNSTileData> tile;
    private final Optional<WNSBadgeData> badge;
    private final Optional<CachePolicy> cachePolicy;

    private WNSPush(Type type,
                    Optional<WNSToastData> toast,
                    Optional<WNSTileData> tile,
                    Optional<WNSBadgeData> badge,
                    Optional<CachePolicy> cachePolicy)
    {
        this.type = type;
        this.toast = toast;
        this.tile = tile;
        this.badge = badge;
        this.cachePolicy = cachePolicy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Type getType() {
        return this.type;
    }

    public Optional<WNSToastData> getToast() {
        return this.toast;
    }

    public Optional<WNSTileData> getTile() {
        return this.tile;
    }

    public Optional<WNSBadgeData> getBadge() {
        return this.badge;
    }

    public Optional<CachePolicy> getCachePolicy() {
        return this.cachePolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSPush that = (WNSPush)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (toast != null ? !toast.equals(that.toast) : that.toast != null) {
            return false;
        }
        if (tile != null ? !tile.equals(that.tile) : that.tile != null) {
            return false;
        }
        if (badge != null ? !badge.equals(that.badge) : that.badge != null) {
            return false;
        }
        if (cachePolicy != null ? !cachePolicy.equals(that.cachePolicy) : that.cachePolicy != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (type != null ? type.hashCode() : 0);
        result = 31 * result + (toast != null ? toast.hashCode() : 0);
        result = 31 * result + (tile != null ? tile.hashCode() : 0);
        result = 31 * result + (badge != null ? badge.hashCode() : 0);
        result = 31 * result + (cachePolicy != null ? cachePolicy.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Type type;
        private WNSToastData toast;
        private WNSTileData tile;
        private WNSBadgeData badge;
        private CachePolicy cachePolicy;

        private Builder() { }

        public Builder setType(Type value) {
            this.type = value;
            return this;
        }

        public Builder setToast(WNSToastData value) {
            this.toast = value;
            return this;
        }

        public Builder setTile(WNSTileData value) {
            this.tile = value;
            return this;
        }

        public Builder setBadge(WNSBadgeData value) {
            this.badge = value;
            return this;
        }

        public Builder setCachePolicy(CachePolicy value) {
            this.cachePolicy = value;
            return this;
        }

        public WNSPush build() {
            checkArgument(type != null, "type must be one of 'toast', 'tile', or 'badge'");
            if (type == Type.TOAST) {
                checkArgument(toast != null, "Must supply a value for 'toast'");
            } else if (type == Type.TILE) {
                checkArgument(tile != null, "Must supply a value for 'tile'");
            } else if (type == Type.BADGE) {
                checkArgument(badge != null, "Must supply a value for 'badge'");
            }
            return new WNSPush(type,
                               Optional.fromNullable(toast),
                               Optional.fromNullable(tile),
                               Optional.fromNullable(badge),
                               Optional.fromNullable(cachePolicy));
        }
    }
}
