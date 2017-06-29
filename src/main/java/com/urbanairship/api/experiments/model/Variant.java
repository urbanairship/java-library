/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * Defines a push that will be sent to a subset of an experiment's audience.
 */
public class Variant {

    private Optional<String> name;
    private Optional<String> description;
    private final VariantPushPayload variantPushPayload;
    private Optional<BigDecimal> weight;

    private Variant(Builder builder) {
        this.name = Optional.fromNullable(builder.name);
        this.description = Optional.fromNullable(builder.description);
        this.variantPushPayload = builder.variantPushPayload;
        this.weight = Optional.fromNullable(builder.weight);
    }

    /**
     * Experiment builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the name of the variant.
     * @return name
     */
    public Optional<String> getName() {
        return name;
    }

    /**
     * Get the description of the experiment.
     * @return description
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get the partial push notification associated with the variant. A partial push notification object
     * represents a Push payload, excepting the audience and device_types fields because they are defined in the
     * experiment object. Message Center messages are also not included in the partial push payload object.
     * @return VariantPushPayload
     */
    public VariantPushPayload getVariantPushPayload() {
        return variantPushPayload;
    }

    /**
     * Get the weight of the variant. Defaults to 1. A whole number, representing the proportion of the audience
     * that will receive the variant.
     * @return weight
     */
    public  Optional<BigDecimal> getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description, variantPushPayload, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Variant other = (Variant) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.variantPushPayload, other.variantPushPayload)
                && Objects.equal(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Variant{" +
                "name=" + name +
                ", description=" + description +
                ", variantPushPayload=" + variantPushPayload +
                ", weight=" + weight +
                '}';
    }

    /**
     * Variant Builder
     */
    public static class Builder {

        private String name;
        private String description;
        private VariantPushPayload variantPushPayload;
        private BigDecimal weight;

        /**
         * Set the variant name.
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the variant description.
         * @param description String
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set the partial push notification associated with the variant. A partial push notification object
         * represents a Push payload, excepting the audience and device_types fields because they are defined in the
         * experiment object. Message Center messages are also not included in the partial push payload object.
         * @param variantPushPayload VariantPushPayload
         * @return Builder
         */
        public Builder setPushPayload(VariantPushPayload variantPushPayload) {
            this.variantPushPayload = variantPushPayload;
            return this;
        }

        /**
         * Set the weight of the variant. Defaults to 1. A whole number, representing the proportion of the audience
         * that will receive the variant.
         * @param weight BigDecimal
         * @return Builder
         */
        public Builder setWeight(BigDecimal weight) {
            this.weight = weight;
            return this;
        }

        /**
         * Build a Variant object. Will fail if the following
         * precondition is not met.
         * <pre>
         * 1. Partial push notification object must be specified.
         * </pre>
         *
         * @throws NullPointerException
         * @return Variant
         */
        public Variant build() {
            Preconditions.checkNotNull(variantPushPayload,
                    "A partial push notification object must be provided.");

            return new Variant(this);
        }
    }
}
