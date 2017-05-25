/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import com.urbanairship.api.push.model.notification.Notification;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a set of varied pushes to send as part of an A/B experiment.
 */
public final class Experiment {

    private final String experimentPayload;
    private final List<Variant> variants;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Experiment(String experimentPayload, ImmutableList<Variant> variants) {
        this.experimentPayload = experimentPayload;
        this.variants = variants;
    }

    public String getExperimentPayload() {
        return experimentPayload;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(experimentPayload, variants);
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

        if (experimentPayload != null ? !experimentPayload.equals(other.experimentPayload) : other.experimentPayload != null) {
            return false;
        }
        if (variants != null ? !variants.equals(other.variants) : other.variants != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "experimentPayload='" + experimentPayload + '\'' +
                ", variants=" + variants +
                '}';

    }

    public static class Builder {

        private String experimentPayload;
        private List<Variant> variants;

        public Builder setExperimentPayload(String experimentPayload) {
            this.experimentPayload = experimentPayload;
            return this;
        }

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

        public Experiment build() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(experimentPayload),
                    "An experiment payload must be provided.");
            Preconditions.checkNotNull(variants, "An experiment requires at least one variant.");
            Preconditions.checkArgument(variants.size() > 0, "At least one variant must be present.");

            return new Experiment(experimentPayload, ImmutableList.copyOf(variants));
        }
    }

    public static class Variant {

        private int id;
        private Optional<BigDecimal> weight;
        private final Notification notification;

        private Variant(int id,  Optional<BigDecimal> weight, Notification notification) {
            this.id = id;
            this.weight = weight;
            this.notification = notification;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * ID of the variant. Should be unique within the experiment.
         *
         * @return
         */
        public int getId() {
            return id;
        }

        public  Optional<BigDecimal> getWeight() {
            return weight;
        }

        public Notification getNotification() {
            return notification;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, weight, notification);
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
            return Objects.equal(this.id, other.id)
                    && Objects.equal(this.weight, other.weight)
                    && Objects.equal(this.notification, other.notification);
        }

        @Override
        public String toString() {
            return "Variant{" +
                    "id=" + id +
                    ", notification=" + notification +
                    ", weight=" + weight.get() +
                    '}';
        }

        public static class Builder {

            private Integer id;
            private Optional<BigDecimal> weight;
            private Notification notification;

            public Builder setId(int id) {
                this.id = id;
                return this;
            }

            public Builder setNotification(Notification notification) {
                this.notification = notification;
                return this;
            }

            public Variant build() {
                Preconditions.checkNotNull(id, "An ID must be provided.");
                Preconditions.checkNotNull(notification, "A notification must be provided.");
                Preconditions.checkNotNull(weight.get(), "A weight must be provided.");


                return new Variant(id, weight, notification);
            }
        }

    }
}
