/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.model.APIScheduleResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

/*
Readers are the part of the deserialization process that actually builds and
return an object.
 */
public final class APIScheduleResponseReader implements JsonObjectReader<APIScheduleResponse> {

    private final APIScheduleResponse.Builder builder;

    public APIScheduleResponseReader() {
        this.builder = APIScheduleResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readScheduleIds(JsonParser jsonParser) throws IOException {
        List<String> list =
                jsonParser.readValueAs(new TypeReference<List<String>>() {
                });
        builder.addAllScheduleUrls(list);
    }

    public void readSchedulePayloads(JsonParser jsonParser) throws IOException {
        builder.addAllSchedulePayload((List<SchedulePayload>) jsonParser.readValueAs(new TypeReference<List<SchedulePayload>>() {
        }));
    }

    @Override
    public APIScheduleResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}