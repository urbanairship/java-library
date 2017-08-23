package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.segments.model.SegmentView;

import java.io.IOException;

public class SegmentViewReader implements JsonObjectReader<SegmentView> {
    private final SegmentView.Builder builder;

    public SegmentViewReader() {
        this.builder = SegmentView.newBuilder();
    }

    public void readCriteria(JsonParser jsonParser) throws IOException {
        builder.setCriteria(jsonParser.readValueAs(Selector.class));
    }

    public void readDisplayName(JsonParser jsonParser) throws IOException {
        builder.setDisplayName(jsonParser.readValueAs(String.class));
    }

    @Override
    public SegmentView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }

}
