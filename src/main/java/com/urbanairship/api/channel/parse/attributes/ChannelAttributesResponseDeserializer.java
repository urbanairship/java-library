package com.urbanairship.api.channel.parse.attributes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.ChannelAttributesResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class ChannelAttributesResponseDeserializer extends JsonDeserializer<ChannelAttributesResponse> {
    private static final FieldParserRegistry<ChannelAttributesResponse, ChannelAttributesResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<ChannelAttributesResponse, ChannelAttributesResponseReader>(
            ImmutableMap.<String, FieldParser<ChannelAttributesResponseReader>>builder()
            .put("ok", (reader, jsonParser, context) -> reader.readOk(jsonParser))
            .put("warning", (reader, jsonParser, deserializationContext) -> reader.readWarning(jsonParser))
            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
            .build()
    );

    private final StandardObjectDeserializer<ChannelAttributesResponse, ?> deserializer;

    public ChannelAttributesResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ChannelAttributesResponse, ChannelAttributesResponseReader>(
                FIELD_PARSERS,
                () -> new ChannelAttributesResponseReader()
        );
    }

    @Override
    public ChannelAttributesResponse deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(jsonParser, context);
    }
}
