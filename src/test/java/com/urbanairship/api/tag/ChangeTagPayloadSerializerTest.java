package com.urbanairship.api.tag;

import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.tag.model.AddRemoveSet;
import com.urbanairship.api.tag.model.ChangeTagPayload;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangeTagPayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        AddRemoveSet ar = AddRemoveSet.newBuilder()
                                    .add("device1")
                                    .add("device2")
                                    .remove("device3")
                                    .build();

        ChangeTagPayload ctp = ChangeTagPayload.newBuilder()
                                               .setApids(ar)
                                               .setDevicePins(ar)
                                               .setDeviceTokens(ar)
                                               .setIOSChannels(ar)
                                               .build();

        String json = MAPPER.writeValueAsString(ctp);
        String expectedJson = "{\"ios_tokens\":{\"add\":[\"device1\",\"device2\"],\"remove\":[\"device3\"]},\"device_tokens\":{\"add\":[\"device1\",\"device2\"],\"remove\":[\"device3\"]},\"device_pins\":{\"add\":[\"device1\",\"device2\"],\"remove\":[\"device3\"]},\"apids\":{\"add\":[\"device1\",\"device2\"],\"remove\":[\"device3\"]}}";

        assertEquals(expectedJson, json);
    }
}
