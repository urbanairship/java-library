/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.schedule.model.SchedulePayload;

import java.io.IOException;

public final class SchedulePayloadDeserializer extends JsonDeserializer<SchedulePayload> {

    private static final FieldParserRegistry<SchedulePayload, SchedulePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<SchedulePayload, SchedulePayloadReader>(
            ImmutableMap.<String, FieldParser<SchedulePayloadReader>>builder()
                    .put("schedule", new FieldParser<SchedulePayloadReader>() {
                        @Override
                        public void parse(SchedulePayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSchedule(jsonParser);
                        }
                    })
                    .put("url", new FieldParser<SchedulePayloadReader>() {
                        @Override
                        public void parse(SchedulePayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readUrl(jsonParser);
                        }
                    })
                    .put("name", new FieldParser<SchedulePayloadReader>() {
                        @Override
                        public void parse(SchedulePayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readName(jsonParser);
                        }
                    })
                    .put("push", new FieldParser<SchedulePayloadReader>() {
                        @Override
                        public void parse(SchedulePayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushPayload(jsonParser);
                        }
                    })
                    .put("push_ids", new FieldParser<SchedulePayloadReader>() {
                        @Override
                        public void parse(SchedulePayloadReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushIds(jsonParser);
                        }
                    })
                    .build()
    );

    public static final SchedulePayloadDeserializer INSTANCE = new SchedulePayloadDeserializer();

    private final StandardObjectDeserializer<SchedulePayload, ?> deserializer;

    public SchedulePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<SchedulePayload, SchedulePayloadReader>(
                FIELD_PARSERS,
                new Supplier<SchedulePayloadReader>() {
                    @Override
                    public SchedulePayloadReader get() {
                        return new SchedulePayloadReader();
                    }
                }
        );
    }

    @Override
    public SchedulePayload deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        try {
            return deserializer.deserialize(parser, deserializationContext);
        } catch (Exception e) {
            throw APIParsingException.raise(String.format("Error parsing schedule object. %s", e.getMessage()), parser);
        }
    }
}
