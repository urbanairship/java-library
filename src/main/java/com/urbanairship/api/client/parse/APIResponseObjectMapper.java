/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.channel.information.model.ios.IosSettings;
import com.urbanairship.api.channel.information.model.ios.QuietTime;
import com.urbanairship.api.channel.information.parse.ChannelViewDeserializer;
import com.urbanairship.api.channel.information.parse.DeviceTypeDeserializer;
import com.urbanairship.api.channel.information.parse.ios.IosSettingsDeserializer;
import com.urbanairship.api.channel.information.parse.ios.QuietTimeDeserializer;
import com.urbanairship.api.client.*;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.location.model.Location;
import com.urbanairship.api.location.parse.LocationDeserializer;
import com.urbanairship.api.client.model.*;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.push.parse.PushPayloadDeserializer;
import com.urbanairship.api.reports.model.AppStats;
import com.urbanairship.api.reports.parse.AppStatsDeserializer;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.schedule.parse.ScheduleDeserializer;
import com.urbanairship.api.schedule.parse.SchedulePayloadDeserializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/*
This is where object serialization and deserialization are registered with
Jackson to enable object parsing.
 */
public final class APIResponseObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1,0,0,null));

    static {
        MODULE.addDeserializer(APIPushResponse.class, new APIPushResponseDeserializer());
        MODULE.addDeserializer(APIErrorDetails.Location.class, new StreamLocationDeserializer());
        MODULE.addDeserializer(APIErrorDetails.class, new APIErrorDetailsDeserializer());
        MODULE.addDeserializer(APIError.class, new APIErrorDeserializer());
        MODULE.addDeserializer(APIScheduleResponse.class, new APIScheduleResponseDeserializer());
        MODULE.addDeserializer(APIListAllSchedulesResponse.class, new APIListAllSchedulesResponseDeserializer());
        MODULE.addDeserializer(Schedule.class, ScheduleDeserializer.INSTANCE);
        MODULE.addDeserializer(SchedulePayload.class, SchedulePayloadDeserializer.INSTANCE);
        MODULE.addDeserializer(PushPayload.class, new PushPayloadDeserializer());
        MODULE.addDeserializer(APIListTagsResponse.class, new APIListTagsResponseDeserializer());
        MODULE.addDeserializer(SegmentInformation.class, new SegmentInformationDeserializer());
        MODULE.addDeserializer(APIListAllSegmentsResponse.class, new APIListAllSegmentsResponseDeserializer());
        MODULE.addDeserializer(IosSettings.class, new IosSettingsDeserializer());
        MODULE.addDeserializer(QuietTime.class, new QuietTimeDeserializer());
        MODULE.addDeserializer(ChannelView.class, new ChannelViewDeserializer());
        MODULE.addDeserializer(DeviceType.class, new DeviceTypeDeserializer());
        MODULE.addDeserializer(APIListAllChannelsResponse.class, new APIListAllChannelsResponseDeserializer());
        MODULE.addDeserializer(AppStats.class, new AppStatsDeserializer());
        MODULE.addDeserializer(Location.class, new LocationDeserializer());
        MODULE.addDeserializer(APILocationResponse.class, new APILocationResponseDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    public static SimpleModule getModule(){
        return MODULE;
    }

    public static ObjectMapper getInstance(){
        return MAPPER;
    }
    private APIResponseObjectMapper (){}

}
