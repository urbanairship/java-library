/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.*;
import com.urbanairship.api.reports.model.Opens;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class OpensDeserializer extends JsonDeserializer<Opens> {

    private static final FieldParserRegistry<Opens, OpensReader> FIELD_PARSERS =
            new MapFieldParserRegistry<Opens, OpensReader>(ImmutableMap.<String, FieldParser<OpensReader>>builder()
            .put("android", new FieldParser<OpensReader>() {
                @Override
                public void parse(OpensReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readAndroid(jsonParser);
                }
            })
            .put("date", new FieldParser<OpensReader>() {
                @Override
                public void parse(OpensReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readDate(jsonParser);
                }
            })
            .put("ios", new FieldParser<OpensReader>() {
                @Override
                public void parse(OpensReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readIOS(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<Opens, ?> deserializer;

    public OpensDeserializer() {
        deserializer = new StandardObjectDeserializer<Opens, OpensReader>(
                FIELD_PARSERS,
                new Supplier<OpensReader>() {
                    @Override
                    public OpensReader get() {
                        return new OpensReader();
                    }
                }
        );
    }

    @Override
    public Opens deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
