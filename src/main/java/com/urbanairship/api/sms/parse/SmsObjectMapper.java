package com.urbanairship.api.sms.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload;
import com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload;
import com.urbanairship.api.sms.model.CustomSmsResponseResponse;
import com.urbanairship.api.sms.model.MmsSlides;

public class SmsObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("SMS API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addSerializer(CustomSmsResponseSmsPayload.class, new CustomSmsResponseSmsPayloadSerializer());
        MODULE.addSerializer(CustomSmsResponseMmsPayload.class, new CustomSmsResponseMmsPayloadSerializer());
        MODULE.addSerializer(MmsSlides.class, new MmsSlidesSerializer());
        MODULE.addDeserializer(CustomSmsResponseResponse.class, new CustomSmsResponseResponseDeserializer());

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

    private SmsObjectMapper() {
    }

}
