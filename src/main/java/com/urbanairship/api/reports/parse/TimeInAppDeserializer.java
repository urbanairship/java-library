/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.TimeInApp;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class TimeInAppDeserializer extends JsonDeserializer<TimeInApp> {

    private static final FieldParserRegistry<TimeInApp, TimeInAppReader> FIELD_PARSERS =
            new MapFieldParserRegistry<TimeInApp, TimeInAppReader>(ImmutableMap.<String, FieldParser<TimeInAppReader>>builder()
                    .put("android", new FieldParser<TimeInAppReader>() {
                        @Override
                        public void parse(TimeInAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAndroid(jsonParser);
                        }
                    })
                    .put("date", new FieldParser<TimeInAppReader>() {
                        @Override
                        public void parse(TimeInAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDate(jsonParser);
                        }
                    })
                    .put("ios", new FieldParser<TimeInAppReader>() {
                        @Override
                        public void parse(TimeInAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readIOS(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<TimeInApp, ?> deserializer;

    public TimeInAppDeserializer() {
        deserializer = new StandardObjectDeserializer<TimeInApp, TimeInAppReader>(
                FIELD_PARSERS,
                new Supplier<TimeInAppReader>() {
                    @Override
                    public TimeInAppReader get() {
                        return new TimeInAppReader();
                    }
                }
        );
    }

    @Override
    public TimeInApp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
