/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public final class QuietTimeDeserializer extends JsonDeserializer<QuietTime> {

    private static final FieldParserRegistry<QuietTime, QuietTimeReader> FIELD_PARSERS = new MapFieldParserRegistry<QuietTime, QuietTimeReader>(
            ImmutableMap.<String, FieldParser<QuietTimeReader>>builder()
                    .put(Constants.START, new FieldParser<QuietTimeReader>() {
                        @Override
                        public void parse(QuietTimeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readStart(jsonParser);
                        }
                    })
                    .put(Constants.END, new FieldParser<QuietTimeReader>() {
                        @Override
                        public void parse(QuietTimeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readEnd(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<QuietTime, ?> deserializer;

    public QuietTimeDeserializer() {
        deserializer = new StandardObjectDeserializer<QuietTime, QuietTimeReader>(
                FIELD_PARSERS,
                new Supplier<QuietTimeReader>() {
                    @Override
                    public QuietTimeReader get() {
                        return new QuietTimeReader();
                    }
                }
        );
    }

    @Override
    public QuietTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
