/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;

/**
 * Optional features for a Push payload for the Urban Airship API.
 * Created for future optional features to be added.
 **/

public class PushOptions extends PushModelObject {

    private final Optional<PushExpiry> expiry;
    private final Optional<PushNoThrottle> noThrottle;

    private PushOptions(Optional<PushExpiry> expiry,Optional<PushNoThrottle> noThrottle) {
        this.expiry = expiry;
        this.noThrottle = noThrottle;
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
     * Get the no_throttle value.  This is optional.
     * @return Optional&lt;noThrottle&gt;
     **/
    public Optional<PushNoThrottle> getNoThrottle() {
        return noThrottle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushOptions that = (PushOptions) o;

        if (expiry != null && !expiry.equals(that.expiry)) {
            return false;
        }

        return !(noThrottle != null ? !noThrottle.equals(that.noThrottle) : that.noThrottle != null);
    }

    @Override
    public int hashCode() {
        int result = expiry.hashCode();
        result = 31 * result + noThrottle.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PushOptions{"
            + "expiry=" + expiry
            + ", noThrottle=" + noThrottle
            + '}';
    }

    public static class Builder {
        private PushExpiry expiry = null;
        private PushNoThrottle noThrottle = null;

        private Builder() { }

        /**
         * Set the expiry
         * @param value Long
         * @return Long
         **/
        public Builder setExpiry(PushExpiry value) {
            this.expiry = value;
            return this;
        }

        /**
         * Set no_throttle value
         * @param value Boolean
         * @return Builder
         **/
        public Builder setNoThrottle(PushNoThrottle value) {
            this.noThrottle = value;
            return this;
        }

        public PushOptions build() {
            return new PushOptions(Optional.fromNullable(expiry),Optional.fromNullable(noThrottle));
        }
    }
}
