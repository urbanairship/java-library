package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.SegmentView;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
