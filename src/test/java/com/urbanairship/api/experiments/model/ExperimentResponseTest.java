package com.urbanairship.api.experiments.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.experiments.parse.ExperimentObjectMapper;

public class ExperimentResponseTest {

    @Test
    public void testExperimentResponse() {
        ExperimentResponse response = ExperimentResponse.newBuilder()
                .setOk(true)
                .setOperationId("op123")
                .setPushId("id1")
                .build();

        assertNotNull(response);
        assertEquals(response.getOk(), true);
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
        assertEquals(false, response.getOk());
    }
}
