package com.urbanairship.api.push.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;

public class PushPayloadReader implements JsonObjectReader<PushPayload> {

    private final PushPayload.Builder builder;

    public PushPayloadReader() {
        this.builder = PushPayload.newBuilder();
    }

    public void readAudience(JsonParser jsonParser) throws IOException {
        builder.setAudience(jsonParser.readValueAs(Selector.class));
    }

    public void readNotification(JsonParser jsonParser) throws IOException {
        builder.setNotification(jsonParser.readValueAs(Notification.class));
    }

    public void readMessage(JsonParser jsonParser) throws IOException {
        builder.setMessage(jsonParser.readValueAs(RichPushMessage.class));
    }

    public void readOptions(JsonParser parser) throws IOException {
        builder.setPushOptions(parser.readValueAs(PushOptions.class));
    }

    public void readDeviceTypes(JsonParser parser) throws IOException {
        builder.setPlatforms(parser.readValueAs(PlatformData.class));
    }

    @Override
    public PushPayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
