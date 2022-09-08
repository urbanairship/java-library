/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.base.Preconditions;

import java.util.Objects;
import java.util.Optional;

/**
 * The TemplateVariable object is used to specify which pieces of a template to override when
 * creating template-based pushes.
 */
public class TemplateVariable {
    private final String key;
    private final Optional<String> name;
    private final Optional<String> description;
    private final String defaultValue;

    private TemplateVariable(Builder builder) {
        this.key = builder.key;
        this.name = Optional.ofNullable(builder.name);
        this.description = Optional.ofNullable(builder.description);
        this.defaultValue = builder.defaultValue;
    }

    /**
     * Generate a new TemplateVariable builder object.
     *
     * @return A TemplateVariable Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return the TemplateVariable key.
     *
     * @return A string representing the variable key
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the TemplateVariable name.
     *
     * @return An optional string representing the variable name
     */
    public Optional<String> getName() {
        return name;
    }

    /**
     * Return the TemplateVariable description.
     *
     * @return An optional string describing the variable
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Return the default value of the TemplateVariable.
     *
     * @return A string representing the variable default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return "TemplateVariable{" +
                "key='" + key +
                ", name=" + name +
                ", description=" + description +
                ", defaultValue=" + defaultValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateVariable that = (TemplateVariable) o;

        if (!Objects.equals(defaultValue, that.defaultValue)) return false;
        if (!description.equals(that.description)) return false;
        if (!key.equals(that.key)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + defaultValue.hashCode();
        return result;
    }

    public static class Builder {
        private String key = null;
        private String name = null;
        private String description = null;
        private String defaultValue = null;

        /**
         * Set the variable key.
         *
         * @param key String
         * @return Builder
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the variable name.
         *
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the variable description.
         *
         * @param description String
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set the default value of the variable.
         *
         * @param defaultValue String
         * @return Builder
         */
        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * Build the TemplateVariable object.
         *
         * <pre>
         *     1. Key cannot be null.
         * </pre>
         *
         * @return TemplateVariable
         */
        public TemplateVariable build() {
            Preconditions.checkNotNull(key, "The TemplateVariable must specify a key.");
            return new TemplateVariable(this);
        }
    }

}
