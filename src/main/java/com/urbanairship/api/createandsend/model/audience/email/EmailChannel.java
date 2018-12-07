package com.urbanairship.api.createandsend.model.audience.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Map;

public class EmailChannel {
    private final Optional<DateTime> commercialOptedIn;
    private final Optional<DateTime> transactionalOptedIn;
    private final String uaAddress;
    private final Optional<Map<String, String>> substitutions;

    private EmailChannel(Builder builder) {
        uaAddress = builder.uaAddress;
        commercialOptedIn = Optional.fromNullable(builder.commercialOptedIn);
        transactionalOptedIn = Optional.fromNullable(builder.transactionalOptedIn);

        if (!builder.substitutions.build().isEmpty()) {
            this.substitutions = Optional.fromNullable(builder.substitutions.build());
        } else {
            substitutions = Optional.absent();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<DateTime> getCommercialOptedIn() {
        return commercialOptedIn;
    }

    public Optional<DateTime> getTransactionalOptedIn() {
        return transactionalOptedIn;
    }

    public String getUaAddress() {
        return uaAddress;
    }

    public Optional<Map<String, String>> getSubstitutions() {
        return substitutions;
    }

    public static final class Builder {
        private String uaAddress;
        private DateTime commercialOptedIn;
        private DateTime transactionalOptedIn;
        private ImmutableMap.Builder<String, String> substitutions = ImmutableMap.builder();

        public Builder setAddress(String address) {
            this.uaAddress = address;
            return this;
        }

        public Builder setCommertialOptedIn(DateTime commercialOptedInDate) {
            this.commercialOptedIn = commercialOptedInDate;
            return this;
        }

        public Builder setTransactionalOptedIn(DateTime transactionalOptedInDate) {
            this.transactionalOptedIn = transactionalOptedInDate;
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

        public EmailChannel build() {
            Preconditions.checkNotNull(uaAddress, "uaAddress cannot be null.");
            return new EmailChannel(this);
        }
    }
}
