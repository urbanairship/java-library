package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testSimpleToast() throws Exception {
        String json
            = "{"
            + "  \"toast\": {"
            + "    \"binding\": { \"template\": \"ToastText03\" }"
            + "  }"
            + "}";

        WNSToastData expectedToast = WNSToastData.newBuilder()
            .setBinding(WNSBinding.newBuilder()
                        .setTemplate("ToastText03")
                        .build())
            .build();

        WNSDevicePayload payload = mapper.readValue(json, WNSDevicePayload.class);
        assertNotNull(payload.getBody());
        assertTrue(payload.getBody().isPresent());
        assertNotNull(payload.getAlert());
        assertFalse(payload.getAlert().isPresent());

        WNSPush body = payload.getBody().get();
        assertEquals(WNSPush.Type.TOAST, body.getType());
        assertEquals(expectedToast, body.getToast().get());
    }

    @Test(expected=APIParsingException.class)
    public void testValidate_Empty() throws Exception  {
        mapper.readValue("{}", WNSDevicePayload.class);
    }
}
