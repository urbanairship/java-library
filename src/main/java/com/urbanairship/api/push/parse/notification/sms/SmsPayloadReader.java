package com.urbanairship.api.push.parse.notification.sms;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.sms.SmsPayload;

import java.io.IOException;

public class SmsPayloadReader implements JsonObjectReader<SmsPayload> {

    private SmsPayload.Builder builder;

    public SmsPayloadReader() {
        this.builder = SmsPayload.newBuilder();
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(StringFieldDeserializer.INSTANCE.deserialize(parser, "alert"));
    }

    public void readPushExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(PushExpiry.class));
    }

    @Override
    public SmsPayload validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
