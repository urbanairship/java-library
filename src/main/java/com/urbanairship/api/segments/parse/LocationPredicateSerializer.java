/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.DateRange;
import com.urbanairship.api.segments.model.LocationPredicate;
import com.urbanairship.api.segments.model.RecentDateRange;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class LocationPredicateSerializer extends JsonSerializer<LocationPredicate> {

    public static final LocationPredicateSerializer INSTANCE = new LocationPredicateSerializer(LocationIdentifierSerializer.INSTANCE);
    private static final DateRangeSerializer DATE_RANGE_SERIALIZER = new DateRangeSerializer();
    private static final RecentDateRangeSerializer RECENT_DATE_RANGE_SERIALIZER = new RecentDateRangeSerializer();
    private final LocationIdentifierSerializer locationIdentifierSerializer;

    private LocationPredicateSerializer(LocationIdentifierSerializer locationIdentifierSerializer) {
        this.locationIdentifierSerializer = locationIdentifierSerializer;
    }

    @Override
    public void serialize(LocationPredicate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeFieldName("location");
        jgen.writeStartObject();

        locationIdentifierSerializer.serialize(value.getLocationIdentifier(), jgen, provider);

        writeDateSpecifier(value, jgen, provider);

        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    private void writeDateSpecifier(LocationPredicate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeFieldName("date");
        jgen.writeStartObject();

        if (value.isRecentDateRangeBounded()) {
            RECENT_DATE_RANGE_SERIALIZER.serialize(value.getRecentDateRange(), jgen, provider);
        } else {
            DATE_RANGE_SERIALIZER.serialize(value.getDateRange(), jgen, provider);
        }

        jgen.writeEndObject();
    }

    private static class DateRangeSerializer extends JsonSerializer<DateRange> {

        @Override
        public void serialize(DateRange value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeFieldName(value.getDateRangeUnit().getIdentifier());
            jgen.writeStartObject();

            jgen.writeFieldName("start");
            jgen.writeString(value.getRangeStart());

            jgen.writeFieldName("end");
            jgen.writeString(value.getRangeEnd());

            jgen.writeEndObject();
        }
    }

    private static class RecentDateRangeSerializer extends JsonSerializer<RecentDateRange> {

        @Override
        public void serialize(RecentDateRange value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeFieldName("recent");
            jgen.writeStartObject();

            jgen.writeFieldName(value.getDateRangeUnit().getIdentifier());
            jgen.writeNumber(value.getUnits());

            jgen.writeEndObject();
        }
    }
}
