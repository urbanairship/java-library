/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.urbanairship.api.client.RequestError;
import com.urbanairship.api.client.RequestErrorDetails;
import com.urbanairship.api.push.parse.PushObjectMapper;

/*
This is where object serialization and deserialization are registered with
Jackson to enable object parsing.
 */
public final class RequestErrorObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(RequestErrorDetails.Location.class, new StreamLocationDeserializer());
        MODULE.addDeserializer(RequestErrorDetails.class, new RequestErrorDetailsDeserializer());
        MODULE.addDeserializer(RequestError.class, new RequestErrorDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    private RequestErrorObjectMapper() {
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

}
