package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class ChannelUninstallResponseDeserializer extends JsonDeserializer<ChannelUninstallResponse> {
    private static final FieldParserRegistry<ChannelUninstallResponse, ChannelUninstallResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<ChannelUninstallResponse, ChannelUninstallResponseReader>(
            ImmutableMap.<String, FieldParser<ChannelUninstallResponseReader>>builder()
                    .put("ok", new FieldParser<ChannelUninstallResponseReader>() {
                        @Override
                        public void parse(ChannelUninstallResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readOk(jsonParser);
                        }
                    })
                    .put("error", new FieldParser<ChannelUninstallResponseReader>() {
                        @Override
                        public void parse(ChannelUninstallResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readError(jsonParser);
                        }
                    })
                    .put("details", new FieldParser<ChannelUninstallResponseReader>() {
                        @Override
                        public void parse(ChannelUninstallResponseReader reader, JsonParser jsonParser, DeserializationContext context) throws IOException {
                            reader.readErrorDetails(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<ChannelUninstallResponse, ?> deserializer;

    public ChannelUninstallResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ChannelUninstallResponse, ChannelUninstallResponseReader>(
                FIELD_PARSERS,
                new Supplier<ChannelUninstallResponseReader>() {
                    @Override
                    public ChannelUninstallResponseReader get() {
                        return new ChannelUninstallResponseReader();
                    }
                }
        );
    }

    @Override
    public ChannelUninstallResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
