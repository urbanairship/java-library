/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.schedule.parse.TimeZones;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public final class ScheduleValidator {

    public void validate(Schedule schedule) throws APIParsingException {

        if (!schedule.getLocalTimePresent() && schedule.getScheduledTimestamp().isBefore(DateTime.now().minusSeconds(1))) {
            // Added an extra second above to account for the difference between timestamp generation and testing when using Before/Equals/After_Now
            throw new APIParsingException(String.format("Cannot schedule for the past %s", schedule.getScheduledTimestamp().toString()));
        }

        if (schedule.getLocalTimePresent()) {
            for (String tz : TimeZones.KNOWN_TIMEZONE_IDS) {
                DateTimeZone timeZone = DateTimeZone.forID(tz);
                if (schedule.getScheduledTimestamp().toDateTime(timeZone).isBefore(DateTime.now().minusSeconds(1))) {
                    // Added an extra second above to account for the difference between timestamp generation and testing when using Before/Equals/After_Now
                    throw new APIParsingException(String.format("The local time provided must be in the future for at least one time zone %s", schedule.getScheduledTimestamp().toString()));
                }
            }
        }
    }
}
