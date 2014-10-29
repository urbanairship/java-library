package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.location.SegmentDefinition;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class SegmentDefinitionReader implements JsonObjectReader<SegmentDefinition> {

    private final SegmentDefinition.Builder builder;

    public SegmentDefinitionReader() {
        this.builder = SegmentDefinition.newBuilder();
    }

    public void readDisplayName(JsonParser parser) throws IOException {
        builder.setDisplayName(StringFieldDeserializer.INSTANCE.deserialize(parser, "display_name"));
    }

    public void readCriteria(JsonParser parser) throws IOException {
        builder.setCriteria(parser.readValueAs(Selector.class));
    }

    @Override
    public SegmentDefinition validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (APIParsingException e) {
            throw e;
        }
    }
}
