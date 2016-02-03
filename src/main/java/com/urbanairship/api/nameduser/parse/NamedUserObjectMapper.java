/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.parse;

import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.channel.parse.ChannelViewDeserializer;
import com.urbanairship.api.channel.parse.DeviceTypeDeserializer;
import com.urbanairship.api.channel.parse.ios.IosSettingsDeserializer;
import com.urbanairship.api.channel.parse.ios.QuietTimeDeserializer;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class NamedUserObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Named User API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(IosSettings.class, new IosSettingsDeserializer());
        MODULE.addDeserializer(QuietTime.class, new QuietTimeDeserializer());
        MODULE.addDeserializer(ChannelView.class, new ChannelViewDeserializer());
        MODULE.addDeserializer(ChannelType.class, new DeviceTypeDeserializer());
        MODULE.addDeserializer(NamedUserView.class, new NamedUserViewDeserializer());
        MODULE.addDeserializer(NamedUserListingResponse.class, new NamedUserlListingResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private NamedUserObjectMapper() {}
}
