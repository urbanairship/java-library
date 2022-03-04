package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentListingView;

import java.io.IOException;
import java.util.List;

public final class SegmentListingResponseReader implements JsonObjectReader<SegmentListingResponse> {
    private final SegmentListingResponse.Builder builder;

    public SegmentListingResponseReader() {
        this.builder = SegmentListingResponse.newBuilder();
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readSegments(JsonParser jsonParser) throws IOException {
        builder.addAllSegmentObjects(
                (List<SegmentListingView>) jsonParser.readValueAs(new TypeReference<List<SegmentListingView>>() {}));
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(Boolean.class));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public SegmentListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }

}
