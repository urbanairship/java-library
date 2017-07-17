/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.experiments.model.Variant;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class VariantDeserializer extends JsonDeserializer<Variant> {

    private static final FieldParserRegistry<Variant, VariantReader> FIELD_PARSERS = new MapFieldParserRegistry<Variant, VariantReader>(

            ImmutableMap.<String, FieldParser<VariantReader>>builder()
                    .put("name", new FieldParser<VariantReader>() {
                            @Override
                            public void parse(VariantReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                reader.readName(jsonParser);}
                    })
                    .put("description", new FieldParser<VariantReader>() {
                            @Override
                            public void parse(VariantReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                reader.readDescription(jsonParser);
                            }
                    })
                    .put("weight", new FieldParser<VariantReader>() {
                            @Override
                            public void parse(VariantReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                reader.readWeight(jsonParser);
                            }
                    })
                    .put("push", new FieldParser<VariantReader>() {
                            @Override
                            public void parse(VariantReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                reader.readVariantPushPayload(jsonParser);
                            }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<Variant, ?> deserializer;

    public VariantDeserializer() {
        deserializer = new StandardObjectDeserializer<Variant, VariantReader>(
                FIELD_PARSERS,
                new Supplier<VariantReader>() {
                    @Override
                    public VariantReader get() {
                        return new VariantReader();
                    }
                }
        );
    }

    @Override
    public Variant deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
