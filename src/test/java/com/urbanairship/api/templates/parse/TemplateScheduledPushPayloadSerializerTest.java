package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateScheduledPushPayload;
import com.urbanairship.api.templates.model.TemplateSelector;
import org.joda.time.DateTime;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TemplateScheduledPushPayloadSerializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplateScheduledPushPayloadSerializer() throws Exception {
        Campaigns campaigns = Campaigns.newBuilder()
                .addCategory("first_category")
                .addCategory("second_category")
                .build();

        TemplateSelector mergeData = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("FIRST_NAME", "Prince")
                .addSubstitution("LAST_NAME", "")
                .build();

        Schedule schedule = Schedule.newBuilder().setScheduledTimestamp(DateTime.parse("2019-05-30T07:53:13Z")).build();

        TemplateScheduledPushPayload payload = TemplateScheduledPushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID))
                .setMergeData(mergeData)
                .setSchedule(schedule)
                .setCampaigns(campaigns)
                .setName("Prince")
                .build();

        String templateScheduledPushPayloadSerialized = MAPPER.writeValueAsString(payload);
        String templateScheduledPushPayloadString =
                "{" +
                    "\"audience\":\"ALL\"," +
                    "\"name\":\"Prince\"," +
                    "\"device_types\":[\"ios\",\"android\"]," +
                    "\"campaigns\": {" +
                        "\"categories\": [\"first_category\", \"second_category\"]" +
                    "}," +
                    "\"merge_data\":{" +
                        "\"template_id\":\"id123\"," +
                        "\"substitutions\":{" +
                            "\"FIRST_NAME\":\"Prince\"," +
                            "\"LAST_NAME\":\"\"" +
                        "}" +
                    "}," +"\"schedule\":{\"scheduled_time\":\"2019-05-30T07:53:13\"}"+
                "}";

        JsonNode jsonFromObject = MAPPER.readTree(templateScheduledPushPayloadSerialized);
        JsonNode jsonFromString = MAPPER.readTree(templateScheduledPushPayloadString);

        assertEquals(jsonFromObject, jsonFromString);
    }
}
