/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListView;

public class StaticListsObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Static Lists API Module");

    static {
        MODULE.addDeserializer(StaticListView.class, new StaticListViewDeserializer());
        MODULE.addDeserializer(StaticListListingResponse.class, new StaticListListingResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(new JodaModule());
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private StaticListsObjectMapper() {
    }

}
