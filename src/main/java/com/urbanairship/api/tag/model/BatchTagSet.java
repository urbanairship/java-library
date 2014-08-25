/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public final class BatchTagSet {

    public enum DEVICEIDTYPES { IOS_CHANNEL, DEVICE_TOKEN, APID }

    private final DEVICEIDTYPES idType;
    private final String deviceID;
    private final ImmutableSet<String> tags;

    public static Builder newBuilder() {
        return new Builder();
    }

    private BatchTagSet(DEVICEIDTYPES idType, String deviceID, ImmutableSet<String> tags) {
        this.idType = idType;
        this.deviceID = deviceID;
        this.tags = tags;
    }

    public DEVICEIDTYPES getIdType() {
        return idType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public ImmutableSet<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        BatchTagSet that = (BatchTagSet) o;

        if (deviceID != null ? !deviceID.equals(that.deviceID) : that.deviceID != null) { return false; }
        if (idType != that.idType) { return false; }
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = idType != null ? idType.hashCode() : 0;
        result = 31 * result + (deviceID != null ? deviceID.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BatchTagSet{" +
                "idType=" + idType +
                ", deviceID='" + deviceID + '\'' +
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
            Preconditions.checkNotNull(id_type, "There must be a type specified");
            Preconditions.checkNotNull(device_id, "There must be a device id");
            Preconditions.checkNotNull(tags, "There must be tags to apply");

            return new BatchTagSet(id_type, device_id, tags.build());
        }
    }

}
