package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ExperimentDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testExperiment() throws Exception {
        String experimentString =
                "{" +
                        "\"audience\":{\"named_user\":\"birdperson\"}," +
                        "\"device_types\":[\"ios\"]," +
                        "\"variants\":[" +
                        "{\"push\":{\"notification\":{\"alert\":\"Hello Jenn\"}}}," +
                        "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                        "\"name\":\"Another test\"," +
                        "\"description\":\"Its a test hoo boy\"" +
                        "}";

        Experiment experiment = MAPPER.readValue(experimentString, Experiment.class);
        assertNotNull(experiment);
        assertTrue(experiment.getName().isPresent());
        assertTrue(experiment.getDescription().isPresent());
        assertEquals(experiment.getAudience(), Selectors.namedUser("birdperson"));
        assertEquals(experiment.getDeviceTypes(), DeviceTypeData.of(DeviceType.IOS));
        assertFalse(experiment.getVariants().isEmpty());
        assertFalse(experiment.getControl().isPresent());
    }

    @Test(expected = Exception.class)
    public void testEmptyExperiment() {
        Experiment experiment = Experiment.newBuilder().build();
    }

}
