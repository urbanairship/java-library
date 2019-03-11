package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;

import java.io.IOException;
import com.google.common.base.Supplier;

public class IOSSoundDataDeserializer extends JsonDeserializer<IOSSoundData> {

    private static final FieldParserRegistry<IOSSoundData, IOSSoundDataReader> FIELD_PARSER = new MapFieldParserRegistry<IOSSoundData, IOSSoundDataReader>(
            ImmutableMap.<String, FieldParser<IOSSoundDataReader>>builder()
            .put("critical", new FieldParser<IOSSoundDataReader>() {
                @Override
                public void parse(IOSSoundDataReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readCritical(jsonParser, deserializationContext);
                }
            })
            .put("volume", new FieldParser<IOSSoundDataReader>() {
                @Override
                public void parse(IOSSoundDataReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readVolume(jsonParser, deserializationContext);
                }
            })
            .put("name", new FieldParser<IOSSoundDataReader>() {
                @Override
                public void parse(IOSSoundDataReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readName(jsonParser, deserializationContext);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<IOSSoundData, ?> deserializer;

    public IOSSoundDataDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSSoundData, IOSSoundDataReader>(
            FIELD_PARSER,
            new Supplier<IOSSoundDataReader>() {
                @Override
                public IOSSoundDataReader get() {
                    return new IOSSoundDataReader();
                }
            }
        );
    }

    @Override
    public IOSSoundData deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonParseException {
        return deserializer.deserialize(jp, context);
    }
}
