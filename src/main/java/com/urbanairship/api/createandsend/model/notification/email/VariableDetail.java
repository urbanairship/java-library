package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Optional;

/**
 * Represents the variable detail in the create and send notification.
 */
public class VariableDetail {
    private final Optional<String> key;
    private final Optional<String> defaultValue;

    private VariableDetail(Builder builder) {
        key = Optional.fromNullable(builder.key);
        defaultValue = Optional.fromNullable(builder.defaultValue);
    }

    /**
     * VariableDetail Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the variable you want to add to the template.
     * @return Optional String
     */
    public Optional<String> getKey() {
        return key;
    }

    /**
     * Get the default value for the variable.
     * @return Optional String
     */
    public Optional<String> getDefaultValue() {
        return defaultValue;
    }

    public static class Builder {
        private String key;
        private String defaultValue;

        /**
         * Set the variable you want to add to the template.
         * @param key String
         * @return VariableDetail Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the default value for the variable.
         * @param defaultValue String
         * @return VariableDetail Builder
         */
        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public VariableDetail build() {
            return new VariableDetail(this);
        }
    }
}
