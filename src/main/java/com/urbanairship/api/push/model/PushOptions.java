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

    private PushOptions(Optional<PushExpiry> expiry) {
        this.expiry = expiry;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushOptions that = (PushOptions) o;

        return !(expiry != null ? !expiry.equals(that.expiry) : that.expiry != null);
    }

    @Override
    public int hashCode() {
        return (expiry != null ? expiry.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "PushOptionsPayload{" +
                "expiry=" + expiry +
                '}';
    }

    public static class Builder {
        private PushExpiry expiry = null;

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

        public PushOptions build() {
            return new PushOptions(Optional.fromNullable(expiry));
        }
    }
}
