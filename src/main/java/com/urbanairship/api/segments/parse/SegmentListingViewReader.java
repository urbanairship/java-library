package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.segments.model.SegmentListingView;

import java.io.IOException;

public class SegmentListingViewReader implements JsonObjectReader<SegmentListingView> {
    private final SegmentListingView.Builder builder;

    public SegmentListingViewReader() {
        this.builder = SegmentListingView.newBuilder();
    }

    public void readDisplayName(JsonParser jsonParser) throws IOException {
        builder.setDisplayName(jsonParser.readValueAs(String.class));
    }

    public void readId(JsonParser jsonParser) throws IOException {
        builder.setId(jsonParser.readValueAs(String.class));
    }

    public void readCreationDate(JsonParser jsonParser) throws IOException {
        builder.setCreationDate(jsonParser.readValueAs(long.class));
    }

    public void readModificationDate(JsonParser jsonParser) throws IOException {
        builder.setModificationDate(jsonParser.readValueAs(long.class));
    }

    @Override
    public SegmentListingView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }

}
