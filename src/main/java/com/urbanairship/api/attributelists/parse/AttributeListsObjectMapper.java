/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.attributelists.model.AttributeListsView;

public class AttributeListsObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Attribute Lists API Module");

    static {
        MODULE.addDeserializer(AttributeListsView.class, new AttributeListsViewDeserializer());
        MODULE.addDeserializer(AttributeListsListingResponse.class, new AttributeListsListingResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(new JodaModule());
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private AttributeListsObjectMapper() {
    }

}
