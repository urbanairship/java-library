/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.Crop;

import java.io.IOException;

public class CropDeserializer extends JsonDeserializer<Crop> {

    private static final FieldParserRegistry<Crop, CropPayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<Crop, CropPayloadReader>(
            ImmutableMap.<String, FieldParser<CropPayloadReader>>builder()
                    .put("x", (reader, json, context) -> reader.readX(json))
                    .put("y", (reader, json, context) -> reader.readY(json))
                    .put("width", (reader, json, context) -> reader.readWidth(json))
                    .put("height", (reader, json, context) -> reader.readHeight(json))
                    .build()
    );

    private final StandardObjectDeserializer<Crop, ?> deserializer;

    public CropDeserializer() {
        deserializer = new StandardObjectDeserializer<Crop, CropPayloadReader>(
                FIELD_PARSERS,
                () -> new CropPayloadReader()
        );
    }

    @Override
    public Crop deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}