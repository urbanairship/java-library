package com.urbanairship.api.tag;

import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BatchModificationPayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        BatchModificationPayload bmp = BatchModificationPayload.newBuilder()
                .addBatchObject(BatchTagSet.newBuilder()
                        .setDevice(BatchTagSet.DeviceIdTypes.APID, "device1")
                        .addTag("tag1")
                        .addTag("tag2")
                        .build())
                .addBatchObject(BatchTagSet.newBuilder()
                        .setDevice(BatchTagSet.DeviceIdTypes.DEVICE_TOKEN, "device2")
                        .addTag("special1")
                        .addTag("special2")
                        .addTag("special3")
                        .build())
                .build();

        String json = MAPPER.writeValueAsString(bmp);
        String expectedJson = "[{\"apid\":\"device1\",\"tags\":[\"tag1\",\"tag2\"]},{\"device_token\":\"device2\",\"tags\":[\"special1\",\"special2\",\"special3\"]}]";

        assertEquals(expectedJson, json);
    }

    @Test
    public void testChannelsSerialization() throws Exception {

        BatchModificationPayload bmp = BatchModificationPayload.newBuilder()
            .addBatchObject(BatchTagSet.newBuilder()
                .setDevice(BatchTagSet.DeviceIdTypes.IOS_CHANNEL, "device1")
                .addTag("tag1")
                .addTag("tag2")
                .build())
            .addBatchObject(BatchTagSet.newBuilder()
                .setDevice(BatchTagSet.DeviceIdTypes.ANDROID_CHANNEL, "device2")
                .addTag("tag3")
                .addTag("tag4")
                .build())
            .addBatchObject(BatchTagSet.newBuilder()
                .setDevice(BatchTagSet.DeviceIdTypes.AMAZON_CHANNEL, "device3")
                .addTag("tag5")
                .addTag("tag6")
                .build())
            .build();

        String json = MAPPER.writeValueAsString(bmp);
        String expectedJson = "[{\"ios_channel\":\"device1\",\"tags\":[\"tag1\",\"tag2\"]},{\"android_channel\":\"device2\",\"tags\":[\"tag3\",\"tag4\"]},{\"amazon_channel\":\"device3\",\"tags\":[\"tag5\",\"tag6\"]}]";

        assertEquals(expectedJson, json);
    }
}
