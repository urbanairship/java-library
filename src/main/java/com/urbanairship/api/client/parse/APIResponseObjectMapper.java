package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIError;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.client.APIScheduleResponse;
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
