/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.PushListingResponse;

import java.io.IOException;

public final class PushListingResponseDeserializer extends JsonDeserializer<PushListingResponse> {

    private static final FieldParserRegistry<PushListingResponse, PushListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<PushListingResponse, PushListingResponseReader>(
                    ImmutableMap.<String, FieldParser<PushListingResponseReader>>builder()
                            .put("next_page", new FieldParser<PushListingResponseReader>() {
                                @Override
                                public void parse(PushListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("pushes", new FieldParser<PushListingResponseReader>() {
                                @Override
                                public void parse(PushListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException
                                {
                                    reader.readPushInfoObjects(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<PushListingResponse, ?> deserializer;

    public PushListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<PushListingResponse, PushListingResponseReader>(
                FIELD_PARSER,
                new Supplier<PushListingResponseReader>() {
                    @Override
                    public PushListingResponseReader get() {
                        return new PushListingResponseReader();
                    }
                }
        );
    }

    @Override
    public PushListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
