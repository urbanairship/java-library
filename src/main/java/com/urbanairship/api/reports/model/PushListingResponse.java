/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

/**
 * Push listing response object
 */
public class PushListingResponse {

    private final Optional<String> nextPage;
    private final Optional<ImmutableList<PushInfoResponse>> pushInfoObjects;
    private final Optional<Boolean> ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private PushListingResponse() {
        this(null, null, null, null ,null);
    }

    private PushListingResponse(
            String nextPage,
            Optional<ImmutableList<PushInfoResponse>> pushInfoObjects,
            Boolean ok,
            String error,
            ErrorDetails errorDetails) {
        this.nextPage = Optional.fromNullable(nextPage);
        this.pushInfoObjects = pushInfoObjects;
        this.ok = Optional.fromNullable(ok);
        this.error = Optional.fromNullable(error);
        this.errorDetails = Optional.fromNullable(errorDetails);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the next page if present for a push listing request
     *
     * @return An optional String
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get the list of push info objects if present for a push listing
     * request
     *
     * @return An Optional ImmutableList of SinglePushInfoResponse objects
     */
    public Optional<ImmutableList<PushInfoResponse>> getPushInfoList() {
        return pushInfoObjects;
    }

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public Optional<Boolean> getOk() {
        return ok;
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
        return "PushInfoResponse{" +
                "nextPage='" + nextPage + '\'' +
                ", pushInfoObjects=" + pushInfoObjects +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nextPage, pushInfoObjects, ok, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PushListingResponse other = (PushListingResponse) obj;
        return Objects.equal(this.nextPage, other.nextPage) &&
                Objects.equal(this.pushInfoObjects, other.pushInfoObjects) &&
                Objects.equal(ok, other.ok) &&
                Objects.equal(error, other.error) &&
                Objects.equal(errorDetails, other.errorDetails);
    }

    public static class Builder {
        private String nextPage = null;
        private ImmutableList.Builder<PushInfoResponse> pushInfoObjects = ImmutableList.builder();
        private boolean ok = true;
        private String error;
        private ErrorDetails errorDetails;

        private Builder() {
        }

        /**
         * Set the next page
         *
         * @param value String
         * @return Builder
         */
        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        /**
         * Add a push info object for push listing
         *
         * @param value SinglePushInfoResponse
         * @return Builder
         */
        public Builder addPushInfoObject(PushInfoResponse value) {
            this.pushInfoObjects.add(value);
            return this;
        }

        /**
         * Add all push info objects for push listing
         *
         * @param value Iterable of SinglePushInfoResponse objects
         * @return Builder
         */
        public Builder addPushInfoObjects(Iterable<? extends PushInfoResponse> value) {
            this.pushInfoObjects.addAll(value);
            return this;
        }

        /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
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
         * Build the PushListingResponse object
         *
         * @return PushListingResponse
         */
        public PushListingResponse build() {
            return new PushListingResponse(nextPage, Optional.fromNullable(pushInfoObjects.build()), ok, error, errorDetails);
        }
    }
}
