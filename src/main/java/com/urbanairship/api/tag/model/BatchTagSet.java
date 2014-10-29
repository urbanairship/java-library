/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.tag.model;


import com.google.common.base.Objects;
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
    public String toString() {
        return "BatchTagSet{" +
                "idType=" + idType +
                ", deviceID='" + deviceID + '\'' +
                ", tags=" + tags +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idType, deviceID, tags);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BatchTagSet other = (BatchTagSet) obj;
        return Objects.equal(this.idType, other.idType) && Objects.equal(this.deviceID, other.deviceID) && Objects.equal(this.tags, other.tags);
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
