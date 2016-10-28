/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.templates.model.PartialPushPayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PartialPushPayloadDeserializer extends JsonDeserializer<PartialPushPayload> {
    private static final FieldParserRegistry<PartialPushPayload, PartialPushPayloadReader> FIELD_PARSER =
            new MapFieldParserRegistry<PartialPushPayload, PartialPushPayloadReader>(
                    ImmutableMap.<String, FieldParser<PartialPushPayloadReader>>builder()
                            .put("notification", new FieldParser<PartialPushPayloadReader>() {
                                @Override
                                public void parse(PartialPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNotification(jsonParser);
                                }
                            })
                            .put("options", new FieldParser<PartialPushPayloadReader>() {
                                @Override
                                public void parse(PartialPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushOptions(jsonParser);
                                }
                            })
                            .put("message", new FieldParser<PartialPushPayloadReader>() {
                                @Override
                                public void parse(PartialPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readRichPushMessage(jsonParser);
                                }
                            })
                            .put("in_app", new FieldParser<PartialPushPayloadReader>() {
                                @Override
                                public void parse(PartialPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readInApp(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<PartialPushPayload, ?> deserializer;

    public PartialPushPayloadDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PartialPushPayload, PartialPushPayloadReader>(
                FIELD_PARSER,
                new Supplier<PartialPushPayloadReader>() {
                    @Override
                    public PartialPushPayloadReader get() {
                        return new PartialPushPayloadReader();
                    }
                }
        );
    }

    @Override
    public PartialPushPayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
