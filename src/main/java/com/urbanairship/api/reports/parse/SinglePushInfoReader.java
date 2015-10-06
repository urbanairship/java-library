/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;
import java.util.UUID;

public final class SinglePushInfoReader implements JsonObjectReader<SinglePushInfoResponse> {

    private final SinglePushInfoResponse.Builder builder;

    public SinglePushInfoReader() {
        this.builder = SinglePushInfoResponse.newBuilder();
    }

    public void readPushUUID(JsonParser jsonParser) throws IOException {
        builder.setPushUUID(jsonParser.readValueAs(UUID.class));
    }

    public void readDirectResponses(JsonParser jsonParser) throws IOException {
        builder.setDirectResponses(jsonParser.readValueAs(int.class));
    }

    public void readSends(JsonParser jsonParser) throws IOException {
        builder.setSends(jsonParser.readValueAs(int.class));
    }

    public void readPushType(JsonParser jsonParser) throws IOException {
        builder.setPushType(jsonParser.readValueAs(SinglePushInfoResponse.PushType.class));
    }

    public void readPushTime(JsonParser jsonParser) throws IOException {
        builder.setPushTime(jsonParser.readValueAs(String.class));
    }

    public void readGroupID(JsonParser jsonParser) throws IOException {
        builder.setGroupID(jsonParser.readValueAs(UUID.class));
    }

    @Override
    public SinglePushInfoResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
