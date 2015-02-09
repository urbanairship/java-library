/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class ADMDevicePayloadReader implements JsonObjectReader<ADMDevicePayload> {

    private ADMDevicePayload.Builder builder = ADMDevicePayload.newBuilder();

    public ADMDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readConsolidationKey(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setConsolidationKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "consolidation_key"));
    }

    public void readExpiresAfter(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setExpiresAfter(IntFieldDeserializer.INSTANCE.deserialize(parser, "expires_after"));
    }

    public void readExtra(JsonParser parser, DeserializationContext context) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readInteractive(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    @Override
    public ADMDevicePayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
