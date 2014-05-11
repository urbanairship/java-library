/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.Options;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class OptionsDeserializer extends JsonDeserializer<Options> {

    private static final FieldParserRegistry<Options, OptionsReader> FIELD_PARSERS = new MapFieldParserRegistry<Options, OptionsReader>(
            ImmutableMap.<String, FieldParser<OptionsReader>>builder()
                    .put("expiry", new FieldParser<OptionsReader>() {
                        @Override
                        public void parse(OptionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readExpiry(jsonParser);
                        }
                    })
                    .build()
    );

    public static final OptionsDeserializer INSTANCE = new OptionsDeserializer();

    private final StandardObjectDeserializer<Options, ?> deserializer;

    public OptionsDeserializer() {
        deserializer = new StandardObjectDeserializer<Options, OptionsReader>(
                FIELD_PARSERS,
                new Supplier<OptionsReader>() {
                    @Override
                    public OptionsReader get() {
                        return new OptionsReader();
                    }
                }
        );
    }

    @Override
    public Options deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
