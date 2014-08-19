/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.*;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.push.parse.PushPayloadDeserializer;
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
public class APIResponseObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1,0,0,null));

    static {
        MODULE.addDeserializer(APIPushResponse.class, new APIPushResponseDeserializer());
        MODULE.addDeserializer(APIErrorDetails.Location.class, new LocationDeserializer());
        MODULE.addDeserializer(APIErrorDetails.class, new APIErrorDetailsDeserializer());
        MODULE.addDeserializer(APIError.class, new APIErrorDeserializer());
        MODULE.addDeserializer(APIScheduleResponse.class, new APIScheduleResponseDeserializer());
        MODULE.addDeserializer(APIListScheduleResponse.class, new APIListScheduleResponseDeserializer());
        MODULE.addDeserializer(Schedule.class, ScheduleDeserializer.INSTANCE);
        MODULE.addDeserializer(SchedulePayload.class, SchedulePayloadDeserializer.INSTANCE);
        MODULE.addDeserializer(PushPayload.class, new PushPayloadDeserializer());
        MODULE.addDeserializer(APIListTagsResponse.class, new APIListTagsResponseDeserializer());

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
