package com.urbanairship.api.channel.parse;

import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.DeviceType;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.channel.parse.ios.IosSettingsDeserializer;
import com.urbanairship.api.channel.parse.ios.QuietTimeDeserializer;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class ChannelObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Channels API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(IosSettings.class, new IosSettingsDeserializer());
        MODULE.addDeserializer(QuietTime.class, new QuietTimeDeserializer());
        MODULE.addDeserializer(ChannelView.class, new ChannelViewDeserializer());
        MODULE.addDeserializer(DeviceType.class, new DeviceTypeDeserializer());
        MODULE.addDeserializer(ChannelResponse.class, new ChannelsResponseDeserializer());


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

    private ChannelObjectMapper() { }
}
