/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.location.SegmentDefinition;
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
        return builder.build();
    }
}
