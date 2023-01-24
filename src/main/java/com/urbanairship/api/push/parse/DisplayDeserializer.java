package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.Display;

import java.io.IOException;

public class DisplayDeserializer extends JsonDeserializer<Display> {
    private static final FieldParserRegistry<Display, DisplayReader> FIELD_PARSERS =
            new MapFieldParserRegistry<Display, DisplayReader>(
                    ImmutableMap.<String, FieldParser<DisplayReader>>builder()
                            .put("primary_color", (reader, jsonParser, deserializationContext) -> reader.readPrimaryColor(jsonParser))
                            .put("secondary_color", (reader, jsonParser, deserializationContext) -> reader.readSecondaryColor(jsonParser))
                            .put("duration", (reader, jsonParser, deserializationContext) -> reader.readDuration(jsonParser))
                            .put("position", (reader, jsonParser, deserializationContext) -> reader.readPosition(jsonParser))
                            .build());

    private final StandardObjectDeserializer<Display, ?> deserializer;

    public DisplayDeserializer() {
        deserializer = new StandardObjectDeserializer<Display, DisplayReader>(
                FIELD_PARSERS,
                () -> new DisplayReader()
        );
    }

    @Override
    public Display deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }

}
