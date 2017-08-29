/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.reports.model.PushInfoResponse;

import java.io.IOException;
import java.util.UUID;

public final class PushInfoResponseReader implements JsonObjectReader<PushInfoResponse> {

    private final PushInfoResponse.Builder builder;

    public PushInfoResponseReader() {
        this.builder = PushInfoResponse.newBuilder();
    }

    public void readPushUUID(JsonParser jsonParser) throws IOException {
        builder.setPushId(jsonParser.readValueAs(UUID.class));
    }

    public void readDirectResponses(JsonParser jsonParser) throws IOException {
        builder.setDirectResponses(jsonParser.readValueAs(int.class));
    }

    public void readSends(JsonParser jsonParser) throws IOException {
        builder.setSends(jsonParser.readValueAs(int.class));
    }

    public void readPushType(JsonParser jsonParser) throws IOException {
        builder.setPushType(jsonParser.readValueAs(PushInfoResponse.PushType.class));
    }

    public void readPushTime(JsonParser jsonParser) throws IOException {
        builder.setPushTime(jsonParser.readValueAs(String.class));
    }

    public void readGroupID(JsonParser jsonParser) throws IOException {
        builder.setGroupId(jsonParser.readValueAs(UUID.class));
    }

    @Override
    public PushInfoResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
