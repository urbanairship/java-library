/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.UUID;

public final class SinglePushInfoResponse {

    private final UUID pushUUID;
    private final int directResponses;
    private final int sends;
    private final PushType pushType;
    private final DateTime pushTime;
    private final Optional<UUID> groupID;
    private SinglePushInfoResponse(UUID pushUUID,
                                   int directResponses,
                                   int sends,
                                   PushType pushType,
                                   DateTime pushTime,
                                   UUID groupID) {
        this.pushUUID = pushUUID;
        this.directResponses = directResponses;
        this.sends = sends;
        this.pushType = pushType;
        this.pushTime = pushTime;
        this.groupID = Optional.fromNullable(groupID);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public UUID getPushUUID() {
        return pushUUID;
    }

    public int getDirectResponses() {
        return directResponses;
    }

    public int getSends() {
        return sends;
    }

    public PushType getPushType() {
        return pushType;
    }

    public DateTime getPushTime() {
        return pushTime;
    }

    public Optional<UUID> getGroupID() {
        return groupID;
    }

    @Override
    public String toString() {
        return "SinglePushInfoResponse{" +
                "pushUUID=" + pushUUID +
                ", directResponses=" + directResponses +
                ", sends=" + sends +
                ", pushType=" + pushType +
                ", pushTime=" + pushTime +
                ", groupID=" + groupID +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pushUUID, directResponses, sends, pushType, pushTime, groupID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SinglePushInfoResponse other = (SinglePushInfoResponse) obj;
        return Objects.equal(this.pushUUID, other.pushUUID) && Objects.equal(this.directResponses, other.directResponses) && Objects.equal(this.sends, other.sends) && Objects.equal(this.pushType, other.pushType) && Objects.equal(this.pushTime, other.pushTime) && Objects.equal(this.groupID, other.groupID);
    }

    public enum PushType {
        UNICAST_PUSH,
        BROADCAST_PUSH,
        TAG_PUSH,
        SCHEDULED_PUSH
    }

    public static class Builder {
        private UUID pushUUID;
        private int directResponses;
        private int sends;
        private PushType pushType;
        private DateTime pushTime;
        private UUID groupID;

        private Builder() {
        }

        public Builder setPushUUID(UUID value) {
            this.pushUUID = value;
            return this;
        }

        public Builder setDirectResponses(int value) {
            this.directResponses = value;
            return this;
        }

        public Builder setSends(int value) {
            this.sends = value;
            return this;
        }

        public Builder setPushType(PushType value) {
            this.pushType = value;
            return this;
        }

        public Builder setPushTime(String value) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            this.pushTime = formatter.parseDateTime(value);
            return this;
        }

        public Builder setGroupID(UUID value) {
            this.groupID = value;
            return this;
        }

        public SinglePushInfoResponse build() {
            Preconditions.checkNotNull(pushUUID, "Push UUID cannot be null.");
            Preconditions.checkNotNull(pushType, "Push Type cannot be null.");
            Preconditions.checkNotNull(pushTime, "Push Time cannot be null.");

            return new SinglePushInfoResponse(pushUUID, directResponses, sends, pushType, pushTime, groupID);
        }
    }
}
