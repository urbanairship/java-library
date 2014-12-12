/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.PushOptions;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class PushOptionsDeserializer extends JsonDeserializer<PushOptions> {

    private static final FieldParserRegistry<PushOptions, PushOptionsReader> FIELD_PARSERS = new MapFieldParserRegistry<PushOptions, PushOptionsReader>(ImmutableMap.<String, FieldParser<PushOptionsReader>>builder().build());

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
