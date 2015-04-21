/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.adm;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class ADMDevicePayloadReader implements JsonObjectReader<ADMDevicePayload> {

    private ADMDevicePayload.Builder builder = ADMDevicePayload.newBuilder();

    public ADMDevicePayloadReader() {
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readConsolidationKey(JsonParser parser) throws IOException {
        builder.setConsolidationKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "consolidation_key"));
    }

    public void readExpiresAfter(JsonParser parser) throws IOException {
        builder.setExpiresAfter(parser.readValueAs(PushExpiry.class));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readInteractive(JsonParser parser) throws IOException {
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
