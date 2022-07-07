/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;

import com.google.common.base.Objects;
import com.urbanairship.api.common.model.ErrorDetails;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Optional;
import java.util.UUID;

/**
 * Return a single push info object
 */
public final class PushInfoResponse {

    private final UUID pushId;
    private final int directResponses;
    private final int sends;
    private final PushType pushType;
    private final DateTime pushTime;
    private final Optional<UUID> groupID;
    private final boolean ok;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private PushInfoResponse() {
        this(null, 0, 0, null, null, null, null, null, null);
    }

    private PushInfoResponse(UUID pushId,
                             int directResponses,
                             int sends,
                             PushType pushType,
                             DateTime pushTime,
                             UUID groupID,
                             Boolean ok,
                             String error,
                             ErrorDetails errorDetails) {
        this.pushId = pushId;
        this.directResponses = directResponses;
        this.sends = sends;
        this.pushType = pushType;
        this.pushTime = pushTime;
        this.groupID = Optional.ofNullable(groupID);
        this.ok = ok;
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
    }

    /**
     * New SinglePushInfoResponse builder
     *
     * @return builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the push uuid
     *
     * @return String uuid
     */
    public UUID getPushId() {
        return pushId;
    }

    /**
     * Get the number of direct responses to the push
     *
     * @return int direct responses
     */
    public int getDirectResponses() {
        return directResponses;
    }

    /**
     * Get the number of push sends
     *
     * @return int sends
     */
    public int getSends() {
        return sends;
    }

    /**
     * Get the type of push -- can be one of {@code UNICAST_PUSH},
     * {@code BROADCAST_PUSH}, {@code TAG_PUSH}, {@code SCHEDULED_PUSH},
     * or {@code SEGMENTS_PUSH}.
     *
     * @return String push type
     */
    public PushType getPushType() {
        return pushType;
    }

    /**
     * Get the time when the push was created
     *
     * @return DateTime of creation
     */
    public DateTime getPushTime() {
        return pushTime;
    }

    /**
     * Get the group ID
     *
     * @return Optional UUID
     */
    public Optional<UUID> getGroupID() {
        return groupID;
    }

            /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
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
        return "SinglePushInfoResponse{" +
                "pushId=" + pushId +
                ", directResponses=" + directResponses +
                ", sends=" + sends +
                ", pushType=" + pushType +
                ", pushTime=" + pushTime +
                ", groupID=" + groupID +
                ", ok=" + ok +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pushId, directResponses, sends, pushType, pushTime, groupID, ok, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PushInfoResponse other = (PushInfoResponse) obj;
        return Objects.equal(this.pushId, other.pushId) && Objects.equal(this.directResponses, other.directResponses) && Objects.equal(this.sends, other.sends) && Objects.equal(this.pushType, other.pushType) && Objects.equal(this.pushTime, other.pushTime) && Objects.equal(this.groupID, other.groupID) && Objects.equal(ok, other.ok) && Objects.equal(error, other.error) && Objects.equal(errorDetails, other.errorDetails);
    }

    public enum PushType {
        UNICAST_PUSH,
        BROADCAST_PUSH,
        TAG_PUSH,
        SCHEDULED_PUSH,
        SEGMENTS_PUSH
    }

    public final static class Builder {
        private UUID pushId;
        private int directResponses;
        private int sends;
        private PushType pushType;
        private DateTime pushTime;
        private UUID groupID;
        private boolean ok;
        private String error;
        private ErrorDetails errorDetails;

        private Builder() {
        }

        /**
         * Set the push uuid
         *
         * @param value UUID
         * @return Builder
         */
        public Builder setPushId(UUID value) {
            this.pushId = value;
            return this;
        }

        /**
         * Set the number of direct responses
         *
         * @param value int
         * @return Builder
         */
        public Builder setDirectResponses(int value) {
            this.directResponses = value;
            return this;
        }

        /**
         * Set the number of sends
         *
         * @param value int
         * @return Builder
         */
        public Builder setSends(int value) {
            this.sends = value;
            return this;
        }

        /**
         * Set the push type
         *
         * @param value PushType
         * @return Builder
         */
        public Builder setPushType(PushType value) {
            this.pushType = value;
            return this;
        }

        /**
         * Set the creation time
         *
         * @param value String
         * @return Builder
         */
        public Builder setPushTime(String value) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            this.pushTime = formatter.parseDateTime(value);
            return this;
        }

        /**
         * Set the group ID
         *
         * @param value UUID
         * @return Builder
         */
        public Builder setGroupId(UUID value) {
            this.groupID = value;
            return this;
        }

                /**
         * Set the ok status
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setOk(boolean value) {
            this.ok = value;
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
         * Build the SinglePushInfoResponse object
         *
         * @return SinglePushInfoResponse
         */
        public PushInfoResponse build() {
            return new PushInfoResponse(
                    pushId,
                    directResponses,
                    sends,
                    pushType,
                    pushTime,
                    groupID,
                    ok,
                    error,
                    errorDetails);
        }
    }
}
