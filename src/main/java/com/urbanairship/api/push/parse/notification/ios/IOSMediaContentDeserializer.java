/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class IOSMediaContentDeserializer extends JsonDeserializer<IOSMediaContent> {

    private static final FieldParserRegistry<IOSMediaContent, IOSMediaContentReader> FIELD_PARSER = new MapFieldParserRegistry<IOSMediaContent, IOSMediaContentReader>(
            ImmutableMap.<String, FieldParser<IOSMediaContentReader>>builder()
            .put("body", new FieldParser<IOSMediaContentReader>() {
                @Override
                public void parse(IOSMediaContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readBody(json, context);
                }
            })
            .put("title", new FieldParser<IOSMediaContentReader>() {
                @Override
                public void parse(IOSMediaContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readTitle(json, context);
                }
            })
            .put("subtitle", new FieldParser<IOSMediaContentReader>() {
                @Override
                public void parse(IOSMediaContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readSubtitle(json, context);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<IOSMediaContent, ?> deserializer;

    public IOSMediaContentDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSMediaContent, IOSMediaContentReader>(
                FIELD_PARSER,
                new Supplier<IOSMediaContentReader>() {
                    @Override
                    public IOSMediaContentReader get() {
                        return new IOSMediaContentReader();
                    }
                }
        );
    }

    @Override
    public IOSMediaContent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, ctxt);
    }
}
