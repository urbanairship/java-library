package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
public class VariantSerializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testVariantSerializer() throws Exception {

        DateTime dateTime = DateTime.parse("2017-07-27T18:27:25.000Z");

        Schedule schedule = Schedule.newBuilder()
                .setScheduledTimestamp(dateTime)
                .build();

        Variant variant = Variant.newBuilder()
                .setSchedule(schedule)
                .setPushPayload(VariantPushPayload.newBuilder()
                        .setNotification(Notification.newBuilder()
                                .setAlert("Hello there")
                                .build()
                        )
                        .build())
                .build();

        String variantSerialized = MAPPER.writeValueAsString(variant);
        Variant variantFromJson = MAPPER.readValue(variantSerialized, Variant.class);

        Assert.assertEquals(variantFromJson, variant);
    }
}
