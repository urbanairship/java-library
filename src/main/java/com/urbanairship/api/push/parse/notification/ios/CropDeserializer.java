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
import com.urbanairship.api.push.model.notification.ios.Crop;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class CropDeserializer extends JsonDeserializer<Crop> {

    private static final FieldParserRegistry<Crop, CropPayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<Crop, CropPayloadReader>(
            ImmutableMap.<String, FieldParser<CropPayloadReader>>builder()
                    .put("x", new FieldParser<CropPayloadReader>() {
                        @Override
                        public void parse(CropPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readX(json);
                        }
                    })
                    .put("y", new FieldParser<CropPayloadReader>() {
                        @Override
                        public void parse(CropPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readY(json);
                        }
                    })
                    .put("width", new FieldParser<CropPayloadReader>() {
                        @Override
                        public void parse(CropPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readWidth(json);
                        }
                    })
                    .put("height", new FieldParser<CropPayloadReader>() {
                        @Override
                        public void parse(CropPayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readHeight(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<Crop, ?> deserializer;

    public CropDeserializer() {
        deserializer = new StandardObjectDeserializer<Crop, CropPayloadReader>(
                FIELD_PARSERS,
                new Supplier<CropPayloadReader>() {
                    @Override
                    public CropPayloadReader get() {
                        return new CropPayloadReader();
                    }
                }
        );
    }

    public Crop deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}