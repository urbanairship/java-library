/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * The TemplateSelector object describes the template ID and the variable substitutions
 * to use with it. Used when creating a push to template.
 */
public class TemplateSelector {
    private final String templateId;
    private final Optional<Map<String,String>> substitutions;

    private TemplateSelector(Builder builder) {
        this.templateId = builder.templateId;
        if (builder.substitutions.isEmpty()) {
            this.substitutions = Optional.absent();
        } else {
            this.substitutions = Optional.of(builder.substitutions);
        }
    }

    /**
     * Returns a new TemplateSelector Builder.
     *
     * @return A TemplateSelector Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return the template ID associated with this selector.
     *
     * @return A string representing a template ID
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Return the mapping of variables to their values.
     *
     * @return An optional ImmutableMap of string variables to string values
     */
    public Optional<Map<String, String>> getSubstitutions() {
        return substitutions;
    }

    @Override
    public String toString() {
        return "TemplateSelector{" +
                "templateId='" + templateId + '\'' +
                ", substitutions=" + substitutions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateSelector that = (TemplateSelector) o;

        if (!substitutions.equals(that.substitutions)) return false;
        if (!templateId.equals(that.templateId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templateId.hashCode();
        result = 31 * result + substitutions.hashCode();
        return result;
    }

    public static class Builder {
        private String templateId = null;
        private Map<String, String> substitutions = new HashMap<String, String>();

        /**
         * Set the template ID.
         *
         * @param templateId String
         * @return Builder
         */
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Add a single variable substitution to the map of substitutions.
         *
         * @param var A variable string
         * @param value A value string
         * @return Builder
         */
        public Builder addSubstitution(String var, String value) {
            this.substitutions.put(var, value);
            return this;
        }

        /**
         * Add all values to the map of substitutions.
         *
         * @param entries A map of variables to values
         * @return Builder
         */
        public Builder addSubstitutions(Map<String, String> entries) {
            this.substitutions.putAll(entries);
            return this;
        }

        /**
         * Builds the TemplateSelector object.
         * <pre>
         *     1. templateId cannot be null.
         * </pre>
         *
         * @return A TemplateSelector object.
         */
        public TemplateSelector build() {
            Preconditions.checkNotNull(templateId, "You must specify a templateId when building a TemplateSelector");
            return new TemplateSelector(this);
        }
    }
}
