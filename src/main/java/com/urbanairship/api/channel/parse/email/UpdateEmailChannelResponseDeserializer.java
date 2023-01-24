package com.urbanairship.api.channel.parse.email;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class  UpdateEmailChannelResponseDeserializer extends JsonDeserializer<EmailChannelResponse> {

    private static final FieldParserRegistry<EmailChannelResponse, UpdateEmailChannelResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<>(
            ImmutableMap.<String, FieldParser<UpdateEmailChannelResponseReader>>builder()
                    .put("ok", (reader, parser, context) -> reader.readOk(parser))
                    .put("channel_id", (reader, parser, context) -> reader.readChannelId(parser))
                    .build()
    );

    private final StandardObjectDeserializer<EmailChannelResponse, ?> deserializer;

    public UpdateEmailChannelResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<EmailChannelResponse, UpdateEmailChannelResponseReader>(
                FIELD_PARSERS,
                () -> new UpdateEmailChannelResponseReader()
        );
    }

    @Override
    public EmailChannelResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
