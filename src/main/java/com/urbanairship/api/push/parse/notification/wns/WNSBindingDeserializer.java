/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;

import java.io.IOException;

public class WNSBindingDeserializer extends JsonDeserializer<WNSBinding> {

    private static final FieldParserRegistry<WNSBinding, WNSBindingReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSBinding, WNSBindingReader>(
            ImmutableMap.<String, FieldParser<WNSBindingReader>>builder()
            .put("template", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTemplate(json);
                    }
                })
            .put("version", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readVersion(json);
                    }
                })
            .put("fallback", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readFallback(json);
                    }
                })
            .put("lang", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readLang(json);
                    }
                })
            .put("base_uri", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBaseUri(json);
                    }
                })
            .put("add_image_query", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAddImageQuery(json);
                    }
                })
            .put("image", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readImage(json);
                    }
                })
            .put("text", new FieldParser<WNSBindingReader>() {
                    public void parse(WNSBindingReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readText(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<WNSBinding, ?> deserializer;

    public WNSBindingDeserializer() {
        deserializer = new StandardObjectDeserializer<WNSBinding, WNSBindingReader>(
            FIELD_PARSERS,
            new Supplier<WNSBindingReader>() {
                @Override
                public WNSBindingReader get() {
                    return new WNSBindingReader();
                }
            }
        );
    }

    @Override
    public WNSBinding deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
