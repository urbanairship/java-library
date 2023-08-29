/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

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
import com.urbanairship.api.push.model.PushPayload;

import java.io.IOException;

public class PushPayloadDeserializer extends JsonDeserializer<PushPayload> {

    private static final FieldParserRegistry<PushPayload, PushPayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<PushPayload, PushPayloadReader>(

            ImmutableMap.<String, FieldParser<PushPayloadReader>>builder()
                    .put("audience", (reader, jsonParser, deserializationContext) -> reader.readAudience(jsonParser))
                    .put("notification", (reader, jsonParser, deserializationContext) -> reader.readNotification(jsonParser))
                    .put("message", (reader, jsonParser, deserializationContext) -> reader.readMessage(jsonParser))
                    .put("options", (reader, jsonParser, deserializationContext) -> reader.readOptions(jsonParser))
                    .put("device_types", (reader, jsonParser, deserializationContext) -> reader.readDeviceTypes(jsonParser))
                    .put("in_app", (reader, jsonParser, deserializationContext) -> reader.readInApp(jsonParser))
                    .put("orchestration", (reader, jsonParser, deserializationContext) -> reader.readOrchestration(jsonParser))
                    .put("message_type", (reader, jsonParser, deserializationContext) -> reader.readMessageType(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<PushPayload, ?> deserializer;

    public PushPayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<>(
                FIELD_PARSERS,
                () -> new PushPayloadReader()
        );
    }

    @Override
    public PushPayload deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
