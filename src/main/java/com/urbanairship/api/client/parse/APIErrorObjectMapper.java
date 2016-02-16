/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIError;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/*
This is where object serialization and deserialization are registered with
Jackson to enable object parsing.
 */
public final class APIErrorObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(APIErrorDetails.Location.class, new StreamLocationDeserializer());
        MODULE.addDeserializer(APIErrorDetails.class, new APIErrorDetailsDeserializer());
        MODULE.addDeserializer(APIError.class, new APIErrorDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    private APIErrorObjectMapper() {
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

}
