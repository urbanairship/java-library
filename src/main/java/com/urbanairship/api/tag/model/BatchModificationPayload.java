/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;

public final class BatchModificationPayload extends PushModelObject {

    private final ImmutableSet<BatchTagSet> batchObject;

    public static Builder newBuilder() {
        return new Builder();
    }

    private BatchModificationPayload(ImmutableSet<BatchTagSet> set) {
        this.batchObject = set;
    }

    public ImmutableSet<BatchTagSet> getBatchObjects() {
        return batchObject;
    }

    @Override
    public String toString() {
        return "BatchModificationPayload{" +
                "batchObject=" + batchObject +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(batchObject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BatchModificationPayload other = (BatchModificationPayload) obj;
        return Objects.equal(this.batchObject, other.batchObject);
    }

    public static class Builder {
        private ImmutableSet.Builder<BatchTagSet> batch_object = null;

        private Builder() { }

        public Builder addBatchObject(BatchTagSet value) {
            if (batch_object == null) { batch_object = ImmutableSet.builder(); }
            batch_object.add(value);
            return this;
        }

        public BatchModificationPayload build() {
            Preconditions.checkNotNull(batch_object, "There must be a batch object");

            return new BatchModificationPayload(batch_object.build());
        }
    }

}
