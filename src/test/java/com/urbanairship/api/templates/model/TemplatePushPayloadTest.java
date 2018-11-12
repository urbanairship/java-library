package com.urbanairship.api.templates.model;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemplatePushPayloadTest {

    @Test
    public void testTemplatePushPayload() {

        TemplateSelector mergeData = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("SUB1", "thing1")
                .addSubstitution("SUB2", "thing2")
                .addSubstitution("SUB3", "thing3")
                .build();

        TemplatePushPayload payload = TemplatePushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
                .setMergeData(mergeData)
                .build();

        assertNotNull(payload);
        assertEquals(payload.getAudience(), Selectors.tag("tag"));
        assertEquals(payload.getDeviceTypes(), DeviceTypeData.of(DeviceType.ANDROID));
        assertEquals(payload.getMergeData(), mergeData);
    }

    @Test(expected = Exception.class)
    public void testInvalidTemplatePushPayload() {
        TemplatePushPayload payload = TemplatePushPayload.newBuilder()
                .setAudience(Selectors.tag("tag"))
                .build();
    }

}
