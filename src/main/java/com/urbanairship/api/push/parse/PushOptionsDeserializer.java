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
import com.urbanairship.api.push.model.PushOptions;

import java.io.IOException;

public class PushOptionsDeserializer extends JsonDeserializer<PushOptions> {

    private static final FieldParserRegistry<PushOptions, PushOptionsReader> FIELD_PARSERS = new MapFieldParserRegistry<PushOptions, PushOptionsReader>(ImmutableMap.<String, FieldParser<PushOptionsReader>>builder()
        .put("expiry", new FieldParser<PushOptionsReader>() {
            @Override
            public void parse(PushOptionsReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                reader.readExpiry(parser);
            }
        })
        .put("no_throttle", new FieldParser<PushOptionsReader>() {
            @Override
            public void parse(PushOptionsReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                reader.readNoThrottle(parser);
            }
        })
        .put("personalization", new FieldParser<PushOptionsReader>() {
            @Override
            public void parse(PushOptionsReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                reader.readPersonalization(parser);
            }
        })
        .build()
    );

    private final StandardObjectDeserializer<PushOptions, ?> deserializer;

    public PushOptionsDeserializer() {
        deserializer = new StandardObjectDeserializer<PushOptions, PushOptionsReader>(
                FIELD_PARSERS,
                new Supplier<PushOptionsReader>() {
                    @Override
                    public PushOptionsReader get() {
                        return new PushOptionsReader();
                    }
                }
        );
    }

    @Override
    public PushOptions deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
