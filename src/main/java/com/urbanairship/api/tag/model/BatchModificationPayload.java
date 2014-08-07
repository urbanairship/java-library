package com.urbanairship.api.tag.model;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;

public final class BatchModificationPayload extends PushModelObject {

    private final ImmutableSet<BatchTagSet> batch_object;

    public static Builder newBuilder() {
        return new Builder();
    }

    private BatchModificationPayload(ImmutableSet<BatchTagSet> set) {
        this.batch_object = set;
    }

    public ImmutableSet<BatchTagSet> getBatchObjects() {
        return batch_object;
    }

    @Override
    public String toString() {
        return "BatchModificationPayload{" +
                "batch_object=" + batch_object +
                '}';
    }

    public static class Builder {
        private ImmutableSet.Builder<BatchTagSet> batch_object = null;

        private Builder() { }

        public Builder addBatchObject(BatchTagSet value) {
            if (batch_object == null)
                batch_object = ImmutableSet.builder();
            batch_object.add(value);
            return this;
        }

        public BatchModificationPayload build() {
            Preconditions.checkArgument(!(batch_object == null), "There must be a batch object");

            return new BatchModificationPayload(batch_object.build());
        }
    }

}
