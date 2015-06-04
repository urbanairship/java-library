/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

public class AudienceSegmentTuple {

    private final String segmentId;
    private final AudienceSegment segment;

    public AudienceSegmentTuple(String segmentId, AudienceSegment segment) {
        this.segmentId = segmentId;
        this.segment = segment;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public AudienceSegment getSegment() {
        return segment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AudienceSegmentTuple that = (AudienceSegmentTuple) o;

        if (segment != null ? !segment.equals(that.segment) : that.segment != null) {
            return false;
        }
        if (segmentId != null ? !segmentId.equals(that.segmentId) : that.segmentId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = segmentId != null ? segmentId.hashCode() : 0;
        result = 31 * result + (segment != null ? segment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AudienceSegmentTuple{" +
                "segmentId='" + segmentId + '\'' +
                ", segment=" + segment +
                '}';
    }
}
