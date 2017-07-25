package com.urbanairship.api.channel.parse.open;

import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class OpenChannelReader implements JsonObjectReader<OpenChannel> {
    private final OpenChannel.Builder builder;

    public OpenChannelReader() {
        this.builder = OpenChannel.newBuilder();
    }

    public void readPlatformName(JsonParser parser) throws IOException {
        builder.setOpenPlatformName(StringFieldDeserializer.INSTANCE.deserialize(parser, Constants.OPEN_PLATFORM_NAME));
    }

    public void readOldAddress(JsonParser parser) throws IOException {
        builder.setOldAddress(StringFieldDeserializer.INSTANCE.deserialize(parser, Constants.OLD_ADDRESS));
    }

    public void readIdentifiers(JsonParser parser) throws IOException {
        builder.addAllIdentifierEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, Constants.IDENTIFIERS));
    }

    @Override
    public OpenChannel validateAndBuild() throws IOException {
        try{
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
