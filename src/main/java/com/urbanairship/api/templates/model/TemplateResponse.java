/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.List;

/**
 * Response returned by the template creation, update, delete, and push endpoints.
 */
public class TemplateResponse {

    private final boolean ok;
    private final Optional<String> operationId;
    private final Optional<String> templateId;
    private final Optional<ImmutableList<String>> pushIds;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private TemplateResponse(Builder builder) {
        this.ok = builder.ok;
        this.operationId = Optional.fromNullable(builder.operationId);
        this.templateId = Optional.fromNullable(builder.templateId);
        this.error = Optional.fromNullable(builder.error);
        this.errorDetails = Optional.fromNullable(builder.errorDetails);
        if (builder.pushIds.build().isEmpty()) {
            this.pushIds = Optional.absent();
        } else {
            this.pushIds = Optional.fromNullable(builder.pushIds.build());
        }
    }

    /**
     * Return a new TemplateResponse builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the ok status.
     *
     * @return A boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the operation ID.
     *
     * @return An optional string
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * Get the template ID.
     *
     * @return An optional string
     */
    public Optional<String> getTemplateId() {
        return templateId;
    }

    /**
     * Get the list of push IDs.
     *
     * @return An optional immutable list of strings
     */
    public Optional<ImmutableList<String>> getPushIds() {
        return pushIds;
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
        return "TemplateResponse{" +
                "ok=" + ok +
                ", operationId=" + operationId +
                ", templateId=" + templateId +
                ", pushIds=" + pushIds +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateResponse that = (TemplateResponse) o;

        if (ok != that.ok) return false;
        if (!operationId.equals(that.operationId)) return false;
        if (!pushIds.equals(that.pushIds)) return false;
        if (!templateId.equals(that.templateId)) return false;
        if (!error.equals(that.error)) return false;
        if (!errorDetails.equals(that.errorDetails)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (ok ? 1 : 0);
        result = 31 * result + operationId.hashCode();
        result = 31 * result + templateId.hashCode();
        result = 31 * result + pushIds.hashCode();
        result = 31 * result + error.hashCode();
        result = 31 * result + errorDetails.hashCode();
        return result;
    }

    public static class Builder {

        private boolean ok = false;
        private String operationId = null;
        private String templateId = null;
        private ImmutableList.Builder<String> pushIds = ImmutableList.builder();
        private String error = null;
        private ErrorDetails errorDetails = null;

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
         * Set the template ID.
         *
         * @param templateId A string
         * @return Builder
         */
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Add a single push ID.
         *
         * @param pushId A string
         * @return Builder
         */
        public Builder addPushId(String pushId) {
            this.pushIds.add(pushId);
            return this;
        }

        /**
         * Add a list of push IDs.
         *
         * @param pushIds A list of strings.
         * @return Builder
         */
        public Builder addAllPushIds(List<String> pushIds) {
            this.pushIds.addAll(pushIds);
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
         * Build the TemplateResponse object.
         *
         * @return TemplateResponse
         */
        public TemplateResponse build() {
            return new TemplateResponse(this);
        }
    }
}
