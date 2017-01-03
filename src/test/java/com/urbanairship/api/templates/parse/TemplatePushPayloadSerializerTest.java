package com.urbanairship.api.templates.parse;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateSelector;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

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

        Map<String, Object> expectedMap = MAPPER.readValue(templatePushPayloadString, Map.class);
        Map<String, Object> actualMap = MAPPER.readValue(templatePushPayloadSerialized, Map.class);
        assertEquals(expectedMap, actualMap);
    }
}
