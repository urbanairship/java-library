/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public final class ChannelsResponseDeserializer extends JsonDeserializer<ChannelResponse> {

    private static final FieldParserRegistry<ChannelResponse, ChannelsResponseReader> FIELD_PARSER =
            new MapFieldParserRegistry<ChannelResponse, ChannelsResponseReader>(
                    ImmutableMap.<String, FieldParser<ChannelsResponseReader>>builder()
                            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
                            .put("next_page", (reader, jsonParser, deserializationContext) -> reader.readNextPage(jsonParser))
                            .put("channel", (reader, jsonParser, deserializationContext) -> reader.readChannelObject(jsonParser))
                            .put("channels", (reader, jsonParser, deserializationContext) -> reader.readChannelObjects(jsonParser))
                            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
                            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<ChannelResponse, ?> deserializer;

    public ChannelsResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<ChannelResponse, ChannelsResponseReader>(
                FIELD_PARSER,
                () -> new ChannelsResponseReader()
        );
    }

    @Override
    public ChannelResponse deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}