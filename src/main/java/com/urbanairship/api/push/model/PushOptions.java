/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Optional features for a Push payload for the Urban Airship API.
 * Created for future optional features to be added.
 **/

public class PushOptions extends PushModelObject {

    private final Optional<PushExpiry> expiry;
    private final Optional<Boolean> noThrottle;
    private final Optional<Boolean> personalization;
    private final Optional<Boolean> redactPayload;
    private final Optional<Boolean> bypassHoldoutGroups;
    private final Optional<Boolean> bypassFrequencyLimits;

    private PushOptions(Builder builder) {
        expiry = Optional.ofNullable(builder.expiry);
        noThrottle = Optional.ofNullable(builder.noThrottle);
        personalization = Optional.ofNullable(builder.personalization);
        redactPayload = Optional.ofNullable(builder.redactPayload);
        bypassHoldoutGroups = Optional.ofNullable(builder.bypassHoldoutGroups);
        bypassFrequencyLimits = Optional.ofNullable(builder.bypassFrequencyLimits);
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

    /**
     * Get the personalization value. If true, the push will allow personalization.
     * @return Optional Boolean
     */
    public Optional<Boolean> getPersonalization() {
        return personalization;
    }

    /**
     * Get the redact payload value. If true, the push will allow redact payload.
     * @return Optional Boolean
     */
    public Optional<Boolean> getRedactPayload() {
        return redactPayload;
    }

    /**
     * Get the bypassHoldoutGroups value. If true, the push will allow bypassHoldoutGroups.
     * @return Optional Boolean
     */
    public Optional<Boolean> getBypassHoldoutGroups() {
        return bypassHoldoutGroups;
    }

    /**
     * Get the bypassFrequencyLimits value. If true, the push will allow bypassFrequencyLimits.
     * @return Optional Boolean
     */
    public Optional<Boolean> getBypassFrequencyLimits() {
        return bypassFrequencyLimits;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PushOptions that = (PushOptions) o;
        return Objects.equals(expiry, that.expiry) && Objects.equals(noThrottle, that.noThrottle) && Objects.equals(personalization, that.personalization) && Objects.equals(redactPayload, that.redactPayload) && Objects.equals(bypassHoldoutGroups, that.bypassHoldoutGroups) && Objects.equals(bypassFrequencyLimits, that.bypassFrequencyLimits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expiry, noThrottle, personalization, redactPayload, bypassHoldoutGroups, bypassFrequencyLimits);
    }

    @Override
    public String toString() {
        return "PushOptions{" +
                "expiry=" + expiry +
                ", noThrottle=" + noThrottle +
                ", personalization=" + personalization +
                ", redactPayload=" + redactPayload +
                ", bypassHoldoutGroups=" + bypassHoldoutGroups +
                ", bypassFrequencyLimits=" + bypassFrequencyLimits +
                '}';
    }

    public static class Builder {
        private PushExpiry expiry = null;
        private Boolean noThrottle = null;
        private Boolean personalization = null;
        private Boolean redactPayload = null;
        private Boolean bypassHoldoutGroups = null;
        private Boolean bypassFrequencyLimits = null;

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

        /**
         * Set the personalization option. If true, the push will allow personalization.
         * @param personalization Boolean
         * @return PushOptions Builder
         */
        public Builder setPersonalization(Boolean personalization) {
            this.personalization = personalization;
            return this;
      }

        /**
         * Set the RedactPayload option. If true, the push will allow RedactPayload.
         * @param redactPayload Boolean
         * @return PushOptions Builder
         */
        public Builder setRedactPayload(Boolean redactPayload) {
            this.redactPayload = redactPayload;
            return this;
        }

        /**
         * Set the bypassHoldoutGroups option. If true, the push will allow personalization.
         * @param bypassHoldoutGroups Boolean
         * @return PushOptions Builder
         */
        public Builder setBypassHoldoutGroups(Boolean bypassHoldoutGroups) {
            this.bypassHoldoutGroups = bypassHoldoutGroups;
            return this;
        }

        /**
         * Set the bypassFrequencyLimits option. If true, the push will allow personalization.
         * @param bypassFrequencyLimits Boolean
         * @return PushOptions Builder
         */
        public Builder setBypassFrequencyLimits(Boolean bypassFrequencyLimits) {
            this.bypassFrequencyLimits = bypassFrequencyLimits;
            return this;
        }

        public PushOptions build() {
            return new PushOptions(this);
        }
    }
}
