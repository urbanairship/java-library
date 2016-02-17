package com.urbanairship.api.namedusers;

import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.nameduser.model.AssociateNamedUserPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssociateNamedUserTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testAssociation() throws Exception {


        AssociateNamedUserPayload associateNamedUserPayload = AssociateNamedUserPayload.newBuilder()
                .setChannelId("df6a6b50-9843-0304-d5a5-743f246a4946")
                .setDeviceType(DeviceType.IOS)
                .setNamedUserId("user-id-1234")
                .build();

        String json = MAPPER.writeValueAsString(associateNamedUserPayload);
        String expectedJson = "{\"channel_id\":\"df6a6b50-9843-0304-d5a5-743f246a4946\"," +
                "\"device_type\":\"ios\"," +
                "\"named_user_id\":\"user-id-1234\"}";


        assertEquals(expectedJson, json);
    }

}
