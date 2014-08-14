package com.urbanairship.api.tag.model;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class BatchTagSet {

    public enum DEVICEIDTYPES { IOS_CHANNEL, DEVICE_TOKEN, APID }

    private final DEVICEIDTYPES id_type;
    private final String device_id;
    private final ImmutableSet<String> tags;

    public static Builder newBuilder() {
        return new Builder();
    }

    private BatchTagSet(DEVICEIDTYPES id_type, String device_id, ImmutableSet<String> tags) {
        this.id_type = id_type;
        this.device_id = device_id;
        this.tags = tags;
    }

    public DEVICEIDTYPES getIdType() {
        return id_type;
    }

    public String getDeviceID() {
        return device_id;
    }

    public ImmutableSet<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        BatchTagSet that = (BatchTagSet) o;

        if (device_id != null ? !device_id.equals(that.device_id) : that.device_id != null) { return false; }
        if (id_type != that.id_type) { return false; }
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id_type != null ? id_type.hashCode() : 0;
        result = 31 * result + (device_id != null ? device_id.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BatchTagSet{" +
                "id_type=" + id_type +
                ", device_id='" + device_id + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static class Builder {
        private DEVICEIDTYPES id_type = null;
        private String device_id = null;
        private ImmutableSet.Builder<String> tags = null;

        private Builder() { }

        public Builder setDevice(DEVICEIDTYPES type, String id) {
            this.id_type = type;
            this.device_id = id;
            return this;
        }

        public Builder addTag(String value) {
            if (tags == null) { tags = ImmutableSet.builder(); }
            tags.add(value);
            return this;
        }

        public BatchTagSet build() {
            Preconditions.checkArgument(!(id_type == null), "There must be a type specified");
            Preconditions.checkArgument(!(device_id == null), "There must be a device id");
            Preconditions.checkArgument(!(tags == null), "There must be tags to apply");

            return new BatchTagSet(id_type, device_id, tags.build());
        }
    }

}
