package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Optional;

public class VariableDetail {
    private final Optional<String> key;
    private final Optional<String> defaultValue;

    private VariableDetail(Builder builder) {
        key = Optional.fromNullable(builder.key);
        defaultValue = Optional.fromNullable(builder.defaultValue);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getKey() {
        return key;
    }

    public Optional<String> getDefaultValue() {
        return defaultValue;
    }

    public static class Builder {
        private String key;
        private String defaultValue;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public VariableDetail build() {
            return new VariableDetail(this);
        }
    }
}
