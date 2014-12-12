package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.Notification;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotificationBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testRoundTrip() throws Exception {
        Notification notification = Notification.newBuilder()
                .setAlert(RandomStringUtils.randomAlphabetic(20))
                .build();

        String json = mapper.writeValueAsString(notification);
        Notification parsed = mapper.readValue(json, Notification.class);

        assertEquals(notification, parsed);

        notification = Notification.newBuilder()
                .setAlert(RandomStringUtils.randomAlphabetic(20))
                .build();

        json = mapper.writeValueAsString(notification);
        parsed = mapper.readValue(json, Notification.class);

        assertEquals(notification, parsed);

/*        notification = Notification.newBuilder()
            .addPlatformOverride(Platform.WNS, new WNSDevicePayload(null))
            .build();

        json = mapper.writeValueAsString(notification);
        parsed = mapper.readValue(json, Notification.class);

        assertEquals(notification, parsed); */
    }

    @Test(expected = APIParsingException.class)
    public void testEmpty() throws Exception {
        mapper.readValue("{}", Notification.class);
    }
}
