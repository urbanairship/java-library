package com.urbanairship.api.createandsend.model.audience.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Represents a single audience email channel for create and send.
 */
public class EmailChannel {
    private final Optional<DateTime> commercialOptedIn;
    private final Optional<DateTime> transactionalOptedIn;
    private final String uaAddress;
    private final Optional<Map<String, String>> substitutions;
    private final Optional<Map<String, Object>> variables;

    private EmailChannel(Builder builder) {
        uaAddress = builder.uaAddress;
        commercialOptedIn = Optional.fromNullable(builder.commercialOptedIn);
        transactionalOptedIn = Optional.fromNullable(builder.transactionalOptedIn);

        if (!builder.substitutions.build().isEmpty()) {
            this.substitutions = Optional.fromNullable(builder.substitutions.build());
        } else {
            substitutions = Optional.absent();
        }
        if (!builder.variables.build().isEmpty()) {
            this.variables = Optional.fromNullable(builder.variables.build());
        } else {
            variables = Optional.absent();
        }
    }

    /**
     * EmailChannel Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the date-time when an address gave permission to receive commercial emails.
     * @return Optional DateTime
     */
    public Optional<DateTime> getCommercialOptedIn() {
        return commercialOptedIn;
    }

    /**
     * Get the date-time when an address gave permission to receive transactional emails.
     * @return Optional DateTime
     */
    public Optional<DateTime> getTransactionalOptedIn() {
        return transactionalOptedIn;
    }

    /**
     * Get the address that you want to populate in the channel's address field.
     * @return String
     */
    public String getUaAddress() {
        return uaAddress;
    }

    /**
     * Get substitution key-value pairs representing variables your notification template.
     * @return Optional Map of Strings
     */
    public Optional<Map<String, String>> getSubstitutions() {
        return substitutions;
    }

    /**
     * Get the variables.
     * @return An Optional immutable map of variables in the push payload.
     */
    public Optional<Map<String, Object>> getPersonalizationVariables() {
        return variables;
    }
    public static final class Builder {
        private String uaAddress;
        private DateTime commercialOptedIn;
        private DateTime transactionalOptedIn;
        private ImmutableMap.Builder<String, String> substitutions = ImmutableMap.builder();
        private ImmutableMap.Builder<String, Object> variables = ImmutableMap.builder();


        /**
         * The address that you want to populate in the channel's address field.
         * @param address String
         * @return EmailChannel Builder
         */
        public Builder setAddress(String address) {
            this.uaAddress = address;
            return this;
        }

        /**
         * Set the date-time when an address gave permission to receive commercial emails.
         * @param commercialOptedInDate DateTime
         * @return EmailChannel Builder
         */
        public Builder setCommertialOptedIn(DateTime commercialOptedInDate) {
            this.commercialOptedIn = commercialOptedInDate;
            return this;
        }

        /**
         * Set the date-time when an address gave permission to receive transactional emails. Transactional emails do not require this value,
         * but you can set this value if a user previously unsubscribed from transactional emails
         * @param transactionalOptedInDate DateTime
         * @return EmailChannel Builder
         */
        public Builder setTransactionalOptedIn(DateTime transactionalOptedInDate) {
            this.transactionalOptedIn = transactionalOptedInDate;
            return this;
        }

        /**
         * Add a substitution. You can provide additional key-value pairs representing variables your notification template.
         * Your variable keys must not be prefixed with ua_
         * @param key String
         * @param value String
         * @return EmailChannel Builder
         */
        public Builder addSubstitution(String key, String value) {
            substitutions.put(key, value);
            return this;
        }

        /**
         * Add all substitution values. You can provide additional key-value pairs representing variables your notification template.
         * Your variable keys must not be prefixed with ua_
         * @param substitutions Map of Strings.
         * @return EmailChannel Builder
         */
        public Builder addAllSubstitutions(Map<String, String> substitutions) {
            this.substitutions.putAll(substitutions);
            return this;
        }

        /**
         * Add a variable.
         * @param key String
         * @param object Object
         * @return Builder
         */
        public Builder addPersonalizationVariable(String key, Object object) {
            this.variables.put(key, object);
            return this;
        }

        public EmailChannel build() {
            Preconditions.checkNotNull(uaAddress, "uaAddress cannot be null.");
            return new EmailChannel(this);
        }
    }
}
