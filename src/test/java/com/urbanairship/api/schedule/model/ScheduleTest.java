package com.urbanairship.api.schedule.model;

import com.urbanairship.api.schedule.model.BestTime;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ScheduleTest {

    private DateTime dateTime;

    @Before

    public void setUp() {
        dateTime = DateTime.now();


    }

    /**
     * Neither setScheduledTimestamp nor setBestTime called
     */
    @Test(expected = IllegalArgumentException.class)

    public void testScheduledTimeAndBestTimeBothAbsent() {
        Schedule schedule = Schedule.newBuilder()
                .build();
        
    }

    /**
     * Both setScheduledTimestamp and setBestTime called
     */
    @Test(expected = IllegalArgumentException.class)

    public void testScheduledTimeAndBestTimeBothPresent() {

        Schedule schedule = Schedule.newBuilder()
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
