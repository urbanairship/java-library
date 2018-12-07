package com.urbanairship.api.createandsend.model.audience.sms;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a single SmsChannel for create and send audience.
 */
public final class SmsChannel {
    private final String msisdn;
    private final DateTime optedIn;
    private final String sender;

    private final Optional<ImmutableMap<String, String>> substitutions;

    private SmsChannel(Builder builder) {
        msisdn = builder.msisdn;
        optedIn = builder.optedIn;
        sender = builder.sender;

        if (builder.substitutions.build().isEmpty()) {
            substitutions = Optional.empty();
        } else {
            substitutions = Optional.of(builder.substitutions.build());
        }
    }

    /**
     * Builder for SmsChannel
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the msisdn. The address that you want to populate the channel's address field.
     * @return String
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Get the date when the user opted in.
     * @return DateTime
     */
    public DateTime getOptedIn() {
        return optedIn;
    }

    /**
     * Get the sender. The address that you want to populate the channel's address field.
     * @return String
     */
    public String getSender() {
        return sender;
    }

    /**
     * Get the substitutions. Additional key-value pairs representing variables your notification template.
     * Your variable keys must not be prefixed with ua_
     * @return Optional ImmutableMap of Strings.
     */
    public Optional<ImmutableMap<String, String>> getSubstitutions() {
        return substitutions;
    }

    @Override
    public String toString() {
        return "SmsChannel{" +
                "msisdn='" + msisdn + '\'' +
                ", optedIn='" + optedIn + '\'' +
                ", sender='" + sender + '\'' +
                ", substitutions=" + substitutions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsChannel that = (SmsChannel) o;
        return Objects.equal(msisdn, that.msisdn) &&
                Objects.equal(optedIn, that.optedIn) &&
                Objects.equal(sender, that.sender) &&
                Objects.equal(substitutions, that.substitutions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msisdn, optedIn, sender, substitutions);
    }

    public final static class Builder {
        private String msisdn;
        private DateTime optedIn;
        private String sender;

        private ImmutableMap.Builder<String, String> substitutions = ImmutableMap.builder();

        /**
         * Set the msisdn. The address that you want to populate the channel's address field.
         * @param msisdn String
         * @return SmsChannel Builder
         */
        public Builder setMsisdn(String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        /**
         * Set the date when the user opted in.
         * @param optedIn DateTime
         * @return SmsChannel Builder
         */
        public Builder setOptedIn(DateTime optedIn) {
            this.optedIn = optedIn;
            return this;
        }

        /**
         * Set the sender. The address that you want to populate the channel's address field.
         * @param sender String
         * @return SmsChannel Builder
         */
        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        /**
         * Add a substitution. Additional key-value pairs representing variables your notification template.
         * Your variable keys must not be prefixed with ua_
         * @param key String
         * @param value String
         * @return SmsChannel Builder
         */
        public Builder addSubstitution(String key, String value) {
            substitutions.put(key, value);
            return this;
        }

        /**
         * Add all substitutions. Additional key-value pairs representing variables your notification template.
         * Your variable keys must not be prefixed with ua_
         * @param substitutions Map of Strings
         * @return SmsChannel Builder
         */
        public Builder addAllSubstitutions(Map<String, String> substitutions) {
            substitutions.putAll(substitutions);
            return this;
        }

        public SmsChannel build() {
            Preconditions.checkNotNull(msisdn, "msisdn cannot be null.");
            Preconditions.checkNotNull(optedIn, "optedIn must not be null.");
            Preconditions.checkNotNull(sender, "sender must not be null.");

            return new SmsChannel(this);
        }
    }
}
