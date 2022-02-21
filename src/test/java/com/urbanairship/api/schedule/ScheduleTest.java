package com.urbanairship.api.schedule;

import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ScheduleTest {

    private DateTime dateTime;

    @Before
    public void setUp() {
        dateTime = DateTime.now();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Neither setScheduledTimestamp nor setBestTime called
     */
    @Test
    public void testScheduledTimeAndBestTimeBothAbsent() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        Schedule.newBuilder()
                .build();
    }

    /**
     * Both setScheduledTimestamp and setBestTime called
     */
    @Test
    public void testScheduledTimeAndBestTimeBothPresent() throws Exception {
        thrown.expect(IllegalArgumentException.class);

        Schedule.newBuilder()
                .setScheduledTimestamp(dateTime)
                .setBestTime(BestTime.newBuilder()
                        .setSendDate(dateTime)
                        .build())
                .build();
    }

    /**
     * Only setScheduledTimestamp, not setBestTime called
     */
    @Test
    public void testScheduledTime() {

        Schedule schedule = Schedule.newBuilder()
                .setScheduledTimestamp(dateTime)
                .build();

        Assert.assertNotNull(schedule.getScheduledTimestamp() );
    }

    /**
     * Only setBestTime called, not setScheduledTimestamp
     */
    @Test
    public void testBestTime() {

        Schedule schedule = Schedule.newBuilder()
                .setBestTime(BestTime.newBuilder()
                        .setSendDate(dateTime)
                        .build())
                .build();

        Assert.assertNotNull(schedule.getBestTime());
    }
}
