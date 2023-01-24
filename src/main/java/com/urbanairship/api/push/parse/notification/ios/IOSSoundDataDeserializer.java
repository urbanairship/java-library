package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;

import java.io.IOException;

public class IOSSoundDataDeserializer extends JsonDeserializer<IOSSoundData> {

    private static final FieldParserRegistry<IOSSoundData, IOSSoundDataReader> FIELD_PARSER = new MapFieldParserRegistry<IOSSoundData, IOSSoundDataReader>(
            ImmutableMap.<String, FieldParser<IOSSoundDataReader>>builder()
            .put("critical", (reader, jsonParser, deserializationContext) -> reader.readCritical(jsonParser))
            .put("volume", (reader, jsonParser, deserializationContext) -> reader.readVolume(jsonParser))
            .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
            .build()
    );

    private final StandardObjectDeserializer<IOSSoundData, ?> deserializer;

    public IOSSoundDataDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSSoundData, IOSSoundDataReader>(
            FIELD_PARSER,
                () -> new IOSSoundDataReader()
        );
    }

    @Override
    public IOSSoundData deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        try {
            JsonToken token = jp.getCurrentToken();
            switch (token) {

                case VALUE_STRING:
                    String name = jp.getText();
                    return IOSSoundData.newBuilder()
                            .setName(name)
                            .build();

                case START_OBJECT:
                    return deserializer.deserialize(jp, context);

                default:
                    APIParsingException.raise(String.format("Unexpected sound token '%s'", token.name()), jp);
            }
        }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            APIParsingException.raise(e.getMessage(), jp);
        }
        return null;
    }
}
