package com.urbanairship.api.push.parse.ios;


import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IOSDevicePayloadSerializerTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {


        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert("iOS override")
                .build();

        String expected
                = "\"iOS override\"";

        String json = mapper.writeValueAsString(payload.getAlert().get());

        assertEquals(expected, json);
    }


    @Test
    public void testEmptyAlert() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder().build())
                .setBadge(IOSBadgeData.newBuilder().setType(IOSBadgeData.Type.VALUE).setValue(1).build())
                .build();

        String json = mapper.writeValueAsString(payload);


        String expected
                = "{\"alert\":{},\"badge\":1}";

        assertEquals(expected, json);
    }

    @Test
    public void testCompoundAlert() throws Exception {

        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder()
                        .setBody("B")
                        .setActionLocKey("ALK")
                        .setLocKey("LK")
                        .setLocArgs(ImmutableList.of("arg1", "arg2"))
                        .setLaunchImage("LI")
                        .build())
                .build();

        String json = mapper.writeValueAsString(payload);


        String expected
            = "{\"alert\":{\"body\":\"B\",\"action-loc-key\":\"ALK\",\"loc-key\":\"LK\",\"loc-args\":[\"arg1\",\"arg2\"],\"launch-image\":\"LI\"}}";

        assertEquals(expected, json);
    }

    @Test
    public void testAlertSansBody() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
            .setAlert(IOSAlertData.newBuilder()
                .setActionLocKey("ALK")
                .setLocKey("LK")
                .setLocArgs(ImmutableList.of("arg1", "arg2"))
                .setLaunchImage("LI")
                .build())
            .build();

        String json = mapper.writeValueAsString(payload);

        String expected

            = "{\"alert\":{\"action-loc-key\":\"ALK\",\"loc-key\":\"LK\",\"loc-args\":[\"arg1\",\"arg2\"],\"launch-image\":\"LI\"}}";

        assertEquals(expected, json);
    }

}
