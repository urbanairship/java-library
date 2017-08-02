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
import com.urbanairship.api.experiments.model.VariantPushPayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class VariantPushPayloadDeserializer extends JsonDeserializer<VariantPushPayload> {

    private static final FieldParserRegistry<VariantPushPayload, VariantPushPayloadReader> FIELD_PARSERS =
            new MapFieldParserRegistry<VariantPushPayload, VariantPushPayloadReader>(
                    ImmutableMap.<String, FieldParser<VariantPushPayloadReader>>builder()
                            .put("notification", new FieldParser<VariantPushPayloadReader>() {
                                @Override
                                public void parse(VariantPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNotification(jsonParser);
                                }
                            })
                            .put("options", new FieldParser<VariantPushPayloadReader>() {
                                @Override
                                public void parse(VariantPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushOptions(jsonParser);
                                }
                            })
                            .put("in_app", new FieldParser<VariantPushPayloadReader>() {
                                @Override
                                public void parse(VariantPushPayloadReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readInApp(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<VariantPushPayload, ?> deserializer;

    public VariantPushPayloadDeserializer() {
        this.deserializer = new StandardObjectDeserializer<VariantPushPayload, VariantPushPayloadReader>(
                FIELD_PARSERS,
                new Supplier<VariantPushPayloadReader>() {
                    @Override
                    public VariantPushPayloadReader get() {
                        return new VariantPushPayloadReader();
                    }
                }
        );
    }

    @Override
    public VariantPushPayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
