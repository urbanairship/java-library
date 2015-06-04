/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class APIListTagsResponseDeserializer extends JsonDeserializer<APIListTagsResponse> {

    private static final FieldParserRegistry<APIListTagsResponse, APIListTagsResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListTagsResponse, APIListTagsResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListTagsResponseReader>>builder()
                            .put("tags", new FieldParser<APIListTagsResponseReader>() {
                                @Override
                                public void parse(APIListTagsResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readTags(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIListTagsResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public APIListTagsResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIListTagsResponse, APIListTagsResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListTagsResponseReader>() {
                    @Override
                    public APIListTagsResponseReader get() {
                        return new APIListTagsResponseReader();
                    }
                }
        );
    }

    @Override
    public APIListTagsResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
