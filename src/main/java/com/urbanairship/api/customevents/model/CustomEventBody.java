package com.urbanairship.api.customevents.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Map;

public class CustomEventBody {
    private final String name;
    private final Optional<BigDecimal> value;
    private final Optional<String> transaction;
    private final Optional<String> interactionId;
    private final Optional<String> interactionType;
    private final Optional<ImmutableMap<String, String>> properties;
    private final String sessionId;

    private CustomEventBody(Builder builder) {
        this.name = builder.name;
        this.value = Optional.fromNullable(builder.value);
        this.transaction = Optional.fromNullable(builder.transaction);
        this.interactionId = Optional.fromNullable(builder.interactionId);
        this.interactionType = Optional.fromNullable(builder.interactionType);

        if (builder.properties.build().isEmpty()) {
            this.properties = Optional.absent();
        } else {
            this.properties = Optional.of(builder.properties.build());
        }

        this.sessionId = builder.sessionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Optional<BigDecimal> getValue() {
        return value;
    }

    public Optional<String> getTransaction() {
        return transaction;
    }

    public Optional<String> getInteractionId() {
        return interactionId;
    }

    public Optional<String> getInteractionType() {
        return interactionType;
    }

    public Optional<ImmutableMap<String, String>> getProperties() {
        return properties;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static class Builder {
        private String name = null;
        private BigDecimal value = null;
        private String transaction = null;
        private String interactionId = null;
        private String interactionType = null;
        private ImmutableMap.Builder<String, String> properties = ImmutableMap.builder();
        private String sessionId = null;

        private Builder() {

        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder setTransaction(String transaction) {
            this.transaction = transaction;
            return this;
        }

        public Builder setInteractionId(String interactionId) {
            this.interactionId = interactionId;
            return this;
        }

        public Builder setInteractionType(String interactionType) {
            this.interactionType = interactionType;
            return this;
        }

        public Builder addPropertiesEntry(String key, String value) {
            this.properties.put(key, value);
            return this;
        }

        public Builder addAllPropertyEntries(Map<String, String> entries) {
            this.properties.putAll(entries);
            return this;
        }

        public Builder setSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public CustomEventBody build() {
            Preconditions.checkNotNull(name, "'name' must be set");
            Preconditions.checkNotNull(sessionId, "'sessionId' must be set");

            return new CustomEventBody(this);
        }
    }
}
