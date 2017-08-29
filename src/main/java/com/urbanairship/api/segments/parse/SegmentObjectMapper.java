package com.urbanairship.api.segments.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentListingView;
import com.urbanairship.api.segments.model.SegmentView;

public class SegmentObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Segments API Module", new Version(1, 0, 0, null));

    static {
        MODULE.addSerializer(SegmentView.class, new SegmentViewSerializer());
        MODULE.addDeserializer(SegmentView.class, new SegmentViewDeserializer());
        MODULE.addDeserializer(SegmentListingResponse.class, new SegmentListingResponseDeserializer());
        MODULE.addDeserializer(SegmentListingView.class, new SegmentListingViewDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private SegmentObjectMapper() {
    }

}
