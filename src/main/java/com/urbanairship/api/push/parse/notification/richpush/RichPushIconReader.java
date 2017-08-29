/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;

import java.io.IOException;

public class RichPushIconReader implements JsonObjectReader<RichPushIcon> {

    private RichPushIcon.Builder builder = RichPushIcon.newBuilder();

    public RichPushIconReader() {
    }

    public void readListIcon(JsonParser parser) throws IOException {
        builder.setListIcon(StringFieldDeserializer.INSTANCE.deserialize(parser, "list_icon"));
    }

    @Override
    public RichPushIcon validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
