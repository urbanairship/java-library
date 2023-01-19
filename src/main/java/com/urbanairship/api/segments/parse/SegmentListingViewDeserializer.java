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
import com.urbanairship.api.segments.model.SegmentListingView;

import java.io.IOException;

public class SegmentListingViewDeserializer extends JsonDeserializer<SegmentListingView> {
    public static final FieldParserRegistry<SegmentListingView, SegmentListingViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<SegmentListingView, SegmentListingViewReader>(
                    ImmutableMap.<String, FieldParser<SegmentListingViewReader>>builder()
                            .put("creation_date", (reader, jsonParser, deserializationContext) -> reader.readCreationDate(jsonParser))
                            .put("display_name", (reader, jsonParser, deserializationContext) -> reader.readDisplayName(jsonParser))
                            .put("id", (reader, jsonParser, deserializationContext) -> reader.readId(jsonParser))
                            .put("modification_date", (reader, jsonParser, deserializationContext) -> reader.readModificationDate(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<SegmentListingView, ?> deserializer;

    public SegmentListingViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<SegmentListingView, SegmentListingViewReader>(
                FIELD_PARSER,
                () -> new SegmentListingViewReader()
        );
    }

    @Override
    public SegmentListingView deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
