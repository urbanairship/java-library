/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.IntFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.ListOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;

import java.io.IOException;

public class IOSAlertDataReader implements JsonObjectReader<IOSAlertData> {

    private IOSAlertData.Builder builder = IOSAlertData.newBuilder();

    public IOSAlertDataReader() {
    }

    public void readBody(JsonParser parser) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readActionLocKey(JsonParser parser) throws IOException {
        builder.setActionLocKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "action-loc-key"));
    }

    public void readLocKey(JsonParser parser) throws IOException {
        builder.setLocKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "loc-key"));
    }

    public void readLocArgs(JsonParser parser) throws IOException {
        builder.setLocArgs(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "loc-args"));
    }

    public void readLaunchImage(JsonParser parser) throws IOException {
        builder.setLaunchImage(StringFieldDeserializer.INSTANCE.deserialize(parser, "launch-image"));
    }

    public void readSummaryArg(JsonParser parser) throws  IOException {
        builder.setSummaryArg(StringFieldDeserializer.INSTANCE.deserialize(parser, "summary-arg"));
    }

    public void readSummaryArgCount(JsonParser parser) throws  IOException {
        builder.setSummaryArgCount(IntFieldDeserializer.INSTANCE.deserialize(parser, "summary-arg-count"));
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readTitleLocArgs(JsonParser parser) throws IOException {
        builder.setTitleLocArgs(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "title-loc-args"));
    }

    public void readTitleLocKey(JsonParser parser) throws IOException {
        builder.setTitleLocKey(StringFieldDeserializer.INSTANCE.deserialize(parser, "title-loc-key"));
    }

    @Override
        public IOSAlertData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
