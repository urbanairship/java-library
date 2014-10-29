/*
 * Copyright 2014 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;


import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public final class APIListAllChannelsResponseReader implements JsonObjectReader<APIListAllChannelsResponse> {

    private final APIListAllChannelsResponse.Builder builder;

    public APIListAllChannelsResponseReader() { this.builder = APIListAllChannelsResponse.newBuilder(); }

    public void readNextPage(JsonParser jsonParser) throws IOException { builder.setNextPage(jsonParser.readValueAs(String.class)); }
    public void readChannelObjects(JsonParser jsonParser) throws IOException { builder.addAllChannels((List<ChannelView>) jsonParser.readValueAs(new TypeReference<List<ChannelView>>() {
    })); }

    @Override
    public APIListAllChannelsResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
