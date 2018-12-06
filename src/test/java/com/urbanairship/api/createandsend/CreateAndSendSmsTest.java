package com.urbanairship.api.createandsend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.createandsend.model.notification.SmsFields;
import com.urbanairship.api.createandsend.model.notification.SmsPayload;
import com.urbanairship.api.createandsend.model.notification.SmsTemplate;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

public class CreateAndSendSmsTest {
    private SmsPayload smsPayload;
    private SmsPayload smsPayloadWithTemplate;

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Before
    public void setUp() {
        PushExpiry expiry = PushExpiry.newBuilder()
                .setExpirySeconds(1000)
                .build();

        SmsFields smsFields = SmsFields.newBuilder()
                .setAlert("smsFieldAlert")
                .build();

        SmsTemplate smsTemplate = SmsTemplate.newBuilder()
                .setSmsFields(smsFields)
                .build();

        smsPayloadWithTemplate = SmsPayload.newBuilder()
                .setSmsTemplate(smsTemplate)
                .setPushExpiry(expiry)
                .build();

        smsPayload = SmsPayload.newBuilder()
                .setAlert("smsAlert")
                .setPushExpiry(expiry)
                .build();
    }

    @Test
    public void testBadPayload() {

    }

    @Test
    public void testFields() throws IOException {
        String expectedJson = "{\"alert\":\"smsFieldAlert\"}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate.getSmsTemplate().get().getSmsFields().get());

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testSmsTemplate() throws IOException {
        String expectedJson = "{\"fields\":{\"alert\":\"smsFieldAlert\"}}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate.getSmsTemplate().get());

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testSmsPayload() throws IOException {
        String expectedJson = "{\"template\":{\"fields\":{\"alert\":\"smsFieldAlert\"}},\"expiry\":1000}";
        String actualJson = MAPPER.writeValueAsString(smsPayloadWithTemplate);

        JsonNode actualNode = MAPPER.readTree(actualJson);
        JsonNode expectedNode = MAPPER.readTree(expectedJson);

        assertEquals(expectedNode, actualNode);
    }
}
