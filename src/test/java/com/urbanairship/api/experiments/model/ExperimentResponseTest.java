package com.urbanairship.api.experiments.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
}
