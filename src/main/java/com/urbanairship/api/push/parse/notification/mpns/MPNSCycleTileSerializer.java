/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSCycleTileData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSCycleTileSerializer extends JsonSerializer<MPNSCycleTileData> {
    @Override
    public void serialize(MPNSCycleTileData tile, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        // Common fields
        if (tile.getCount().isPresent()) {
            jgen.writeNumberField("count", tile.getCount().get());
        }
        if (tile.getTitle().isPresent()) {
            jgen.writeStringField("title", tile.getTitle().get());
        }
        if (tile.getId().isPresent()) {
            jgen.writeStringField("id", tile.getId().get());
        }
        jgen.writeStringField("template", tile.getTemplate());

        // WP8 CycleTile fields
        if (tile.getSmallBackgroundImage().isPresent()) {
            jgen.writeStringField("small_background_image", tile.getSmallBackgroundImage().get());
        }

        if (tile.getImageCount() > 0) {
            jgen.writeArrayFieldStart("cycle_image");
            for (String image : tile.getImages().get()) {
                jgen.writeString(image);
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }
}
