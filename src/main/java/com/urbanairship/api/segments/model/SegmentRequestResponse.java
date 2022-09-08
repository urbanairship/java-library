package com.urbanairship.api.segments.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.model.GenericResponse;

import java.util.Objects;
import java.util.Optional;

public class SegmentRequestResponse extends GenericResponse {

    private final String segmentId;

    public SegmentRequestResponse(
            @JsonProperty("ok") Boolean ok,
            @JsonProperty("operation_id") String operationId,
            @JsonProperty("error") String error,
            @JsonProperty("details") ErrorDetails errorDetails,
            @JsonProperty("error_code") Integer errorCode,
            @JsonProperty("warning") String warning,
            @JsonProperty("segment_id") String segmentId) {
        super(ok, operationId, error, errorDetails, errorCode, warning);
        this.segmentId = segmentId;
    }

    public Optional<String> getSegmentId() {
        return Optional.ofNullable(segmentId);
    }


    @Override
    public String toString() {
        return "SegmentRequestResponse{" +
                "segmentId='" + segmentId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SegmentRequestResponse that = (SegmentRequestResponse) o;
        return Objects.equals(segmentId, that.segmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), segmentId);
    }
}
