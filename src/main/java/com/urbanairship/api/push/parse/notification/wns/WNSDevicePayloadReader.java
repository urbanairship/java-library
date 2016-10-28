/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class WNSDevicePayloadReader implements JsonObjectReader<WNSDevicePayload> {

    private final WNSToastDeserializer toastDS;
    private final WNSTileDeserializer tileDS;
    private final WNSBadgeDeserializer badgeDS;

    private WNSPush.Builder builder;
    private Optional<String> alert = Optional.absent();

    public WNSDevicePayloadReader(WNSToastDeserializer toastDS, WNSTileDeserializer tileDS, WNSBadgeDeserializer badgeDS) {
        this.toastDS = toastDS;
        this.tileDS = tileDS;
        this.badgeDS = badgeDS;
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        alert = Optional.fromNullable(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readToast(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = WNSPush.newBuilder();
        }
        builder.setToast(toastDS.deserialize(parser, context));
        builder.setType(WNSPush.Type.TOAST);
    }

    public void readTile(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = WNSPush.newBuilder();
        }
        builder.setTile(tileDS.deserialize(parser, context));
        builder.setType(WNSPush.Type.TILE);
    }

    public void readBadge(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = WNSPush.newBuilder();
        }
        builder.setBadge(badgeDS.deserialize(parser, context));
        builder.setType(WNSPush.Type.BADGE);
    }

    public void readCachePolicy(JsonParser parser, DeserializationContext context) throws IOException {
        if (builder == null) {
            builder = WNSPush.newBuilder();
        }
        builder.setCachePolicy(WNSCachePolicyDeserializer.INSTANCE.deserialize(parser, context));
    }

    @Override
    public WNSDevicePayload validateAndBuild() throws IOException {
        try {
            if (!alert.isPresent() && builder == null) {
                throw new APIParsingException("'wns' override cannot be empty.");
            }
            if (alert.isPresent() && builder != null) {
                throw new APIParsingException("'wns' override must provide exactly one of 'alert', 'tile', 'toast', or 'badge'.");
            }
            WNSPush push = null;
            if (builder != null) {
                push = builder.build();
            }
            return WNSDevicePayload.newBuilder()
                .setBody(push)
                .setAlert(alert.isPresent() ? alert.get() : null)
                .build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
