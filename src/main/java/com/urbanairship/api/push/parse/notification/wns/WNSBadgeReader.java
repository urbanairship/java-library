package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBadgeData;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.DeserializationContext;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;

public class WNSBadgeReader implements JsonObjectReader<WNSBadgeData> {

    private final WNSBadgeData.Builder builder;

    public WNSBadgeReader() {
        this.builder = WNSBadgeData.newBuilder();
    }

    public void readValue(JsonParser parser) throws IOException {
        builder.setValue(IntFieldDeserializer.INSTANCE.deserialize(parser, "value"));
    }

    public void readGlyph(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setGlyph(WNSGlyphDeserializer.INSTANCE.deserialize(parser, context));
    }

    @Override
    public WNSBadgeData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
