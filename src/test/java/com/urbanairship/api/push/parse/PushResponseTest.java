package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.PushResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PushResponseTest {

    @Test
    public void testAPIPushResponse() {
        String pushJSON = "{\n" +
                "    \"ok\" : true,\n" +
                "    \"operation_id\" : \"df6a6b50\",\n" +
                "    \"push_ids\": [\n" +
                "        \"id1\",\n" +
                "        \"id2\"\n" +
                "    ],\n" +
                "    \"message_ids\": [],\n" +
                "    \"content_urls\" : [],\n" +
                "    \"localized_ids\" : []\n" +
                "}";

        ObjectMapper mapper = PushObjectMapper.getInstance();
        try {
            PushResponse response = mapper.readValue(pushJSON, PushResponse.class);
            assertEquals("Error in response operationId", "df6a6b50", response.getOperationId().get());
            assertEquals("Error in response pushIds", "id1", response.getPushIds().get().get(0));
            assertEquals("Error in response pushIds", "id2", response.getPushIds().get().get(1));
            assertTrue("Error in response status",
                    response.getOk());
            assertTrue("Error in response messageIds",
                response.getMessageIds().get().isEmpty());
            assertTrue("Error in response contentUrls",
                response.getContentUrls().get().isEmpty());
        } catch (IOException ex) {
            fail("Exception in PushResponseTest Message: " + ex.getMessage());
        }
    }

    @Test
    public void testErrorAPIPushResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = PushObjectMapper.getInstance();
        PushResponse response = mapper.readValue(jsonResponse, PushResponse.class);

        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertFalse(response.getOk());
    }
}
