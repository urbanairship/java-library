/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.templates.model.TemplateView;

import java.io.IOException;

public final class TemplateViewDeserializer extends JsonDeserializer<TemplateView> {
    private static final FieldParserRegistry<TemplateView, TemplateViewReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateView, TemplateViewReader>(
                    ImmutableMap.<String, FieldParser<TemplateViewReader>>builder()
                    .put("id", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readId(jsonParser);
                        }
                    })
                    .put("created_at", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readCreatedAt(jsonParser);
                        }
                    })
                    .put("modified_at", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readModifiedAt(jsonParser);
                        }
                    })
                    .put("last_used", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readLastUsed(jsonParser);
                        }
                    })
                    .put("name", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readName(jsonParser);
                        }
                    })
                    .put("description", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readDescription(jsonParser);
                        }
                    })
                    .put("variables", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readVariables(jsonParser);
                        }
                    })
                    .put("push", new FieldParser<TemplateViewReader>() {
                        @Override
                        public void parse(TemplateViewReader reader,
                                          JsonParser jsonParser,
                                          DeserializationContext deserializationContext) throws IOException {
                            reader.readPartialPush(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<TemplateView, ?> deserializer;

    public TemplateViewDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateView, TemplateViewReader>(
                FIELD_PARSER,
                new Supplier<TemplateViewReader>() {
                    @Override
                    public TemplateViewReader get() {
                        return new TemplateViewReader();
                    }
                }
        );
    }

    @Override
    public TemplateView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
