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

    public APIPushResponse (String operationId, ImmutableList<String> pushIds) {
        this.operationId = Optional.fromNullable(operationId);
        this.pushIds = Optional.fromNullable(pushIds);
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     * @return Operation id for this API request
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * List of push id's, one for every actual push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     * @return List of push ids.
     */
    public Optional<ImmutableList<String>> getPushIds() {
        return pushIds;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    @Override
    public String toString() {
        return "APIPushResponse{" +
                "operationId=" + operationId +
                ", pushIds=" + pushIds +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushIds);
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
        return Objects.equal(this.operationId, other.operationId) && Objects.equal(this.pushIds, other.pushIds);
    }

    /**
     * APIPushResponse Builder
     */
    public static class Builder {
        private String operationId;
        private ImmutableList.Builder<String> pushIds = ImmutableList.builder();

        private Builder() { }

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

        public APIPushResponse build() {
            return new APIPushResponse(operationId, pushIds.build());

        }
    }
}
