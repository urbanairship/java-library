/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

/**
 * Represents a response from the Urban Airship API for Experiments.
 */
public final class ExperimentResponse {

    private final boolean ok;
    private final Optional<String> operationId;
    private final Optional<String> experimentId;
    private final Optional<String> pushId;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private ExperimentResponse(boolean ok,
                              String operationId,
                              String experimentId,
                              String pushId,
                              String error,
                              ErrorDetails errorDetails) {

        this.ok = ok;
        this.operationId = Optional.ofNullable(operationId);
        this.experimentId = Optional.ofNullable(experimentId);
        this.pushId = Optional.ofNullable(pushId);
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
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

     /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }    

    @Override
    public String toString() {
        return "ExperimentResponse{" +
                "ok=" + ok +
                ", operationId=" + operationId +
                ", experimentId=" + experimentId +
                ", pushId=" + pushId +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushId, ok, experimentId, error, errorDetails);
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
                && Objects.equal(this.experimentId, other.experimentId)
                && Objects.equal(this.error, other.error) 
                && Objects.equal(this.errorDetails, other.errorDetails);
    }

    /**
     * ExperimentResponse Builder
     */
    public static class Builder {

        private String operationId = null;
        private String pushId = null;
        private boolean ok = false;
        private String experimentId = null;
        private String error = null;
        private ErrorDetails errorDetails = null;

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
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        /**
         * Build the ExperimentResponse object.
         *
         * @return ExperimentResponse
         */
        public ExperimentResponse build() {
            return new ExperimentResponse(ok, operationId, experimentId, pushId, error, errorDetails);
        }
    }
}
