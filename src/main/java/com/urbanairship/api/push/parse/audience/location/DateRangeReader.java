/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.push.model.audience.location.AbsoluteDateRange;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import org.joda.time.DateTime;

public class DateRangeReader implements JsonObjectReader<DateRange> {

    private PresenceTimeframe timeframe = PresenceTimeframe.ANYTIME;
    private AbsoluteDateRange.Builder absoluteBuilder = null;
    private RecentDateRange.Builder recentBuilder = null;

    public DateRangeReader() { }

    public void readTimeframe(JsonParser parser) throws IOException {
        boolean value = BooleanFieldDeserializer.INSTANCE.deserialize(parser, "last_seen");
        if (value) {
            timeframe = PresenceTimeframe.LAST_SEEN;
        }
    }

    public void readAbsolute(JsonParser parser) throws IOException {
        absoluteBuilder = parser.readValueAs(AbsoluteDateRange.Builder.class);
    }

    public void setResolution(DateRangeUnit resolution) {
        if (absoluteBuilder != null) {
            absoluteBuilder.setResolution(resolution);
        } else if (recentBuilder != null) {
            recentBuilder.setResolution(resolution);
        }
    }

    public void readRecent(JsonParser parser) throws IOException {
        recentBuilder = parser.readValueAs(RecentDateRange.Builder.class);
    }

    @Override
    public DateRange validateAndBuild() throws IOException {
        try {
            if (absoluteBuilder == null && recentBuilder == null) {
                throw new APIParsingException("Must supply one of 'days' or 'recent' date specifier for location expression");
            } else if (absoluteBuilder != null && recentBuilder != null) {
                throw new APIParsingException("Must supply only one of 'days' or 'recent' date specifier for location expression");
            }
            if (absoluteBuilder != null) {
                absoluteBuilder.setTimeframe(timeframe);
                return absoluteBuilder.build();
            } else {
                recentBuilder.setTimeframe(timeframe);
                return recentBuilder.build();
            }
        } catch (APIParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
