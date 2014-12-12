/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class APIListSingleChannelResponseReader implements JsonObjectReader<APIListSingleChannelResponse> {

    private final APIListSingleChannelResponse.Builder builder;

    public APIListSingleChannelResponseReader() {
        this.builder = APIListSingleChannelResponse.newBuilder();
    }

    public void readChannelObject(JsonParser jsonParser) throws IOException {
        builder.setChannelObject(jsonParser.readValueAs(ChannelView.class));
    }

    @Override
    public APIListSingleChannelResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
