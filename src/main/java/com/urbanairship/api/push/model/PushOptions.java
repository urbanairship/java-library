/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * Optional features for a Push payload for the Urban Airship API.
 * Created for future optional features to be added.
 **/

public class PushOptions extends PushModelObject {

    private final Optional<PushExpiry> expiry;
    private final Optional<Boolean> noThrottle;

    private PushOptions(Builder builder) {
        expiry = Optional.fromNullable(builder.expiry);
        noThrottle = Optional.fromNullable(builder.noThrottle);
    }

    /**
     * Get a PushOptionsPayloadBuilder
     * @return PushOptionsPayloadBuilder
     **/
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the expiry (TTL).  This is optional.
     * @return Optional&lt;Expiry&gt;
     **/
    public Optional<PushExpiry> getExpiry() {
        return expiry;
    }

    /**
     * Get the noThrottle value. If true, the push will ignore global throttling rates that you have configured
     * for the application, resulting in delivery as quickly as possible.
     * @return Optional Boolean
     */
    public Optional<Boolean> getNoThrottle() {
        return noThrottle;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expiry, noThrottle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PushOptions that = (PushOptions) o;
        return Objects.equal(expiry, that.expiry) &&
                Objects.equal(noThrottle, that.noThrottle);
    }

    @Override
    public String toString() {
        return "PushOptions{" +
                "expiry=" + expiry +
                ", noThrottle=" + noThrottle +
                '}';
    }

    public static class Builder {
        private PushExpiry expiry = null;
        private Boolean noThrottle = null;

        private Builder() { }

        /**
         * Set the expiry
         * @param value Long
         * @return PushOptions Builder
         **/
        public Builder setExpiry(PushExpiry value) {
            this.expiry = value;
            return this;
        }

        /**
         * Set the no throttle rate. If true, the push will ignore global throttling rates that you have configured
         * for the application, resulting in delivery as quickly as possible.
         * @param noThrottle Boolean
         * @return PushOptions Builder
         */
        public Builder setNoThrottle(Boolean noThrottle) {
              this.noThrottle = noThrottle;
              return this;
        }

        public PushOptions build() {
            return new PushOptions(this);
        }
    }
}
