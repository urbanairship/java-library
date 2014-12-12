/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;


import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SinglePushInfoResponseDeserializerTest {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testSinglePushInfoResponseJsonDeserialize() throws Exception {

        String json = "{  \n" +
                "  \"push_uuid\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\",\n" +
                "  \"push_time\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176\n" +
                "}";

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime("2013-07-31 22:05:53");

        SinglePushInfoResponse obj = mapper.readValue(json, SinglePushInfoResponse.class);
        assertNotNull(obj);
        assertEquals(UUID.fromString("5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0"), obj.getPushUUID());
        assertEquals(dt, obj.getPushTime());
        assertEquals(SinglePushInfoResponse.PushType.BROADCAST_PUSH, obj.getPushType());
        assertEquals(4, obj.getDirectResponses());
        assertEquals(176, obj.getSends());

    }

    @Test
    public void testSinglePushInfoResponseJsonDeserializeWithGroupID() throws Exception {

        String json = "{  \n" +
                "  \"push_uuid\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\",\n" +
                "  \"push_time\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176,\n" +
                "  \"group_id\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\"\n" +
                "}";

        SinglePushInfoResponse obj = mapper.readValue(json, SinglePushInfoResponse.class);
        assertNotNull(obj);


    }

    @Test(expected = APIParsingException.class)
    public void testSinglePushInfoResponseJsonDeserializeWithInvalidJson() throws Exception {

        String json = "{  \n" +
                "  \"push_time\":\"2013-12-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176,\n" +
                "  \"group_id\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\"\n" +
                "}";

        SinglePushInfoResponse obj = mapper.readValue(json, SinglePushInfoResponse.class);
        assertNotNull(obj);
    }
}
