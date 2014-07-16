/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.model;


import com.urbanairship.api.common.parse.APIParsingException;

public class ScheduleValidator {

     public void validate(Schedule schedule) throws APIParsingException {

         if (schedule.getScheduledTimestamp().isPresent() && schedule.getScheduledTimestamp().get().isBeforeNow()) {
             throw new APIParsingException(String.format("Cannot schedule for the past %s", schedule.getScheduledTimestamp().toString()));
         }
         if (schedule.getLocalScheduledTimestamp().isPresent() && schedule.getLocalScheduledTimestamp().get().isBeforeNow()) {
             throw new APIParsingException(String.format("Cannot schedule for the past %s", schedule.getLocalScheduledTimestamp().toString()));
         }
     }
}
