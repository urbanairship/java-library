/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class APIReportsListingResponseDeserializer extends JsonDeserializer<APIReportsPushListingResponse> {

    private static final FieldParserRegistry<APIReportsPushListingResponse, APIReportsListingResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIReportsPushListingResponse, APIReportsListingResponseReader>(
                    ImmutableMap.<String, FieldParser<APIReportsListingResponseReader>>builder()
                            .put("next_page", new FieldParser<APIReportsListingResponseReader>() {
                                @Override
                                public void parse(APIReportsListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("pushes", new FieldParser<APIReportsListingResponseReader>() {
                                @Override
                                public void parse(APIReportsListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushInfoResponses(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIReportsPushListingResponse, ?> deserializer;

    public APIReportsListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIReportsPushListingResponse, APIReportsListingResponseReader>(
                FIELD_PARSER,
                new Supplier<APIReportsListingResponseReader>() {
                    @Override
                    public APIReportsListingResponseReader get() {
                        return new APIReportsListingResponseReader();
                    }
                }
        );
    }

    @Override
    public APIReportsPushListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
