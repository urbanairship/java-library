/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSFlipTileData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSFlipTileSerializer extends JsonSerializer<MPNSFlipTileData> {
    @Override
    public void serialize(MPNSFlipTileData tile, JsonGenerator jgen, SerializerProvider provider) throws IOException {
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

        // WP8 FlipTile fields
        if (tile.getWideBackgroundImage().isPresent()) {
            jgen.writeStringField("wide_background_image", tile.getWideBackgroundImage().get());
        }
        if (tile.getWideBackBackgroundImage().isPresent()) {
            jgen.writeStringField("wide_back_background_image", tile.getWideBackBackgroundImage().get());
        }
        if (tile.getWideBackContent().isPresent()) {
            jgen.writeStringField("wide_back_content", tile.getWideBackContent().get());
        }
        if (tile.getSmallBackgroundImage().isPresent()) {
            jgen.writeStringField("small_background_image", tile.getSmallBackgroundImage().get());
        }

        jgen.writeEndObject();
    }
}
