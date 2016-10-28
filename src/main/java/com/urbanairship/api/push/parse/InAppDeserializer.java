package com.urbanairship.api.push.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.InApp;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class InAppDeserializer extends JsonDeserializer<InApp> {

    private static final FieldParserRegistry<InApp, InAppReader> FIELD_PARSERS =
            new MapFieldParserRegistry<InApp, InAppReader>(
                    ImmutableMap.<String, FieldParser<InAppReader>>builder()
                            .put("alert", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readAlert(jsonParser);
                                }
                            })
                            .put("expiry", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readExpiry(jsonParser);
                                }
                            })
                            .put("display", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDisplay(jsonParser);
                                }
                            })
                            .put("actions", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readActions(jsonParser);
                                }
                            })
                            .put("interactive", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readInteractive(jsonParser);
                                }
                            })
                            .put("extra", new FieldParser<InAppReader>() {
                                @Override
                                public void parse(InAppReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readAllExtras(jsonParser);
                                }
                            })
                            .build());

    private final StandardObjectDeserializer<InApp, ?> deserializer;

    public InAppDeserializer() {
        deserializer = new StandardObjectDeserializer<InApp, InAppReader>(
                FIELD_PARSERS,
                new Supplier<InAppReader>() {
                    @Override
                    public InAppReader get() {
                        return new InAppReader();
                    }
                }
        );
    }

    @Override
    public InApp deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }

}
