package com.urbanairship.api.experiments.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.experiments.model.ExperimentResponse;

public class ExperimentResponseDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testExperimentResponseDeserializer() throws Exception {
        String experimentCreateResponseString =
                "{" +
                        "\"ok\": true," +
                        "\"operation_id\": \"op-id-123\"," +
                        "\"experiment_id\": \"experiment-id-123\"" +
                        "}";

        ExperimentResponse experimentCreateResponse = MAPPER.readValue(experimentCreateResponseString, ExperimentResponse.class);
        assertNotNull(experimentCreateResponse);
        assertEquals(experimentCreateResponse.getOk(), true);
        assertEquals(experimentCreateResponse.getExperimentId().get(), "experiment-id-123");
        assertEquals(experimentCreateResponse.getOperationId().get(), "op-id-123");
    }
}
