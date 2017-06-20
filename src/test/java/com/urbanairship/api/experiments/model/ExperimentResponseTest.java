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
                .addPushId("id1")
                .addPushId("id2")
                .addPushId("id3")
                .build();

        assertNotNull(response);
        assertEquals(response.getOk(), true);
        assertEquals(response.getOperationId().get(), "op123");
        assertEquals(response.getPushIds().get().get(0), "id1");
        assertEquals(response.getPushIds().get().get(1), "id2");
        assertEquals(response.getPushIds().get().get(2), "id3");
        assertFalse(response.getExperimentId().isPresent());
    }
}
