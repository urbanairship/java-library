/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * Represents a response from the Urban Airship API for Experiments.
 */
public final class ExperimentResponse {

    private final boolean ok;
    private final Optional<String> operationId;
    private final Optional<String> experimentId;
    private final Optional<String> pushId;

    private ExperimentResponse(boolean ok,
                              String operationId,
                              String experimentId,
                              String pushId) {

        this.ok = ok;
        this.operationId = Optional.fromNullable(operationId);
        this.experimentId = Optional.fromNullable(experimentId);
        this.pushId = Optional.fromNullable(pushId);
    }

    /**
     * Return a new ExperimentResponse builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     *
     * @return Optional operation id for this API request
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * Get the experiment ID.
     *
     * @return An optional experiment id for this API request.
     */
    public Optional<String> getExperimentId() {
        return experimentId;
    }

    /**
     * The push id for the push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return Optional push id for this API request.
     */
    public Optional<String> getPushId() {
        return pushId;
    }

    @Override
    public String toString() {
        return "ExperimentResponse{" +
                "ok=" + ok +
                ", operationId=" + operationId +
                ", experimentId=" + experimentId +
                ", pushId=" + pushId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushId, ok, experimentId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ExperimentResponse other = (ExperimentResponse) obj;
        return Objects.equal(this.operationId, other.operationId)
                && Objects.equal(this.pushId, other.pushId)
                && Objects.equal(this.ok, other.ok)
                && Objects.equal(this.experimentId, other.experimentId);
    }

    /**
     * ExperimentResponse Builder
     */
    public static class Builder {

        private String operationId = null;
        private String pushId = null;
        private boolean ok = false;
        private String experimentId = null;

        private Builder() {
        }

        /**
         * Set the ok status.
         *
         * @param ok A boolean
         * @return Builder
         */
        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        /**
         * Set the operation ID.
         *
         * @param operationId A string
         * @return Builder
         */
        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        /**
         * Set the experiment ID.
         *
         * @param experimentId A string
         * @return Builder
         */
        public Builder setExperimentId(String experimentId) {
            this.experimentId = experimentId;
            return this;
        }

        /**
         * Set the push ID.
         *
         * @param pushId A string
         * @return Builder
         */
        public Builder setPushId(String pushId) {
            this.pushId = pushId;
            return this;
        }

        /**
         * Build the ExperimentResponse object.
         *
         * @return ExperimentResponse
         */
        public ExperimentResponse build() {
            return new ExperimentResponse(ok, operationId, experimentId, pushId);
        }
    }
}
