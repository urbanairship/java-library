/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class APIListAllSegmentsResponseDeserializer extends JsonDeserializer<APIListAllSegmentsResponse> {

    private static final FieldParserRegistry<APIListAllSegmentsResponse, APIListAllSegmentsResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListAllSegmentsResponse, APIListAllSegmentsResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListAllSegmentsResponseReader>>builder()
                            .put("next_page", new FieldParser<APIListAllSegmentsResponseReader>() {
                                @Override
                                public void parse(APIListAllSegmentsResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readNextPage(jsonParser);
                                }
                            })
                            .put("segments", new FieldParser<APIListAllSegmentsResponseReader>() {
                                @Override
                                public void parse(APIListAllSegmentsResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readSegments(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIListAllSegmentsResponse, ?> deserializer;

    public APIListAllSegmentsResponseDeserializer(){
        this.deserializer = new StandardObjectDeserializer<APIListAllSegmentsResponse, APIListAllSegmentsResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListAllSegmentsResponseReader>() {
                    @Override
                    public APIListAllSegmentsResponseReader get() {
                        return new APIListAllSegmentsResponseReader();
                    }
                }
        );
    }
    @Override
    public APIListAllSegmentsResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
