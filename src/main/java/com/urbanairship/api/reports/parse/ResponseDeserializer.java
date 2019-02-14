package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.common.collect.ImmutableMap;
import com.google.common.base.Supplier;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.Response;

import java.io.IOException;

public class ResponseDeserializer extends JsonDeserializer<Response> {
    private static final FieldParserRegistry<Response, ResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<Response, ResponseReader>(
                    ImmutableMap.<String, FieldParser<ResponseReader>>builder()
                            .put("android", new FieldParser<ResponseReader>() {
                                @Override
                                public void parse(ResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDeviceStats(jsonParser);
                                }
                            })
                            .put("date", new FieldParser<ResponseReader>() {
                                @Override
                                public void parse(ResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readDate(jsonParser);
                                }
                            })
                            .put("ios", new FieldParser<ResponseReader>() {
                                @Override
                                public void parse(ResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDeviceStats(jsonParser);
                                }
                            })
                            .build()
            );


    private final StandardObjectDeserializer<Response, ?> deserializer;

    public ResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<Response, ResponseReader>(
                FIELD_PARSERS,
                new Supplier<ResponseReader>() {
                    @Override
                    public ResponseReader get() {
                        return new ResponseReader();
                    }
                }
        );
    }

    public Response deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
