/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.location.model.LocationResponse;
import com.urbanairship.api.location.model.LocationView;
import com.urbanairship.api.push.parse.PushObjectMapper;

public class LocationObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Location API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(LocationView.class, new LocationViewDeserializer());
        MODULE.addDeserializer(LocationResponse.class, new LocationResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(new JodaModule());
        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }
}
