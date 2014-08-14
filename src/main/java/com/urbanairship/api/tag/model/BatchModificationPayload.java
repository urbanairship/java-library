package com.urbanairship.api.tag.model;


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
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        BatchModificationPayload that = (BatchModificationPayload) o;

        if (batchObject != null ? !batchObject.equals(that.batchObject) : that.batchObject != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return batchObject != null ? batchObject.hashCode() : 0;
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
            Preconditions.checkArgument(!(batch_object == null), "There must be a batch object");

            return new BatchModificationPayload(batch_object.build());
        }
    }

}
