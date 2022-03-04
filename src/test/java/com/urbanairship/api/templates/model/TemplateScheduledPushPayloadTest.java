package com.urbanairship.api.templates.model;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.schedule.model.Schedule;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplateScheduledPushPayloadTest {

    @Test
    public void testTemplateScheduledPushPayload() {

        TemplateSelector mergeData = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("SUB1", "thing1")
                .addSubstitution("SUB2", "thing2")
                .addSubstitution("SUB3", "thing3")
                .build();

        Schedule schedule = Schedule.newBuilder()
                .setScheduledTimestamp(DateTime.now())
                .build();

        TemplateScheduledPushPayload payload = TemplateScheduledPushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                .setMergeData(mergeData)
                .setSchedule(schedule)
                .build();

        assertNotNull(payload);
        assertEquals(payload.getAudience(), Selectors.tag("tag"));
        assertEquals(payload.getDeviceTypes(), DeviceTypeData.of(DeviceType.ANDROID));
        assertEquals(payload.getMergeData(), mergeData);
    }

    @Test(expected = Exception.class)
    public void testInvalidTemplateScheduledPushPayload() {
        TemplateScheduledPushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .build();
    }

}
