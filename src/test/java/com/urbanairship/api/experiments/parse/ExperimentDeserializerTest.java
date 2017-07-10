package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ExperimentDeserializerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void testEmptyAudience() throws Exception {
        expectedException.expect(APIParsingException.class);
        expectedException.expectMessage("'audience' must be set");

        String experimentString =
                "{" +
                        "\"device_types\":[\"ios\"]," +
                        "\"variants\":[" +
                        "{\"push\":{\"notification\":{\"alert\":\"Hello Jenn\"}}}," +
                        "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                        "\"name\":\"Another test\"," +
                        "\"description\":\"Its a test hoo boy\"" +
                        "}";

        Experiment experiment = MAPPER.readValue(experimentString, Experiment.class);
    }

    @Test
    public void testEmptyDeviceTypes() throws Exception {
        expectedException.expect(APIParsingException.class);
        expectedException.expectMessage("'device_types' must be set");

        String experimentString =
                "{" +
                        "\"audience\":{\"named_user\":\"birdperson\"}," +
                        "\"variants\":[" +
                        "{\"push\":{\"notification\":{\"alert\":\"Hello Jenn\"}}}," +
                        "{\"push\":{\"notification\":{\"alert\":\"Boogaloo\"}}}]," +
                        "\"name\":\"Another test\"," +
                        "\"description\":\"Its a test hoo boy\"" +
                        "}";

        Experiment experiment = MAPPER.readValue(experimentString, Experiment.class);
    }

    @Test
    public void testEmptyVariants() throws Exception {
        expectedException.expect(APIParsingException.class);
        expectedException.expectMessage("At least one variant must be present.");

        String experimentString =
                "{" +
                        "\"audience\":{\"named_user\":\"birdperson\"}," +
                        "\"device_types\":[\"ios\"]," +
                        "\"name\":\"Another test\"," +
                        "\"description\":\"Its a test hoo boy\"" +
                        "}";

        Experiment experiment = MAPPER.readValue(experimentString, Experiment.class);
    }

}
