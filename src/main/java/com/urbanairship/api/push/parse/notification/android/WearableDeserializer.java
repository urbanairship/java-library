/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.android.Wearable;

import java.io.IOException;

public class WearableDeserializer extends JsonDeserializer<Wearable> {
    private static final FieldParserRegistry<Wearable, WearableReader> FIELD_PARSERS = new MapFieldParserRegistry<Wearable, WearableReader>(
            ImmutableMap.<String, FieldParser<WearableReader>>builder()
                    .put("background_image", new FieldParser<WearableReader>() {
                        public void parse(WearableReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readBackgroundImage(json);
                        }
                    })
                    .put("interactive", new FieldParser<WearableReader>() {
                        public void parse(WearableReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readInteractive(json);
                        }
                    })
                    .put("extra_pages", new FieldParser<WearableReader>() {
                        public void parse(WearableReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readExtraPages(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<Wearable, ?> deserializer;

    public WearableDeserializer() {
        deserializer = new StandardObjectDeserializer<Wearable, WearableReader>(
                FIELD_PARSERS,
                new Supplier<WearableReader>() {
                    @Override
                    public WearableReader get() {
                        return new WearableReader();
                    }
                }
        );
    }

    @Override
    public Wearable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }


}
