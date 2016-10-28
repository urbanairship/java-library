/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.mpns.MPNSToastData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class MPNSToastDeserializer extends JsonDeserializer<MPNSToastData> {

    private static final FieldParserRegistry<MPNSToastData, MPNSToastReader> FIELD_PARSERS = new MapFieldParserRegistry<MPNSToastData, MPNSToastReader>(
            ImmutableMap.<String, FieldParser<MPNSToastReader>>builder()
            .put("text1", new FieldParser<MPNSToastReader>() {
                    public void parse(MPNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readText1(json);
                    }
                })
            .put("text2", new FieldParser<MPNSToastReader>() {
                    public void parse(MPNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readText2(json);
                    }
                })
            .put("param", new FieldParser<MPNSToastReader>() {
                    public void parse(MPNSToastReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readParam(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<MPNSToastData, ?> deserializer;

    public MPNSToastDeserializer() {
        deserializer = new StandardObjectDeserializer<MPNSToastData, MPNSToastReader>(
            FIELD_PARSERS,
            new Supplier<MPNSToastReader>() {
                @Override
                public MPNSToastReader get() {
                    return new MPNSToastReader();
                }
            }
        );
    }

    @Override
    public com.urbanairship.api.push.model.notification.mpns.MPNSToastData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
