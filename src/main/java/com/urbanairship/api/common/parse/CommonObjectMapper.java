/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;

public class CommonObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Common API Module",
                                                                new Version(1, 0, 0, null));
    static {
        MODULE
            .addSerializer(DateTime.class, new DateTimeSerializer())
            .addDeserializer(DateTime.class, new DateTimeDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private CommonObjectMapper() { }
}
