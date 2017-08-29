package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.segments.model.SegmentView;

import java.io.IOException;

public class SegmentViewSerializer extends JsonSerializer<SegmentView> {

    @Override
    public void serialize(SegmentView segment, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("criteria", segment.getCriteria());
        jgen.writeObjectField("display_name", segment.getDisplayName());

        jgen.writeEndObject();
    }
}
