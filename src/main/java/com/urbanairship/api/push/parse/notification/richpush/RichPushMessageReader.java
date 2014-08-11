package com.urbanairship.api.push.parse.notification.richpush;

import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class RichPushMessageReader implements JsonObjectReader<RichPushMessage> {

    private RichPushMessage.Builder builder = RichPushMessage.newBuilder();

    public RichPushMessageReader() {
    }

    public void readTitle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readBody(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readContentType(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setContentType(StringFieldDeserializer.INSTANCE.deserialize(parser, "content_type"));
    }

    public void readContentEncoding(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setContentEncoding(StringFieldDeserializer.INSTANCE.deserialize(parser, "content_encoding"));
    }

    public void readExtra(JsonParser parser, DeserializationContext context) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    @Override
    public RichPushMessage validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
