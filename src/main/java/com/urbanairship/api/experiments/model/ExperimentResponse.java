/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class ExperimentResponse {

    private final Optional<String> operationId;
    private final Optional<ImmutableList<String>> pushIds;
    private final boolean ok;
    private final Optional<String> experimentId;

    public ExperimentResponse(String operationId,
                              ImmutableList<String> pushIds,
                              boolean ok,
                              String experimentId) {
        this.operationId = Optional.fromNullable(operationId);
        this.pushIds = Optional.fromNullable(pushIds);
        this.ok = ok;
        this.experimentId = Optional.fromNullable(experimentId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     *
     * @return Operation id for this API request
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * The push id for the push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return Push id for this API request.
     */
    public Optional<ImmutableList<String>> getPushId() {
        return pushIds;
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */
    public boolean getOk() {
        return ok;
    }

    public Optional<String> getExperimentId() {
        return experimentId;
    }

    @Override
    public String toString() {
        return "ExperimentResponse{" +
                "operationId=" + operationId +
                ", pushId=" + pushIds +
                ", ok=" + ok +
                ", experimentId=" + experimentId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushIds, ok, experimentId);
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
                && Objects.equal(this.pushIds, other.pushIds)
                && Objects.equal(this.ok, other.ok)
                && Objects.equal(this.experimentId, other.experimentId);
    }


    /**
     * ExperimentResponse Builder
     */
    public static class Builder {

        private String operationId = null;
        private ImmutableList.Builder<String> pushIds = ImmutableList.builder();
        private boolean ok = false;
        private String experimentId = null;

        private Builder() {
        }

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder addPushId(String pushId) {
            this.pushIds.add(pushId);
            return this;
        }

        public Builder addAllPushIds(Iterable<? extends String> pushIds) {
            this.pushIds.addAll(pushIds);
            return this;
        }

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder setExperimentId(String experimentId) {
            this.experimentId = experimentId;
            return this;
        }

        /**
         * Build the ExperimentResponse object.
         *
         * @return ExperimentResponse
         */
        public ExperimentResponse build() {
            return new ExperimentResponse(operationId, pushIds.build(), ok, experimentId);

        }

    }
}
