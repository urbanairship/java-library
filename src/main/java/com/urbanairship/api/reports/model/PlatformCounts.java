/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

public final class PlatformCounts {

    private final ImmutableMap<PlatformType, PerPushCounts> pushPlatforms;
    private final ImmutableMap<PlatformType, RichPerPushCounts> richPushPlatforms;
    private final DateTime time;

    public static Builder newBuilder() {
        return new Builder();
    }

    private PlatformCounts(ImmutableMap<PlatformType, PerPushCounts> pushPlatforms,
                           ImmutableMap<PlatformType, RichPerPushCounts> richPushPlatforms,
                           DateTime time) {
        this.pushPlatforms = pushPlatforms;
        this.richPushPlatforms = richPushPlatforms;
        this.time = time;
    }

    public ImmutableMap<PlatformType, PerPushCounts> getPushPlatforms() {
        return pushPlatforms;
    }

    public ImmutableMap<PlatformType, RichPerPushCounts> getRichPushPlatforms() {
        return richPushPlatforms;
    }

    public DateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PlatformCounts{" +
                "pushPlatforms=" + pushPlatforms +
                ", richPushPlatforms=" + richPushPlatforms +
                ", time=" + time +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pushPlatforms, richPushPlatforms, time);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlatformCounts other = (PlatformCounts) obj;
        return Objects.equal(this.pushPlatforms, other.pushPlatforms) && Objects.equal(this.richPushPlatforms, other.richPushPlatforms) && Objects.equal(this.time, other.time);
    }

    public static class Builder {
        private ImmutableMap.Builder<PlatformType, PerPushCounts> pushPlatforms = ImmutableMap.builder();
        private ImmutableMap.Builder<PlatformType, RichPerPushCounts> richPushPlatforms = ImmutableMap.builder();
        private DateTime time;

        private Builder() {  }

        public Builder addPlatform(PlatformType type, PerPushCounts count) {
            this.pushPlatforms.put(type, count);
            return this;
        }

        public Builder addRichPlatform(PlatformType type, RichPerPushCounts count) {
            this.richPushPlatforms.put(type, count);
            return this;
        }

        public Builder setTime(DateTime value) {
            this.time = value;
            return this;
        }

        public PlatformCounts build() {
            return new PlatformCounts(pushPlatforms.build(), richPushPlatforms.build(), time);
        }

    }
}
