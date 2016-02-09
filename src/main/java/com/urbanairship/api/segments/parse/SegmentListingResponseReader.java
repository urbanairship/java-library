package com.urbanairship.api.segments.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.segments.model.SegmentListingView;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

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

    @Override
    public SegmentListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }

}
