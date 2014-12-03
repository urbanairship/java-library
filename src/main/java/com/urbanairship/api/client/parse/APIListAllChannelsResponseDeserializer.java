/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class APIListAllChannelsResponseDeserializer extends JsonDeserializer<APIListAllChannelsResponse> {

    private static final FieldParserRegistry<APIListAllChannelsResponse, APIListAllChannelsResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<APIListAllChannelsResponse, APIListAllChannelsResponseReader>(
                    ImmutableMap.<String, FieldParser<APIListAllChannelsResponseReader>>builder()
                    .put("next_page", new FieldParser<APIListAllChannelsResponseReader>() {
                        @Override
                        public void parse(APIListAllChannelsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readNextPage(jsonParser);
                        }
                    })
                    .put("channels", new FieldParser<APIListAllChannelsResponseReader>() {
                        @Override
                        public void parse(APIListAllChannelsResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readChannelObjects(jsonParser);
                        }
                    })
                    .build()
            );

    private final StandardObjectDeserializer<APIListAllChannelsResponse, ?> deserializer;

    public APIListAllChannelsResponseDeserializer(){
        this.deserializer = new StandardObjectDeserializer<APIListAllChannelsResponse, APIListAllChannelsResponseReader>(
                FIELD_PARSER,
                new Supplier<APIListAllChannelsResponseReader>() {
                    @Override
                    public APIListAllChannelsResponseReader get() {
                        return new APIListAllChannelsResponseReader();
                    }
                }
        );
    }

    @Override
    public APIListAllChannelsResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
