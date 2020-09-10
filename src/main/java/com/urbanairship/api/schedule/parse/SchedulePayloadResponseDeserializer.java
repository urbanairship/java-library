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
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;

import java.io.IOException;

public final class SchedulePayloadResponseDeserializer extends JsonDeserializer<SchedulePayloadResponse> {

    private static final FieldParserRegistry<SchedulePayloadResponse, SchedulePayloadResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<SchedulePayloadResponse, SchedulePayloadResponseReader>(
            ImmutableMap.<String, FieldParser<SchedulePayloadResponseReader>>builder()
                    .put("schedule", new FieldParser<SchedulePayloadResponseReader>() {
                        @Override
                        public void parse(SchedulePayloadResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readSchedule(jsonParser);
                        }
                    })
                    .put("url", new FieldParser<SchedulePayloadResponseReader>() {
                        @Override
                        public void parse(SchedulePayloadResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readUrl(jsonParser);
                        }
                    })
                    .put("name", new FieldParser<SchedulePayloadResponseReader>() {
                        @Override
                        public void parse(SchedulePayloadResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readName(jsonParser);
                        }
                    })
                    .put("push", new FieldParser<SchedulePayloadResponseReader>() {
                        @Override
                        public void parse(SchedulePayloadResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushPayload(jsonParser);
                        }
                    })
                    .put("push_ids", new FieldParser<SchedulePayloadResponseReader>() {
                        @Override
                        public void parse(SchedulePayloadResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushIds(jsonParser);
                        }
                    })
                    .build()
    );

    public static final SchedulePayloadResponseDeserializer INSTANCE = new SchedulePayloadResponseDeserializer();

    private final StandardObjectDeserializer<SchedulePayloadResponse, ?> deserializer;

    public SchedulePayloadResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<SchedulePayloadResponse, SchedulePayloadResponseReader>(
                FIELD_PARSERS,
                new Supplier<SchedulePayloadResponseReader>() {
                    @Override
                    public SchedulePayloadResponseReader get() {
                        return new SchedulePayloadResponseReader();
                    }
                }
        );
    }

    @Override
    public SchedulePayloadResponse deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        try {
            return deserializer.deserialize(parser, deserializationContext);
        } catch (Exception e) {
            throw APIParsingException.raise(String.format("Error parsing schedule object. %s", e.getMessage()), parser);
        }
    }
}
