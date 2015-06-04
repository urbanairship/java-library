/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.model;


import com.google.common.base.Objects;

public final class SegmentInformation {

    private final Long creationDate;
    private final String displayName;
    private final String id;
    private final Long modificationDate;

    private SegmentInformation(Long creationDate, String displayName, String id, Long modificationDate) {
        this.creationDate = creationDate;
        this.displayName = displayName;
        this.id = id;
        this.modificationDate = modificationDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(creationDate, displayName, id, modificationDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SegmentInformation other = (SegmentInformation) obj;
        return Objects.equal(this.creationDate, other.creationDate) && Objects.equal(this.displayName, other.displayName) && Objects.equal(this.id, other.id) && Objects.equal(this.modificationDate, other.modificationDate);
    }

    @Override
    public String toString() {
        return "SegmentInformation{" +
                "creationDate=" + creationDate +
                ", displayName='" + displayName + '\'' +
                ", id='" + id + '\'' +
                ", modificationDate=" + modificationDate +
                '}';
    }

    public static class Builder {
        private Long creationDate;
        private String displayName;
        private String id;
        private Long modificationDate;

        private Builder() {
        }

        public Builder setCreationDate(Long value) {
            this.creationDate = value;
            return this;
        }

        public Builder setDisplayName(String value) {
            this.displayName = value;
            return this;
        }

        public Builder setId(String value) {
            this.id = value;
            return this;
        }

        public Builder setModificationDate(Long value) {
            this.modificationDate = value;
            return this;
        }

        public SegmentInformation build() {
            return new SegmentInformation(creationDate, displayName, id, modificationDate);
        }
    }
}
