package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSCycleTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSFlipTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSIconicTileData;
import com.urbanairship.api.push.model.notification.mpns.MPNSTileData;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;
import java.util.Set;


/*
Ugh, this is such a hack right now, because the fields can appear
in any order, and we don't know which tile type it is until we read
the "template" field (which could be anywhere because it's a streaming
parser). I need a way to read the "template" field first - may have to
resort to parsing a JsonNode tree structure and then traversing that.
 */

public class MPNSTileReader implements JsonObjectReader<MPNSTileData> {

    private final MPNSCycleTileData.Builder cycleBuilder;
    private final MPNSFlipTileData.Builder flipBuilder;
    private final MPNSIconicTileData.Builder iconicBuilder;
    private String template;

    public MPNSTileReader() {
        cycleBuilder = MPNSCycleTileData.newBuilder();
        flipBuilder = MPNSFlipTileData.newBuilder();
        iconicBuilder = MPNSIconicTileData.newBuilder();
    }

    // WP8 common fields
    public void readId(JsonParser parser) throws IOException {
        String id = StringFieldDeserializer.INSTANCE.deserialize(parser, "id");
        cycleBuilder.setId(id);
        flipBuilder.setId(id);
        iconicBuilder.setId(id);
    }

    public void readTitle(JsonParser parser) throws IOException {
        String title = StringFieldDeserializer.INSTANCE.deserialize(parser, "title");
        cycleBuilder.setTitle(title);
        flipBuilder.setTitle(title);
        iconicBuilder.setTitle(title);
    }

    public void readCount(JsonParser parser) throws IOException {
        int count = IntFieldDeserializer.INSTANCE.deserialize(parser, "count");
        cycleBuilder.setCount(count);
        flipBuilder.setCount(count);
        iconicBuilder.setCount(count);
    }

    public void readSmallBackgroundImage(JsonParser parser) throws IOException {
        String image = StringFieldDeserializer.INSTANCE.deserialize(parser, "small_background_image");
        flipBuilder.setSmallBackgroundImage(image);
        cycleBuilder.setSmallBackgroundImage(image);
    }

    public void readTemplate(JsonParser parser) throws IOException {
        template = StringFieldDeserializer.INSTANCE.deserialize(parser, "template");
    }

    // WP8 FlipTile fields
    public void readBackContent(JsonParser parser) throws IOException {
        flipBuilder.setBackContent(StringFieldDeserializer.INSTANCE.deserialize(parser, "back_content"));
    }

    public void readBackBackgroundImage(JsonParser parser) throws IOException {
        flipBuilder.setBackBackgroundImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "back_background_image"));
    }

    public void readBackgroundImage(JsonParser parser) throws IOException {
        flipBuilder.setBackgroundImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "background_image"));
    }

    public void readBackTitle(JsonParser parser) throws IOException {
        flipBuilder.setBackTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "back_title"));
    }

    public void readWideBackgroundImage(JsonParser parser) throws IOException {
        flipBuilder.setWideBackgroundImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_background_image"));
    }

    public void readWideBackBackgroundImage(JsonParser parser) throws IOException {
        flipBuilder.setWideBackBackgroundImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_back_background_image"));
    }

    public void readWideBackContent(JsonParser parser) throws IOException {
        flipBuilder.setWideBackContent(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_back_content"));
    }

    // WP8 IconicTile fields
    public void readIconImage(JsonParser parser) throws IOException {
        iconicBuilder.setIconImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "icon_image"));
    }
    public void readSmallIconImage(JsonParser parser) throws IOException {
        iconicBuilder.setSmallIconImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "small_icon_image"));
    }
    public void readBackgroundColor(JsonParser parser) throws IOException {
        iconicBuilder.setBackgroundColor(StringFieldDeserializer.INSTANCE.deserialize(parser, "background_color"));
    }
    public void readWideContent1(JsonParser parser) throws IOException {
        iconicBuilder.setWideContent1(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_content_1"));
    }
    public void readWideContent2(JsonParser parser) throws IOException {
        iconicBuilder.setWideContent2(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_content_2"));
    }
    public void readWideContent3(JsonParser parser) throws IOException {
        iconicBuilder.setWideContent3(StringFieldDeserializer.INSTANCE.deserialize(parser, "wide_content_3"));
    }

    // WP8 CycleTile fields
    public void readCycleImage(JsonParser parser) throws IOException {
        cycleBuilder.addAllImages(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "cycle_image"));
    }

    @Override
    public com.urbanairship.api.push.model.notification.mpns.MPNSTileData validateAndBuild() throws IOException {
        try {
            if (template.equals(Constants.TILE_CYCLE)) {
                return cycleBuilder.build();
            } else if (template.equals(Constants.TILE_FLIP)) {
                  return flipBuilder.build();
            } else if (template.equals(Constants.TILE_ICONIC)) {
                return iconicBuilder.build();
            } else {
                  throw new APIParsingException(String.format("Invalid tile template '%s'", template));
            }
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
