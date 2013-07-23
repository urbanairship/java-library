package com.urbanairship.api.schedule.model;


import com.urbanairship.api.common.parse.APIParsingException;

public class ScheduleValidator {

     public void validate(Schedule schedule) throws APIParsingException {

         if (schedule.getScheduledTimestamp().isBeforeNow()) {
             throw new APIParsingException(String.format("Cannot schedule for the past %s", schedule.getScheduledTimestamp().toString()));
         }
     }
}
