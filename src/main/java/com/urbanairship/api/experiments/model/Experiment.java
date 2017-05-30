/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.notification.Notification;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.urbanairship.api.push.model.audience.Selector;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a set of varied pushes to send as part of an A/B experiment.
 */
public final class Experiment {

    private final Optional<String> name;
    private final Optional<String> description;
    private final Optional<BigDecimal> control;
    private final Selector audience;
    private final DeviceTypeData deviceTypes;
    private final List<Variant> variants;

    /**
     * Experiment builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private Experiment(Optional<String> name,
                       Optional<String> description,
                       Optional<BigDecimal> control,
                       Selector audience,
                       DeviceTypeData deviceTypes,
                       ImmutableList<Variant> variants) {
        this.name = name;
        this.description = description;
        this.control = control;
        this.audience = audience;
        this.deviceTypes = deviceTypes;
        this.variants = variants;
    }

    /**
     * Get the name of the experiment. This is optional.
     * @return name
     */
    public Optional<String> getName() {
        return name;
    }

    /**
     * Get the description for the experiment. This is optional.
     * @return description
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get the control group. This is optional.
     * @return control
     */
    public Optional<BigDecimal> getControl() {
        return control;
    }

    /**
     * Get the audience
     * @return audience
     */
    public Selector getAudience() {
        return audience;
    }

    /**
     * Boolean indicating whether audience is SelectorType.ALL
     * @return audience is all
     */
    public boolean isBroadcast() {
        return audience.getType().equals(SelectorType.ALL);
    }

    /**
     * Get the deviceTypes
     * @return DeviceTypeData
     */
    public DeviceTypeData getDeviceType() {
        return deviceTypes;
    }

    /**
     * Get the variants.
     * @return variants
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

    public static class Builder {

        private String name = null;
        private String description = null;
        private BigDecimal control = null;
        private Selector audience = null;
        private DeviceTypeData deviceTypes = null;
        private List<Variant> variants = null;

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
         * Set the control group.
         * @param control BigDecimal
         * @return Builder
         */
        public Builder setControl(BigDecimal control) {
            this.control = control;
            return this;
        }

        /**
         * Set the Audience.
         * @param audience Selector
         * @return Builder
         */
        public Builder setAudience(Selector audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Set the Device Type data.
         * @param deviceTypes DeviceTypeData
         * @return Builder
         */
        public Builder setDeviceType(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Set the variants.
         * @param variant List<Variant>
         * @return Builder
         */
        public <V extends Variant> Builder addVariant(V variant) {
            if (variants == null) {
                variants = Lists.newArrayList();
            }
            variants.add(variant);
            return this;
        }

        public Builder addAllVariants(List<? extends Variant> variants) {
            this.variants = Lists.newArrayList(variants);
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

            return new Experiment(
                    Optional.fromNullable(name),
                    Optional.fromNullable(description),
                    Optional.fromNullable(control),
                    audience,
                    deviceTypes,
                    ImmutableList.copyOf(variants));
        }
    }

    public static class Variant {

        private Optional<String> name;
        private Optional<String> description;
        private final Notification notification;
        private Optional<BigDecimal> weight;

        private Variant(Optional<String> name,
                        Optional<String> description,
                        Notification notification,
                        Optional<BigDecimal> weight) {
            this.name = name;
            this.description = description;
            this.notification = notification;
            this.weight = weight;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * Get the name of the variant. This is optional.
         * @return name
         */
        public Optional<String> getName() {
            return name;
        }

        /**
         * Get the description of the experiment. This is optional.
         * @return description
         */
        public Optional<String> getDescription() {
            return description;
        }

        /**
         * Get the partial push notification object.
         * @return Notification
         */
        public Notification getNotification() {
            return notification;
        }

        /**
         * Get the weight of the variant. This is optional.
         * @return weight
         */
        public  Optional<BigDecimal> getWeight() {
            return weight;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, description, notification, weight);
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
                    && Objects.equal(this.notification, other.notification)
                    && Objects.equal(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return "Variant{" +
                    "name=" + name +
                    ", description=" + description +
                    ", notification=" + notification +
                    ", weight=" + weight.get() +
                    '}';
        }

        public static class Builder {

            private String name;
            private String description;
            private Notification notification;
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
             * Set the partial push notification object.
             * @param notification Notification
             * @return Builder
             */
            public Builder setNotification(Notification notification) {
                this.notification = notification;
                return this;
            }

            /**
             * Set the weight of the variant.
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
                Preconditions.checkNotNull(notification,
                        "A partial push notification object must be provided.");

                return new Variant(
                        Optional.fromNullable(name),
                        Optional.fromNullable(description),
                        notification,
                        Optional.fromNullable(weight));
            }
        }

    }
}
