/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ADMDevicePayloadDeserializer extends JsonDeserializer<ADMDevicePayload> {

    private static final FieldParserRegistry<ADMDevicePayload, ADMDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<ADMDevicePayload, ADMDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<ADMDevicePayloadReader>>builder()
            .put("alert", new FieldParser<ADMDevicePayloadReader>() {
                    public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json);
                    }
                })
            .put("consolidation_key", new FieldParser<ADMDevicePayloadReader>() {
                    public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readConsolidationKey(json);
                    }
                })
            .put("expires_after", new FieldParser<ADMDevicePayloadReader>() {
                    public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExpiresAfter(json);
                    }
                })
            .put("extra", new FieldParser<ADMDevicePayloadReader>() {
                    public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json);
                    }
                })
            .put("interactive", new FieldParser<ADMDevicePayloadReader>() {
                    public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readInteractive(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<ADMDevicePayload, ?> deserializer;

    public ADMDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<ADMDevicePayload, ADMDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<ADMDevicePayloadReader>() {
                @Override
                public ADMDevicePayloadReader get() {
                    return new ADMDevicePayloadReader();
                }
            }
        );
    }

    @Override
    public ADMDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
