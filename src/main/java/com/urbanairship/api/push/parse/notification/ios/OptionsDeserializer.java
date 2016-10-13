package com.urbanairship.api.push.parse.notification.ios;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.Options;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;


public class OptionsDeserializer extends JsonDeserializer<Options> {

    private static final FieldParserRegistry<Options, OptionsPayloadReader> FIELD_PARSER = new MapFieldParserRegistry<Options, OptionsPayloadReader>(
            ImmutableMap.<String, FieldParser<OptionsPayloadReader>>builder()
            .put("time", new FieldParser<OptionsPayloadReader>() {
                @Override
                public void parse(OptionsPayloadReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                    reader.readTime(parser);
                }
            }).put("crop", new FieldParser<OptionsPayloadReader>() {
                @Override
                public void parse(OptionsPayloadReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                    reader.readCrop(parser, context);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<Options, ?> deserializer;

    public OptionsDeserializer() {
        deserializer = new StandardObjectDeserializer<Options, OptionsPayloadReader>(
                FIELD_PARSER,
                new Supplier<OptionsPayloadReader>() {
                    @Override
                    public OptionsPayloadReader get() {
                        return new OptionsPayloadReader();
                    }
                }
        );
    }

    public Options deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
