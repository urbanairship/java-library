/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateListingResponse;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.model.TemplateSelector;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;

public class TemplatesObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Templates API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(TemplateView.class, new TemplateViewDeserializer());
        MODULE.addSerializer(TemplateView.class, new TemplateViewSerializer());
        MODULE.addDeserializer(TemplateListingResponse.class, new TemplateListingResponseDeserializer());
        MODULE.addDeserializer(TemplateVariable.class, new TemplateVariableDeserializer());
        MODULE.addSerializer(TemplateVariable.class, new TemplateVariableSerializer());
        MODULE.addDeserializer(PartialPushPayload.class, new PartialPushPayloadDeserializer());
        MODULE.addSerializer(PartialPushPayload.class, new PartialPushPayloadSerializer());
        MODULE.addSerializer(TemplateSelector.class, new TemplateSelectorSerializer());
        MODULE.addSerializer(TemplatePushPayload.class, new TemplatePushPayloadSerializer());
        MODULE.addDeserializer(TemplateResponse.class, new TemplateResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(new JodaModule());
        MAPPER.registerModule(PushObjectMapper.getModule());
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private TemplatesObjectMapper() {
    }
}
