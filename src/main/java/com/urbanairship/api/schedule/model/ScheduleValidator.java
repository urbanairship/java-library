/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.schedule.parse.TimeZones;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ScheduleValidator {

     public void validate(Schedule schedule) throws APIParsingException {

         if (schedule.getScheduledTimestamp().isPresent() && schedule.getScheduledTimestamp().get().isBefore(DateTime.now().minusSeconds(1))) {
             // Added an extra second above to account for the difference between timestamp generation and testing when using Before/Equals/After_Now
             throw new APIParsingException(String.format("Cannot schedule for the past %s", schedule.getScheduledTimestamp().get().toString()));
         }

         if (schedule.getLocalScheduledTimestamp().isPresent()) {
             boolean futureDateTime = false;
             for (String tz : TimeZones.KNOWN_TIMEZONE_IDS) {
                 DateTimeZone timeZone = DateTimeZone.forID(tz);
                 if (!schedule.getLocalScheduledTimestamp().get().toDateTime(timeZone).isBefore(DateTime.now().minusSeconds(1))) {
                     // Added an extra second above to account for the difference between timestamp generation and testing when using Before/Equals/After_Now
                     futureDateTime = true;
                     break;
                 }
             }
             if (!futureDateTime) {
                 throw new APIParsingException(String.format("The local time provided must be in the future for at least one time zone %s", schedule.getLocalScheduledTimestamp().get().toString()));
             }
         }
     }
}
