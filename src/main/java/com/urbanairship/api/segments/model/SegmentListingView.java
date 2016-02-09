package com.urbanairship.api.segments.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import sun.security.util.BigInt;

import java.math.BigInteger;

public class SegmentListingView {
    private final String displayName;
    private final String segmentId;
    private final long creationDate;
    private final long modificationDate;

    private SegmentListingView(String displayName, String segmentId, long creationDate, long modificationDate) {
        this.displayName = displayName;
        this.segmentId = segmentId;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    /**
     * New SegmentListView builder.
     *
     * @return builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the segment ID.
     *
     * @return String segment ID
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * Get the display name.
     *
     * @return String display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the creation date.
     *
     * @return int creation date
     */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * Get the modification date.
     *
     * @return int modification date
     */
    public long getModificationDate() {
        return modificationDate;
    }

    @Override
    public String toString() {
        return "SegmentListView{" +
                "displayName=" + displayName +
                ", segmentId=" + segmentId +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(displayName, segmentId, creationDate, modificationDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SegmentListingView other = (SegmentListingView) obj;

        return Objects.equal(this.displayName, other.displayName) &&
                Objects.equal(this.segmentId, other.segmentId) &&
                Objects.equal(this.creationDate, other.creationDate) &&
                Objects.equal(this.modificationDate, other.modificationDate);
    }

    public final static class Builder {
        private String displayName = null;
        private String segmentId = null;
        private long creationDate = -1;
        private long modificationDate = -1;

        private Builder() {
        }

        /**
         * Set the display name
         *
         * @param displayName String
         * @return Builder
         */
        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Set the segment ID
         *
         * @param segmentId String
         * @return Builder
         */
        public Builder setId(String segmentId) {
            this.segmentId = segmentId;
            return this;
        }

        public Builder setCreationDate(long creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder setModificationDate(long modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public SegmentListingView build() {
            Preconditions.checkNotNull(displayName);
            Preconditions.checkNotNull(segmentId);
            Preconditions.checkArgument(creationDate > -1);
            Preconditions.checkArgument(modificationDate > -1);

            return new SegmentListingView(displayName, segmentId, creationDate, modificationDate);
        }
    }

}
