/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.google.common.collect.Lists;
import com.urbanairship.api.push.model.audience.Selector;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a set of varied pushes to send as part of an A/B experiment.
 */
public final class Experiment extends ExperimentModelObject {

    private final Optional<String> name;
    private final Optional<String> description;
    private final Optional<BigDecimal> control;
    private final Selector audience;
    private final DeviceTypeData deviceTypes;
    private final List<Variant> variants;

    private Experiment(Builder builder) {
        this.name = Optional.fromNullable(builder.name);
        this.description = Optional.fromNullable(builder.description);
        this.control = Optional.fromNullable(builder.control);
        this.audience = builder.audience;
        this.deviceTypes = builder.deviceTypes;
        this.variants = builder.variants;
    }

    /**
     * Experiment builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the name of the experiment.
     * @return an Optional String
     */
    public Optional<String> getName() {
        return name;
    }

    /**
     * Get the description for the experiment.
     * @return an Optional String
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get the control group for the experiment. A control is a float between 0 and 1, e.g., 0.4,
     * representing the proportion of the audience that will not receive a push.
     * The remaining audience is split between the variants.
     * @return an Optional BigDecimal
     */
    public Optional<BigDecimal> getControl() {
        return control;
    }

    /**
     * Get the audience for the experiment.
     * @return an Audience object
     */
    public Selector getAudience() {
        return audience;
    }

    /**
     * Boolean indicating whether audience is SelectorType.ALL
     * @return Boolean
     */
    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    /**
     * Get the device types for the experiment.
     * @return a DeviceTypeData object
     */
    public DeviceTypeData getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Get the variants for the experiment. A variant defines a push that will be sent to a subset of the experiment's
     * audience.
     * @return variants List<Variant>
     */
    public List<Variant> getVariants() {
        return variants;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description, control, audience, deviceTypes, variants);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Experiment other = (Experiment) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.control, other.control)
                && Objects.equal(this.audience, other.audience)
                && Objects.equal(this.deviceTypes, other.deviceTypes)
                && Objects.equal(this.variants, other.variants);
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "name=" + name +
                ", description=" + description +
                ", control=" + control +
                ", audience=" + audience +
                ", deviceTypes=" + deviceTypes +
                ", variants=" + variants +
                '}';
    }

    /**
     * Experiment Builder
     */
    public static class Builder {

        private String name = null;
        private String description = null;
        private BigDecimal control = null;
        private Selector audience = null;
        private DeviceTypeData deviceTypes = null;
        private List<Variant> variants = Lists.newArrayList();

        private Builder() { }

        /**
         * Set the experiment name.
         * @param name String
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the experiment description.
         * @param description String
         * @return Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Set the control group for the experiment. A control is a float between 0 and 1, e.g., 0.4,
         * representing the proportion of the audience that will not receive a push.
         * The remaining audience is split between the variants.
         * @param control BigDecimal
         * @return Builder
         */
        public Builder setControl(BigDecimal control) {
            this.control = control;
            return this;
        }

        /**
         * Set the audience for the experiment.
         * @param audience Selector
         * @return Builder
         */
        public Builder setAudience(Selector audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Set the device types for the experiment.
         * @param deviceTypes DeviceTypeData
         * @return Builder
         */
        public Builder setDeviceTypes(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Add a variant to the experiment. A variant defines a push that will be sent to a subset of the experiment's
         * audience.
         * @param variant List<Variant>
         * @return Builder
         */
        public Builder addVariant(Variant variant) {
            variants.add(variant);
            return this;
        }

        public Builder addAllVariants(List<Variant> variants) {
            this.variants.addAll(variants);
            return this;
        }

        /**
         * Build an Experiment object. Will fail if any of the following
         * preconditions are not met.
         * <pre>
         * 1. Audience must be set.
         * 2. DeviceTypes (device types) must be set.
         * 3. At least one variant must be present.
         * </pre>
         *
         * @throws IllegalArgumentException
         * @throws NullPointerException
         * @return Experiment
         */
        public Experiment build() {
            Preconditions.checkNotNull(audience, "'audience' must be set");
            Preconditions.checkNotNull(deviceTypes, "'device_types' must be set");
            Preconditions.checkNotNull(variants, "An experiment requires at least one variant.");
            Preconditions.checkArgument(variants.size() > 0, "At least one variant must be present.");

            return new Experiment(this);
        }
    }
}
