package com.urbanairship.api.createandsend.model.audience.sms;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Map;
import java.util.Optional;

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

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public DateTime getOptedIn() {
        return optedIn;
    }

    public String getSender() {
        return sender;
    }

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

        public Builder setMsisdn(String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        public Builder setOptedIn(DateTime optedIn) {
            this.optedIn = optedIn;
            return this;
        }

        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder addSubstitution(String key, String value) {
            substitutions.put(key, value);
            return this;
        }

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
