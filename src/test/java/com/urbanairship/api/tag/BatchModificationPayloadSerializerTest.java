package com.urbanairship.api.tag;

import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.codehaus.jackson.map.ObjectMapper;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BatchModificationPayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        BatchModificationPayload bmp = BatchModificationPayload.newBuilder()
                                               .addBatchObject(BatchTagSet.newBuilder()
                                                                          .setDevice(BatchTagSet.DEVICEIDTYPES.APID, "device1")
                                                                          .addTag("tag1")
                                                                          .addTag("tag2")
                                                                          .build())
                                               .addBatchObject(BatchTagSet.newBuilder()
                                                                          .setDevice(BatchTagSet.DEVICEIDTYPES.DEVICE_TOKEN, "device2")
                                                                          .addTag("special1")
                                                                          .addTag("special2")
                                                                          .addTag("special3")
                                                                          .build())
                                               .build();

        String json = MAPPER.writeValueAsString(bmp);
        String expectedJson = "[{\"apid\":\"device1\",\"tags\":[\"tag1\",\"tag2\"]},{\"device_token\":\"device2\",\"tags\":[\"special1\",\"special2\",\"special3\"]}]";

        assertEquals(expectedJson, json);
    }
}
