/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.mpns.MPNSToastData;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class MPNSToastReader implements JsonObjectReader<MPNSToastData> {

    private final MPNSToastData.Builder builder;

    public MPNSToastReader() {
        this.builder = MPNSToastData.newBuilder();
    }

    public void readText1(JsonParser parser) throws IOException {
        builder.setText1(StringFieldDeserializer.INSTANCE.deserialize(parser, "text1"));
    }

    public void readText2(JsonParser parser) throws IOException {
        builder.setText2(StringFieldDeserializer.INSTANCE.deserialize(parser, "text2"));
    }

    public void readParam(JsonParser parser) throws IOException {
        builder.setParam(StringFieldDeserializer.INSTANCE.deserialize(parser, "param"));
    }

    @Override
    public com.urbanairship.api.push.model.notification.mpns.MPNSToastData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException("Toast was missing required elements.", e);
        }
    }
}
