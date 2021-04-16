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
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;

import java.io.IOException;

public class IOSDevicePayloadDeserializer extends JsonDeserializer<IOSDevicePayload> {

    private static final FieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader> FIELD_PARSERS = new MapFieldParserRegistry<IOSDevicePayload, IOSDevicePayloadReader>(
            ImmutableMap.<String, FieldParser<IOSDevicePayloadReader>>builder()
            .put("alert", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readAlert(json, context);
                    }
                })
            .put("badge", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBadge(json);
                    }
                })
            .put("content-available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json);
                    }
                })
            .put("content_available", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readContentAvailable(json);
                    }
                })
            .put("extra", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readExtra(json);
                    }
                })
            .put("category", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCategory(json);
                    }
                })
            .put("interactive", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readInteractive(json);
                    }
                })
            .put("expiry", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readExpiry(json);
                    }
            })
            .put("priority", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readPriority(json);
                    }
            })
            .put("title", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTitle(json);
                    }
                })
            .put("subtitle", new FieldParser<IOSDevicePayloadReader>() {
                    public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readSubtitle(json);
                    }
            })
            .put("mutable_content", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readMutableContent(json);
                }
            })
            .put("sound", new FieldParser<IOSDevicePayloadReader>() {
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readSoundData(json, context);
                }
            })
            .put("media_attachment", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readMediaAttachment(json, context);
                }
            })
            .put("collapse_id", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readCollapseId(json);
                }
            })
            .put("thread_id", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readThreadId(json);
                }
            })
            .put("actions", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readActions(json);
                }
            })
            .put("target_content_id", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readTargetContentId(json);
                }
            })
            .put("template", new FieldParser<IOSDevicePayloadReader>() {
                @Override
                public void parse(IOSDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readIosTemplate(json);
                }
            })
            .build()
            );

    private final StandardObjectDeserializer<IOSDevicePayload, ?> deserializer;

    public IOSDevicePayloadDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSDevicePayload, IOSDevicePayloadReader>(
            FIELD_PARSERS,
            new Supplier<IOSDevicePayloadReader>() {
                @Override
                public IOSDevicePayloadReader get() {
                    return new IOSDevicePayloadReader();
                }
            }
        );
    }

    @Override
    public IOSDevicePayload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
