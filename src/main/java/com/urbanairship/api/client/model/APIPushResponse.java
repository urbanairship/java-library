/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;


import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * Represents a response from the Urban Airship API for Push Notifications.
 */
public final class APIPushResponse {

    private final Optional<String> operationId;
    private final Optional<ImmutableList<String>> pushIds;
    private final boolean ok;

    public APIPushResponse(String operationId, ImmutableList<String> pushIds, boolean ok) {
        this.operationId = Optional.fromNullable(operationId);
        this.pushIds = Optional.fromNullable(pushIds);
        this.ok = ok;
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
     * List of push id's, one for every actual push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return List of push ids.
     */
    public Optional<ImmutableList<String>> getPushIds() {
        return pushIds;
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */
    public boolean getOk() {
        return ok;
    }

    @Override
    public String toString() {
        return "APIPushResponse{" +
                "operationId=" + operationId +
                ", pushIds=" + pushIds +
                ", ok=" + ok +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushIds, ok);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final APIPushResponse other = (APIPushResponse) obj;
        return Objects.equal(this.operationId, other.operationId) && Objects.equal(this.pushIds, other.pushIds) && Objects.equal(this.ok, other.ok);
    }

    /**
     * APIPushResponse Builder
     */
    public static class Builder {
        private String operationId;
        private ImmutableList.Builder<String> pushIds = ImmutableList.builder();
        private boolean ok = false;

        private Builder() {
        }

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder addPushId(String pushid) {
            this.pushIds.add(pushid);
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

        public APIPushResponse build() {
            return new APIPushResponse(operationId, pushIds.build(), ok);

        }
    }
}
