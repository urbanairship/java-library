/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;

/**
 * Represents a single template object.
 */
public class TemplateView {

    private final String id;
    private final DateTime createdAt;
    private final DateTime modifiedAt;
    private final DateTime lastUsed;
    private final String name;
    private final Optional<String> description;
    private final ImmutableList<TemplateVariable> variables;
    private final Optional<PartialPushPayload> partialPushPayload;

    private TemplateView(Builder builder) {
        this.id = builder.id;
        this.createdAt = builder.createdAt;
        this.modifiedAt = builder.modifiedAt;
        this.lastUsed = builder.lastUsed;
        this.name = builder.name;
        this.description = Optional.ofNullable(builder.description);
        this.variables = builder.variables.build();
        this.partialPushPayload = Optional.ofNullable(builder.partialPushPayload);
    }

    /**
     * Return a new TemplateView builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the template ID.
     *
     * @return A string ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the created date.
     *
     * @return A DateTime object
     */
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Get the modified object.
     *
     * @return A DateTime object
     */
    public DateTime getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Get the date the template was last used.
     *
     * @return A DateTime object
     */
    public DateTime getLastUsed() {
        return lastUsed;
    }

    /**
     * Get the template name.
     *
     * @return A string
     */
    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get the list of variables associated to this template.
     *
     * @return An immutable list of TemplateVariable objects
     */
    public ImmutableList<TemplateVariable> getVariables() {
        return variables;
    }

    /**
     * Get the partial push payload associated with this template.
     *
     * @return A PartialPushPayload object
     */
    public Optional<PartialPushPayload> getPartialPushPayload() {
        return partialPushPayload;
    }

    @Override
    public String toString() {
        return "TemplateView{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", lastUsed=" + lastUsed +
                ", name=" + name +
                ", description=" + description +
                ", variables=" + variables +
                ", partialPushPayload=" + partialPushPayload +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateView that = (TemplateView) o;

        if (!createdAt.equals(that.createdAt)) return false;
        if (!description.equals(that.description)) return false;
        if (!id.equals(that.id)) return false;
        if (!lastUsed.equals(that.lastUsed)) return false;
        if (!modifiedAt.equals(that.modifiedAt)) return false;
        if (!name.equals(that.name)) return false;
        if (!partialPushPayload.equals(that.partialPushPayload)) return false;
        if (!variables.equals(that.variables)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + modifiedAt.hashCode();
        result = 31 * result + lastUsed.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + variables.hashCode();
        result = 31 * result + partialPushPayload.hashCode();
        return result;
    }

    public final static class Builder {

        private String id = null;
        private DateTime createdAt = null;
        private DateTime modifiedAt = null;
        private DateTime lastUsed = null;
        private String name = null;
        private String description = null;
        private ImmutableList.Builder<TemplateVariable> variables = ImmutableList.builder();
        private PartialPushPayload partialPushPayload = null;

        /**
         * Set the template ID.
         *
         * @param id A string
         * @return Builder
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Set the created date.
         *
         * @param createdAt A DateTime object
         * @return Builder
         */
        public Builder setCreatedAt(DateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Set the modified date.
         *
         * @param modifiedAt A DateTime object
         * @return Builder
         */
        public Builder setModifiedAt(DateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        /**
         * Set the date the template was last used.
         *
         * @param lastUsed A DateTime object
         * @return Builder
         */
        public Builder setLastUsed(DateTime lastUsed) {
            this.lastUsed = lastUsed;
            return this;
        }

        /**
         * Set the template name.
         *
         * @param name A string
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the template description.
         *
         * @param description A string
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Add a variable object to the the template.
         *
         * @param variable A TemplateVariable object
         * @return Builder
         */
        public Builder addVariable(TemplateVariable variable) {
            this.variables.add(variable);
            return this;
        }

        /**
         * Add all variables to the template.
         *
         * @param variables A list of TemplateVariable objects
         * @return Builder
         */
        public Builder addAllVariables(List<TemplateVariable> variables) {
            this.variables.addAll(variables);
            return this;
        }

        /**
         * Set the push payload of the template.
         *
         * @param partialPushPayload A PartialPushPayload object
         * @return Builder
         */
        public Builder setPushPayload(PartialPushPayload partialPushPayload) {
            this.partialPushPayload = partialPushPayload;
            return this;
        }

        /**
         * Build a TemplateView object.
         *
         * <pre>
         *     1. If no ID is present, variables and name must be present (in the context of template serialization)
         * </pre>
         *
         * @return A TemplateView object.
         */
        public TemplateView build() {
            return new TemplateView(this);
        }
    }
}
