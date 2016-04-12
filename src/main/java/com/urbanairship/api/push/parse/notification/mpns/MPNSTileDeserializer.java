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
import com.urbanairship.api.push.model.notification.mpns.MPNSTileData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class MPNSTileDeserializer extends JsonDeserializer<MPNSTileData> {

    private static final FieldParserRegistry<MPNSTileData, MPNSTileReader> FIELD_PARSERS = new MapFieldParserRegistry<MPNSTileData, MPNSTileReader>(
            ImmutableMap.<String, FieldParser<MPNSTileReader>>builder()
            .put("background_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBackgroundImage(json);
                    }
                })
            .put("count", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCount(json);
                    }
                })
            .put("title", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTitle(json);
                    }
                })
            .put("back_background_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBackBackgroundImage(json);
                    }
                })
            .put("back_title", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBackTitle(json);
                    }
                })
            .put("back_content", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBackContent(json);
                    }
                })
            // WP8 common fields
            .put("id", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readId(json);
                    }
                })
            .put("template", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readTemplate(json);
                    }
                })
            // WP8 FlipTile fields
            .put("wide_background_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideBackgroundImage(json);
                    }
                })
            .put("wide_back_background_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideBackBackgroundImage(json);
                    }
                })
            .put("wide_back_content", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideBackContent(json);
                    }
                })
            .put("small_background_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readSmallBackgroundImage(json);
                    }
                })
            // WP8 IconicTile fields
            .put("icon_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readIconImage(json);
                    }
                })
            .put("small_icon_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readSmallIconImage(json);
                    }
                })
            .put("background_color", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readBackgroundColor(json);
                    }
                })
            .put("wide_content_1", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideContent1(json);
                    }
                })
            .put("wide_content_2", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideContent2(json);
                    }
                })
            .put("wide_content_3", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readWideContent3(json);
                    }
                })
            // WP8 CycleTile fields
            .put("cycle_image", new FieldParser<MPNSTileReader>() {
                    public void parse(MPNSTileReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readCycleImage(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<MPNSTileData, ?> deserializer;

    public MPNSTileDeserializer() {
        deserializer = new StandardObjectDeserializer<MPNSTileData, MPNSTileReader>(
            FIELD_PARSERS,
            new Supplier<MPNSTileReader>() {
                @Override
                public MPNSTileReader get() {
                    return new MPNSTileReader();
                }
            }
        );
    }

    @Override
    public com.urbanairship.api.push.model.notification.mpns.MPNSTileData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
