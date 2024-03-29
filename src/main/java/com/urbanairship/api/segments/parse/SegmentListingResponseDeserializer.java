package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.segments.model.SegmentListingResponse;

import java.io.IOException;

public class SegmentListingResponseDeserializer extends JsonDeserializer<SegmentListingResponse> {
    public static final FieldParserRegistry<SegmentListingResponse, SegmentListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentListingResponse, SegmentListingResponseReader>(
                    ImmutableMap.<String, FieldParser<SegmentListingResponseReader>>builder()
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("segments", (reader, jsonParser, deserializationContext) -> reader.readSegments(jsonParser))
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
    );

    private final StandardObjectDeserializer<SegmentListingResponse, ?> deserializer;

    public SegmentListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SegmentListingResponse, SegmentListingResponseReader>(
                FIELD_PARSER,
                () -> new SegmentListingResponseReader()
        );
    }

    @Override
    public SegmentListingResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
