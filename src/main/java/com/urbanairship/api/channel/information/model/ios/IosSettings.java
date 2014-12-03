/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.model.ios;


import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public final class IosSettings {
    private static final IosSettings DEFAULT_INSTANCE = newBuilder().build();

    private final int badge;
    private final Optional<QuietTime> quiettime;
    private final Optional<String> timezone;

    public static IosSettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private IosSettings(int badge, Optional<QuietTime> quiettime, Optional<String> timezone) {
        this.badge = badge;
        this.quiettime = quiettime;
        this.timezone = timezone;
    }

    public int getBadge() {
        return badge;
    }

    public Optional<QuietTime> getQuietTime() {
        return quiettime;
    }

    public Optional<String> getTimezone() {
        return timezone;
    }


    @Override
    public String toString() {
        return "IosSettings{" +
                "badge=" + badge +
                ", quiettime=" + quiettime +
                ", timezone=" + timezone +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(badge, quiettime, timezone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final IosSettings other = (IosSettings) obj;
        return Objects.equal(this.badge, other.badge) && Objects.equal(this.quiettime, other.quiettime) && Objects.equal(this.timezone, other.timezone);
    }

    public final static class Builder {

        private int badge = 0;
        private QuietTime quiettime = null;
        private String timezone = null;

        private Builder() { }

        public Builder setBadge(int badge) {
            this.badge = badge;
            return this;
        }

        public Builder setQuietTime(QuietTime quiettime) {
            this.quiettime = quiettime;
            return this;
        }


        public Builder setTimeZone(String timeZone) {
            this.timezone = timeZone;
            return this;
        }

        public IosSettings build() {
            Preconditions.checkArgument(badge >= 0, "'badge' must be non-negative.");

            return new IosSettings(
                    badge,
                    Optional.fromNullable(quiettime),
                    Optional.fromNullable(timezone)
            );
        }
    }

}