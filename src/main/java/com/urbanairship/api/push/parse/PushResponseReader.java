/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.PushResponse;

import java.io.IOException;
import java.util.List;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class PushResponseReader implements JsonObjectReader<PushResponse> {

    private final PushResponse.Builder builder;

    public PushResponseReader() {
        this.builder = PushResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readPushIds(JsonParser jsonParser) throws IOException {
        List<String> list =
                jsonParser.readValueAs(new TypeReference<List<String>>() {
                });
        builder.addAllPushIds(list);
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readMessageIds(JsonParser jsonParser) throws IOException {
        List<String> list =
            jsonParser.readValueAs(new TypeReference<List<String>>() {
            });
        builder.addAllMessageIds(list);
    }

    public void readContentUrls(JsonParser jsonParser) throws IOException {
        List<String> list =
            jsonParser.readValueAs(new TypeReference<List<String>>() {
            });
        builder.addAllContentUrls(list);
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public PushResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
