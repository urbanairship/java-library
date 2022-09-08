package com.urbanairship.api.experiments.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.experiments.parse.ExperimentObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class ExperimentResponseTest {

    @Test
    public void testExperimentResponse() {
        ExperimentResponse response = ExperimentResponse.newBuilder()
                .setOk(true)
                .setOperationId("op123")
                .setPushId("id1")
                .build();

        assertNotNull(response);
        assertTrue(response.getOk());
        assertEquals(response.getOperationId().get(), "op123");
        assertEquals(response.getPushId().get(), "id1");
        assertFalse(response.getExperimentId().isPresent());
    }

    @Test
    public void testErrorAPIExperimentResponse() throws IOException {
        String jsonResponse = "{\n" +
                "    \"ok\": false,\n" +
                "    \"error\": \"error\",\n" +
                "    \"details\": {\n" +
                "        \"error\": \"error\"\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = ExperimentObjectMapper.getInstance();
        ExperimentResponse response = mapper.readValue(jsonResponse, ExperimentResponse.class);
        assertEquals("error", response.getError().get());
        assertEquals("error", response.getErrorDetails().get().getError().get());
        assertFalse(response.getOk());
    }
}
