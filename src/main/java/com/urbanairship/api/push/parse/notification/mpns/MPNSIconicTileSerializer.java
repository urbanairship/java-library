package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSIconicTileData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSIconicTileSerializer extends JsonSerializer<MPNSIconicTileData> {
    @Override
    public void serialize(MPNSIconicTileData tile, JsonGenerator jgen, SerializerProvider provider) throws IOException {
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

        // WP8 IconicTile fields
        if (tile.getIconImage().isPresent()) {
            jgen.writeStringField("icon_image", tile.getIconImage().get());
        }
        if (tile.getSmallIconImage().isPresent()) {
            jgen.writeStringField("small_icon_image", tile.getSmallIconImage().get());
        }
        if (tile.getBackgroundColor().isPresent()) {
            jgen.writeStringField("background_color", tile.getBackgroundColor().get());
        }
        if (tile.getWideContent1().isPresent()) {
            jgen.writeStringField("wide_content_1", tile.getWideContent1().get());
        }
        if (tile.getWideContent2().isPresent()) {
            jgen.writeStringField("wide_content_2", tile.getWideContent3().get());
        }
        if (tile.getWideContent3().isPresent()) {
            jgen.writeStringField("wide_content_2", tile.getWideContent3().get());
        }

        jgen.writeEndObject();
    }
}
