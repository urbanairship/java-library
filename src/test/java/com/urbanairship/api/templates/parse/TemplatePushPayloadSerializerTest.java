package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateSelector;

import org.junit.Assert;
import org.junit.Test;

public class TemplatePushPayloadSerializerTest {

    private static final ObjectMapper MAPPER = TemplatesObjectMapper.getInstance();

    @Test
    public void testTemplatePushPayloadSerializer() throws Exception {

        TemplateSelector mergeData = TemplateSelector.newBuilder()
                .setTemplateId("id123")
                .addSubstitution("FIRST_NAME", "Prince")
                .addSubstitution("LAST_NAME", "")
                .build();

        TemplatePushPayload payload = TemplatePushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS, DeviceType.ANDROID))
                .setMergeData(mergeData)
                .build();

        String templatePushPayloadSerialized = MAPPER.writeValueAsString(payload);
        String templatePushPayloadString =
                "{" +
                    "\"audience\":\"ALL\"," +
                    "\"device_types\":[\"ios\",\"android\"]," +
                    "\"merge_data\":{" +
                        "\"template_id\":\"id123\"," +
                        "\"substitutions\":{" +
                            "\"FIRST_NAME\":\"Prince\"," +
                            "\"LAST_NAME\":\"\"" +
                        "}" +
                    "}" +
                "}";

        JsonNode jsonFromObject = MAPPER.readTree(templatePushPayloadSerialized);
        JsonNode jsonFromString = MAPPER.readTree(templatePushPayloadString);

        Assert.assertEquals(jsonFromObject, jsonFromString);
    }
}
