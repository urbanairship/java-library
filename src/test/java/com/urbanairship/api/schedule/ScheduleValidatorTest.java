package com.urbanairship.api.schedule;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.ScheduleValidator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class ScheduleValidatorTest {

    ScheduleValidator validator = new ScheduleValidator();

    @Test
    public void testFutureDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setScheduledTimestamp(DateTime.now().plusDays(1)).build());
    }

    @Test(expected = APIParsingException.class)
    public void testPastDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setScheduledTimestamp(DateTime.now().minusDays(1)).build());
    }

    @Test
    public void testFutureLocalDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setLocalScheduledTimestamp(DateTime.now().plusDays(1)).build());
    }

    @Test(expected = APIParsingException.class)
    public void testPastLocalDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setLocalScheduledTimestamp(DateTime.now().minusDays(1)).build());
    }

    @Test
    public void testPartiallyPastLocalDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setLocalScheduledTimestamp(DateTime.now(DateTimeZone.forID("America/Chicago"))).build());
    }

    @Test
    public void testCurrentLocalDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setLocalScheduledTimestamp(DateTime.now()).build());
    }

    @Test
    public void testCurrentDateTime() throws Exception {
        validator.validate(Schedule.newBuilder().setScheduledTimestamp(DateTime.now()).build());
    }
}
