/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;


import com.urbanairship.api.reports.model.PushListingResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PushListingResponseDeserializerTest {

    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    String fiveresponse = "{  \n" +
            "  \"next_page\":\"Value for Next Page\",\n" +
            "  \"pushes\":[  \n" +
            "    {  \n" +
            "      \"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
            "      \"push_time\":\"2013-07-31 23:56:52\",\n" +
            "      \"push_type\":\"BROADCAST_PUSH\",\n" +
            "      \"direct_responses\":0,\n" +
            "      \"sends\":1\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_uuid\":\"3043779a-fa3c-11e2-a22b-d4bed9a887d4\",\n" +
            "      \"push_time\":\"2013-07-31 23:51:58\",\n" +
            "      \"push_type\":\"BROADCAST_PUSH\",\n" +
            "      \"direct_responses\":0,\n" +
            "      \"sends\":1\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_uuid\":\"1c06d01a-fa3c-11e2-aa2d-d4bed9a88699\",\n" +
            "      \"push_time\":\"2013-07-31 23:51:24\",\n" +
            "      \"push_type\":\"BROADCAST_PUSH\",\n" +
            "      \"direct_responses\":0,\n" +
            "      \"sends\":1\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_uuid\":\"a50eb7de-fa3b-11e2-912f-90e2ba025998\",\n" +
            "      \"push_time\":\"2013-07-31 23:48:05\",\n" +
            "      \"push_type\":\"BROADCAST_PUSH\",\n" +
            "      \"direct_responses\":0,\n" +
            "      \"sends\":1\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_uuid\":\"90483c8a-fa3b-11e2-92d0-90e2ba0253a0\",\n" +
            "      \"push_time\":\"2013-07-31 23:47:30\",\n" +
            "      \"push_type\":\"BROADCAST_PUSH\",\n" +
            "      \"direct_responses\":0,\n" +
            "      \"sends\":1,\n" +
            "      \"group_id\": \"a50eb7de-fa3b-11e2-912f-90e2ba025998\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void testPushInfoList() throws Exception {
        PushListingResponse response = mapper.readValue(fiveresponse, PushListingResponse.class);
        assertNotNull(response);
        assertEquals(5, response.getPushInfoList().get().size());
        assertEquals("Value for Next Page", response.getNextPage().get());
        assertEquals("1c06d01a-fa3c-11e2-aa2d-d4bed9a88699",
                response.getPushInfoList().get().get(2).getPushId().toString());
        assertEquals("a50eb7de-fa3b-11e2-912f-90e2ba025998",
                response.getPushInfoList().get().get(4).getGroupID().get().toString());
    }
}
