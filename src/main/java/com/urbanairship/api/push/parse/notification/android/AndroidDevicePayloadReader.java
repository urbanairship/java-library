package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class AndroidDevicePayloadReader implements JsonObjectReader<AndroidDevicePayload> {

    private AndroidDevicePayload.Builder builder = AndroidDevicePayload.newBuilder();

    public AndroidDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readCollapseKey(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setCollapseKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "collapse_key"));
    }

    public void readTimeToLive(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setTimeToLive(IntFieldDeserializer.INSTANCE.deserialize(parser, "time_to_live"));
    }

    public void readDelayWhileIdle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setDelayWhileIdle(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "delay_while_idle"));
    }

    public void readExtra(JsonParser parser, DeserializationContext context) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    @Override
    public AndroidDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
