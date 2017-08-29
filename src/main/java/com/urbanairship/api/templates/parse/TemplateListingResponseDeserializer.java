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
import com.urbanairship.api.templates.model.TemplateListingResponse;

import java.io.IOException;

public class TemplateListingResponseDeserializer extends JsonDeserializer<TemplateListingResponse> {
    private static final FieldParserRegistry<TemplateListingResponse, TemplateListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateListingResponse, TemplateListingResponseReader>(
                    ImmutableMap.<String, FieldParser<TemplateListingResponseReader>>builder()
                            .put("ok", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("template", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTemplate(jsonParser);
                                }
                            })
                            .put("templates", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readAllTemplates(jsonParser);
                                }
                            })
                            .put("count", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readCount(jsonParser);
                                }
                            })
                            .put("total_count", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTotalCount(jsonParser);
                                }
                            })
                            .put("next_page", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("prev_page", new FieldParser<TemplateListingResponseReader>() {
                                @Override
                                public void parse(TemplateListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readPrevPage(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<TemplateListingResponse, ?> deserializer;

    public TemplateListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateListingResponse, TemplateListingResponseReader>(
                FIELD_PARSER,
                new Supplier<TemplateListingResponseReader>() {
                    @Override
                    public TemplateListingResponseReader get() {
                        return new TemplateListingResponseReader();
                    }
                }
        );
    }

    @Override
    public TemplateListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
