package com.urbanairship.api.experiments.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;

public class ExperimentDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testExperiment() throws Exception {
        String experimentString =
                "{" +
                        "\"audience\":{\"named_user\":\"birdperson\"}," +
                        "\"device_types\":[\"ios\"]," +
                        "\"variants\":[" +
                        "{\"push\":{\"notification\":{\"alert\":\"Hello there\"}}}," +
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

    @Test
    public void testEmptyAudience() throws Exception {
        Exception exception = Assert.assertThrows(APIParsingException.class, () -> {
            String experimentString =
            "{" +
                    "\"device_types\":[\"ios\"]," +
                    "\"variants\":[" +
                    "{\"push\":{\"notification\":{\"alert\":\"Hello there\"}}}," +
                    "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                    "\"name\":\"Another test\"," +
                    "\"description\":\"Its a test hoo boy\"" +
                    "}";

            MAPPER.readValue(experimentString, Experiment.class);
        });
        
        String expectedMessage = "'audience' must be set";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testEmptyDeviceTypes() throws Exception {
        Exception exception = Assert.assertThrows(APIParsingException.class, () -> {
            String experimentString =
            "{" +
                    "\"audience\":{\"named_user\":\"birdperson\"}," +
                    "\"variants\":[" +
                    "{\"push\":{\"notification\":{\"alert\":\"Hello there\"}}}," +
                    "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                    "\"name\":\"Another test\"," +
                    "\"description\":\"Its a test hoo boy\"" +
                    "}";

            MAPPER.readValue(experimentString, Experiment.class);
        });
        
        String expectedMessage = "device_types' must be set";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testEmptyVariants() throws Exception {
        Exception exception = Assert.assertThrows(APIParsingException.class, () -> {
            String experimentString =
            "{" +
                    "\"audience\":{\"named_user\":\"birdperson\"}," +
                    "\"device_types\":[\"ios\"]," +
                    "\"name\":\"Another test\"," +
                    "\"description\":\"Its a test hoo boy\"" +
                    "}";

            MAPPER.readValue(experimentString, Experiment.class);
        });
        
        String expectedMessage = "At least one variant must be present.";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

}
