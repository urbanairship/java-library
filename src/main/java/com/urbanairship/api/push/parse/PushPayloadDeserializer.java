/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PushPayloadDeserializer extends JsonDeserializer<PushPayload> {

    private static final FieldParserRegistry<PushPayload, PushPayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<PushPayload, PushPayloadReader>(
        ImmutableMap.<String, FieldParser<PushPayloadReader>>builder()
            .put("audience", new FieldParser<PushPayloadReader>() {
                @Override
                public void parse(PushPayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readAudience(jsonParser);
                }
            })
            .put("notification", new FieldParser<PushPayloadReader>() {
                @Override
                public void parse(PushPayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readNotification(jsonParser);
                }
                })
            .put("message", new FieldParser<PushPayloadReader>() {
                @Override
                public void parse(PushPayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readMessage(jsonParser);
                }
                })
            .put("options", new FieldParser<PushPayloadReader>() {
                @Override
                public void parse(PushPayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOptions(jsonParser);
                }
            })
            .put("device_types", new FieldParser<PushPayloadReader>() {
                @Override
                public void parse(PushPayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readDeviceTypes(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<PushPayload, ?> deserializer;

    public PushPayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<PushPayload, PushPayloadReader>(
            FIELD_PARSERS,
            new Supplier<PushPayloadReader>() {
                @Override
                public PushPayloadReader get() {
                    return new PushPayloadReader();
                }
            }
        );
    }

    @Override
    public PushPayload deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
