package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.segments.model.SegmentView;

import java.io.IOException;

public class SegmentViewDeserializer extends JsonDeserializer<SegmentView> {
    public static final FieldParserRegistry<SegmentView, SegmentViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentView, SegmentViewReader>(
                    ImmutableMap.<String, FieldParser<SegmentViewReader>>builder()
                            .put("criteria", (reader, jsonParser, deserializationContext) -> reader.readCriteria(jsonParser))
                            .put("display_name", (reader, jsonParser, deserializationContext) -> reader.readDisplayName(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );


    private final StandardObjectDeserializer<SegmentView, ?> deserializer;

    public SegmentViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSER,
                SegmentViewReader::new
        );
    }

    @Override
    public SegmentView deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
