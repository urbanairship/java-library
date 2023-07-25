package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.Orchestration;
import com.urbanairship.api.push.model.OrchestrationType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class OrchestrationTest {

    private final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testOrchestrationFanOut() throws Exception {
        Orchestration orchestration = Orchestration.newBuilder()
                .setOrchestrationType(OrchestrationType.FAN_OUT)
                .build();

        String pushPayloadStr = mapper.writeValueAsString(orchestration);
        JsonNode actualJson = mapper.readTree(pushPayloadStr);
        String json = "{\"type\":\"fan_out\"}";
        JsonNode expectedJson = mapper.readTree(json);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testOrchestrationChannelPriority() throws Exception {
        Orchestration orchestration = Orchestration.newBuilder()
                .setOrchestrationType(OrchestrationType.CHANNEL_PRIORITY)
                .addOrchestrationChannelPriority("test")
                .addOrchestrationChannelPriority("test2")
                .build();

        String pushPayloadStr = mapper.writeValueAsString(orchestration);
        JsonNode actualJson = mapper.readTree(pushPayloadStr);
        String json = "{\"type\":\"channel_priority\",\"channel_priority\":[\"test\",\"test2\"]}";
        JsonNode expectedJson = mapper.readTree(json);
        assertEquals(expectedJson, actualJson);
    }

}
