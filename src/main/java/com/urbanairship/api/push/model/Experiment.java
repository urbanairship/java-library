/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

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
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    public static class Builder {

    }

    public static class Variant {

    }
}
