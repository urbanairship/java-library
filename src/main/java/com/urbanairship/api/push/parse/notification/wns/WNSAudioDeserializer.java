/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSAudioData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
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
