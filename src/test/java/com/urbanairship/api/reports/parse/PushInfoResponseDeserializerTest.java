package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.reports.model.PushInfoResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PushInfoResponseDeserializerTest {

    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testPushInfoResponse() {
        String response =
                "{\n" +
                    "\"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
                    "\"push_time\":\"2013-07-31 23:56:52\",\n" +
                    "\"push_type\":\"BROADCAST_PUSH\",\n" +
                    "\"direct_responses\":0,\n" +
                    "\"sends\":1,\n" +
                    "\"group_id\": \"04911800-f48d-11e2-acc5-90e2bf027020\"\n" +
                    "}";

        try {
            PushInfoResponse obj = mapper.readValue(response, PushInfoResponse.class);

            assertNotNull(obj);
            assertEquals("df31cae0-fa3c-11e2-97ce-14feb5d317b8", obj.getPushId().toString());
            assertEquals(0, obj.getDirectResponses());
            assertEquals(1, obj.getSends());
            assertEquals(PushInfoResponse.PushType.BROADCAST_PUSH, obj.getPushType());
            assertTrue(obj.getGroupID().isPresent());
            assertEquals("04911800-f48d-11e2-acc5-90e2bf027020", obj.getGroupID().get().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testPushInfoResponseNoGroupID() {
        String response =
                "{\n" +
                        "\"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
                        "\"push_time\":\"2013-07-31 23:56:52\",\n" +
                        "\"push_type\":\"BROADCAST_PUSH\",\n" +
                        "\"direct_responses\":0,\n" +
                        "\"sends\":1\n" +
                        "}";

        try {
            PushInfoResponse obj = mapper.readValue(response, PushInfoResponse.class);

            assertNotNull(obj);
            assertEquals("df31cae0-fa3c-11e2-97ce-14feb5d317b8", obj.getPushId().toString());
            assertEquals(0, obj.getDirectResponses());
            assertEquals(1, obj.getSends());
            assertEquals(PushInfoResponse.PushType.BROADCAST_PUSH, obj.getPushType());
            assertFalse(obj.getGroupID().isPresent());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception " + ex.getMessage());
        }
    }
    @Test(expected = APIParsingException.class)
    public void testNoPushUuid() throws Exception {

        String json = "{  \n" +
                "  \"push_time\":\"2013-12-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176,\n" +
                "  \"group_id\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\"\n" +
                "}";

        mapper.readValue(json, PushInfoResponse.class);
    }
}
