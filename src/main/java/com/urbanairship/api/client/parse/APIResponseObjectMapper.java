/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIError;
import com.urbanairship.api.client.APIErrorDetails;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.location.model.Location;
import com.urbanairship.api.location.parse.LocationDeserializer;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.segments.model.AudienceSegment;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.Operator;
import com.urbanairship.api.segments.model.Predicate;
import com.urbanairship.api.segments.parse.AudienceSegmentDeserializer;
import com.urbanairship.api.segments.parse.AudienceSegmentSerializer;
import com.urbanairship.api.segments.parse.LocationPredicateDeserializer;
import com.urbanairship.api.segments.parse.LocationPredicateSerializer;
import com.urbanairship.api.segments.parse.OperatorDeserializer;
import com.urbanairship.api.segments.parse.OperatorSerializer;
import com.urbanairship.api.segments.parse.PredicateDeserializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/*
This is where object serialization and deserialization are registered with
Jackson to enable object parsing.
 */
public final class APIResponseObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Urban Airship API Client Module", new Version(1, 0, 0, null));

    static {
        MODULE.addDeserializer(APIErrorDetails.Location.class, new StreamLocationDeserializer());
        MODULE.addDeserializer(APIErrorDetails.class, new APIErrorDetailsDeserializer());
        MODULE.addDeserializer(APIError.class, new APIErrorDeserializer());
        MODULE.addDeserializer(APIListTagsResponse.class, new APIListTagsResponseDeserializer());
        MODULE.addDeserializer(SegmentInformation.class, new SegmentInformationDeserializer());
        MODULE.addDeserializer(APIListAllSegmentsResponse.class, new APIListAllSegmentsResponseDeserializer());
        MODULE.addDeserializer(AudienceSegment.class, AudienceSegmentDeserializer.INSTANCE);
        MODULE.addSerializer(AudienceSegment.class, AudienceSegmentSerializer.INSTANCE);
        MODULE.addDeserializer(LocationPredicate.class, LocationPredicateDeserializer.INSTANCE);
        MODULE.addSerializer(LocationPredicate.class, LocationPredicateSerializer.INSTANCE);
        MODULE.addDeserializer(Operator.class, OperatorDeserializer.INSTANCE);
        MODULE.addSerializer(Operator.class, OperatorSerializer.INSTANCE);
        MODULE.addDeserializer(Predicate.class, PredicateDeserializer.INSTANCE);
        MODULE.addDeserializer(Location.class, new LocationDeserializer());
        MODULE.addDeserializer(APILocationResponse.class, new APILocationResponseDeserializer());

        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(MODULE);
    }

    private APIResponseObjectMapper() {
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

}
