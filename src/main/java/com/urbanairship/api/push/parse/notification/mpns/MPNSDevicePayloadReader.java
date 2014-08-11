package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSPush;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;

public class MPNSDevicePayloadReader implements JsonObjectReader<MPNSDevicePayload> {

    private MPNSPush.Builder builder;
    private final MPNSToastDeserializer toastDS;
    private final MPNSTileDeserializer tileDS;
    private Optional<String> alert = Optional.absent();

    public MPNSDevicePayloadReader(MPNSToastDeserializer toastDS, MPNSTileDeserializer tileDS) {
        this.toastDS = toastDS;
        this.tileDS = tileDS;
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        alert = Optional.fromNullable(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readToast(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = MPNSPush.newBuilder();
        }
        builder.setToast(toastDS.deserialize(parser, context));
        builder.setType(MPNSPush.Type.TOAST);
    }

    public void readTile(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = MPNSPush.newBuilder();
        }
        builder.setTile(tileDS.deserialize(parser, context));
        builder.setType(MPNSPush.Type.TILE);
    }

    public void readBatchingInterval(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBatchingInterval(MPNSBatchingIntervalDeserializer.INSTANCE.deserialize(parser, context));
    }

    @Override
    public MPNSDevicePayload validateAndBuild() throws IOException {
        try {
            if (!alert.isPresent() && builder == null) {
                throw new APIParsingException("'mpns' override cannot be empty.");
            }
            if (alert.isPresent() && builder != null) {
                throw new APIParsingException("'mpns' override must provide exactly one of 'alert', 'tile', or 'toast'.");
            }
            MPNSPush push = null;
            if (builder != null) {
                push = builder.build();
            }
            return MPNSDevicePayload.newBuilder()
                .setBody(push)
                .setAlert(alert.isPresent() ? alert.get() : null)
                .build();
        } catch (RuntimeException e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
