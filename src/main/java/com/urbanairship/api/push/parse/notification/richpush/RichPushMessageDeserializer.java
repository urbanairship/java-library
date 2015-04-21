/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class RichPushMessageDeserializer extends JsonDeserializer<RichPushMessage> {

    private static final FieldParserRegistry<RichPushMessage, RichPushMessageReader> FIELD_PARSERS = new MapFieldParserRegistry<RichPushMessage, RichPushMessageReader>(
            ImmutableMap.<String, FieldParser<RichPushMessageReader>>builder()
            .put("title", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTitle(json);
                    }
                })
            .put("body", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBody(json);
                    }
                })
            .put("content-type", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentType(json);
                    }
                })
            .put("content_type", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentType(json);
                    }
                })
            .put("content-encoding", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentEncoding(json);
                    }
                })
            .put("content_encoding", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentEncoding(json);
                    }
                })
            .put("extra", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json);
                    }
                })
            .put("expiry", new FieldParser<RichPushMessageReader>() {
                    public void parse(RichPushMessageReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExpiry(json);
                    }
            })
            .build()
            );

    private final StandardObjectDeserializer<RichPushMessage, ?> deserializer;

    public RichPushMessageDeserializer() {
        deserializer = new StandardObjectDeserializer<RichPushMessage, RichPushMessageReader>(
            FIELD_PARSERS,
            new Supplier<RichPushMessageReader>() {
                @Override
                public RichPushMessageReader get() {
                    return new RichPushMessageReader();
                }
            }
        );
    }

    @Override
    public RichPushMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
