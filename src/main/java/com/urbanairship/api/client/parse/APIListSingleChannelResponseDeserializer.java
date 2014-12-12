/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class APIListSingleChannelResponseDeserializer extends JsonDeserializer<APIListSingleChannelResponse> {

    private static final FieldParserRegistry<APIListSingleChannelResponse, APIListSingleChannelResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListSingleChannelResponse, APIListSingleChannelResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListSingleChannelResponseReader>>builder()
                            .put("channel", new FieldParser<APIListSingleChannelResponseReader>() {
                                @Override
                                public void parse(APIListSingleChannelResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readChannelObject(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<APIListSingleChannelResponse, ?> deserializer;

    public APIListSingleChannelResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<APIListSingleChannelResponse, APIListSingleChannelResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListSingleChannelResponseReader>() {
                    @Override
                    public APIListSingleChannelResponseReader get() {
                        return new APIListSingleChannelResponseReader();
                    }
                }
        );
    }

    @Override
    public APIListSingleChannelResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
