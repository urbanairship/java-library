/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
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
import com.urbanairship.api.templates.model.TemplateResponse;

import java.io.IOException;

public class TemplateResponseDeserializer extends JsonDeserializer<TemplateResponse> {
    private static final FieldParserRegistry<TemplateResponse, TemplateResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateResponse, TemplateResponseReader>(
                    ImmutableMap.<String, FieldParser<TemplateResponseReader>>builder()
                            .put("ok", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("operation_id", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("template_id", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTemplateId(jsonParser);
                                }
                            })
                            .put("push_ids", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.addAllPushIds(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("details", new FieldParser<TemplateResponseReader>() {
                                @Override
                                public void parse(TemplateResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorDetails(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<TemplateResponse, ?> deserializer;

    public TemplateResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateResponse, TemplateResponseReader>(
                FIELD_PARSER,
                new Supplier<TemplateResponseReader>() {
                    @Override
                    public TemplateResponseReader get() {
                        return new TemplateResponseReader();
                    }
                }
        );
    }

    @Override
    public TemplateResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
