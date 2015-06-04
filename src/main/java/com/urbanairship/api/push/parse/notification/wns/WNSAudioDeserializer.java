/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSAudioDeserializer extends JsonDeserializer<WNSAudioData> {

    private static final FieldParserRegistry<WNSAudioData, WNSAudioReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSAudioData, WNSAudioReader>(
            ImmutableMap.<String, FieldParser<WNSAudioReader>>builder()
            .put("sound", new FieldParser<WNSAudioReader>() {
                    public void parse(WNSAudioReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readSound(json, context);
                    }
                })
            .put("loop", new FieldParser<WNSAudioReader>() {
                    public void parse(WNSAudioReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLoop(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<WNSAudioData, ?> deserializer;

    public WNSAudioDeserializer() {
        deserializer = new StandardObjectDeserializer<WNSAudioData, WNSAudioReader>(
            FIELD_PARSERS,
            new Supplier<WNSAudioReader>() {
                @Override
                public WNSAudioReader get() {
                    return new WNSAudioReader();
                }
            }
        );
    }

    @Override
    public WNSAudioData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
