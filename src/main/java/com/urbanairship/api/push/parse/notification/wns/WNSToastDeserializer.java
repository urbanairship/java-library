/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSToastDeserializer extends JsonDeserializer<WNSToastData> {

    private static final FieldParserRegistry<WNSToastData, WNSToastReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSToastData, WNSToastReader>(
            ImmutableMap.<String, FieldParser<WNSToastReader>>builder()
            .put("binding", new FieldParser<WNSToastReader>() {
                    public void parse(WNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBinding(json, context);
                    }
                })
            .put("duration", new FieldParser<WNSToastReader>() {
                    public void parse(WNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readDuration(json, context);
                    }
                })
            .put("audio", new FieldParser<WNSToastReader>() {
                    public void parse(WNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAudio(json, context);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<WNSToastData, ?> deserializer;

    public WNSToastDeserializer(final WNSBindingDeserializer bindingDS, final WNSAudioDeserializer audioDS) {
        deserializer = new StandardObjectDeserializer<WNSToastData, WNSToastReader>(
            FIELD_PARSERS,
            new Supplier<WNSToastReader>() {
                @Override
                public WNSToastReader get() {
                    return new WNSToastReader(bindingDS, audioDS);
                }
            }
        );
    }

    @Override
    public WNSToastData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
