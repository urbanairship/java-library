/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class AttributeListsViewDeserializer extends JsonDeserializer<AttributeListsView> {
    private static final FieldParserRegistry<AttributeListsView, AttributeListsViewReader> FIELD_PARSERS =
            new MapFieldParserRegistry<AttributeListsView, AttributeListsViewReader>(
                    ImmutableMap.<String, FieldParser<AttributeListsViewReader>>builder()
                            .put("ok", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("name", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readName(jsonParser);
                                }
                            })
                            .put("description", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDescription(jsonParser);
                                }
                            })
                            .put("extra", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readExtras(jsonParser);
                                }
                            })
                            .put("created", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readCreated(jsonParser);
                                }
                            })
                            .put("last_updated", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readLastUpdated(jsonParser);
                                }
                            })
                            .put("channel_count", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readChannelCount(jsonParser);
                                }
                            })
                            .put("error_path", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorPath(jsonParser);
                                }
                            })
                            .put("status", new FieldParser<AttributeListsViewReader>() {
                                @Override
                                public void parse(AttributeListsViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readStatus(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<AttributeListsView, ?> deserializer;

    public AttributeListsViewDeserializer() {
        deserializer = new StandardObjectDeserializer<AttributeListsView, AttributeListsViewReader>(
                FIELD_PARSERS,
                new Supplier<AttributeListsViewReader>() {
                    @Override
                    public AttributeListsViewReader get() {
                        return new AttributeListsViewReader();
                    }
                }
        );
    }

    @Override
    public AttributeListsView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
