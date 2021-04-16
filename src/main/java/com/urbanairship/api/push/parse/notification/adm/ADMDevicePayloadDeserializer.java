/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;

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
            .put("actions", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readActions(json);
                }
            })
            .put("icon", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readIcon(json);
                }
            })
            .put("icon_color", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readIconColor(json);
                }
            })
            .put("notification_channel", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readNotificationChannel(json);
                }
            })
            .put("notification_tag", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readNotificationTag(json);
                }
            })
            .put("sound", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readSound(json);
                }
            })
            .put("summary", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readSummary(json);
                }
            })
            .put("title", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readTitle(json);
                }
            })
            .put("style", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readStyle(json);
                }
            })
            .put("template", new FieldParser<ADMDevicePayloadReader>() {
                @Override
                public void parse(ADMDevicePayloadReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readTemplate(json);
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
