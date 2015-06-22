/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.parse;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.channel.information.model.ios.IosSettings;
import com.urbanairship.api.channel.information.util.Constants;
import com.urbanairship.api.channel.information.util.MapUtil;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.BooleanFieldDeserializer;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.ListOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public final class ChannelViewReader implements JsonObjectReader<ChannelView> {
    private final ChannelView.Builder builder;

    public ChannelViewReader() {
        builder = ChannelView.newBuilder();
    }

    public void readChannelId(JsonParser jsonParser) throws IOException {
        builder.setChannelId(StringFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.CHANNEL_ID));
    }

    public void readDeviceType(JsonParser jsonParser) throws IOException {
        builder.setDeviceType(jsonParser.readValueAs(DeviceType.class));
    }

    public void readInstalled(JsonParser jsonParser) throws IOException {
        builder.setInstalled(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.INSTALLED));
    }

    public void readOptIn(JsonParser jsonParser) throws IOException {
        builder.setOptedIn(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.OPT_IN));
    }

    public void readBackground(JsonParser jsonParser) throws IOException {
        builder.setBackground(BooleanFieldDeserializer.INSTANCE.deserialize(jsonParser, Constants.BACKGROUND));
    }

    public void readPushAddress(JsonParser jsonParser) throws IOException {
        builder.setPushAddress(jsonParser.readValueAs(String.class));
    }

    public void readCreated(JsonParser jsonParser) throws IOException {
        builder.setCreatedMillis(jsonParser.readValueAs(DateTime.class).getMillis());
    }

    public void readLastRegistration(JsonParser jsonParser) throws IOException {
        Long mills;
        DateTime dt = jsonParser.readValueAs(DateTime.class);
        if (dt == null) {
            mills = null;
        } else {
            mills = dt.getMillis();
        }
        builder.setLastRegistrationMillis(mills);
    }

    public void readAlias(JsonParser jsonParser) throws IOException {
        builder.setAlias(jsonParser.readValueAs(String.class));
    }

    public void readTags(JsonParser jsonParser) throws IOException {
        builder.addAllTags(ListOfStringsDeserializer.INSTANCE.deserialize(jsonParser, Constants.TAGS));
    }

    public void readTagGroups(JsonParser jsonParser) throws IOException {
        Map<String, Set<String>> mutableTagGroups = jsonParser.readValueAs(new TypeReference<Map<String, Set<String>>>() {});
        ImmutableMap<String, ImmutableSet<String>> tagGroups = MapUtil.immutableMapConverter(mutableTagGroups);
        builder.addAllTagGroups(tagGroups);
    }

    public void readIosSettings(JsonParser jsonParser) throws IOException {
        builder.setIosSettings(jsonParser.readValueAs(IosSettings.class));
    }

    @Override
    public ChannelView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

}
