package com.urbanairship.api.reports.parse;


import com.urbanairship.api.reports.model.PerPushCounts;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
public class PlatformCountsDeserializerTest {

    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testAndroidPlatformCountJsonDeserialize() throws Exception {

        String json = "{  \n" +
                "    \"direct_responses\":11,\n" +
                "    \"influenced_responses\":33,\n" +
                "    \"sends\":22\n" +
                "  }\n";

        PerPushCounts obj = mapper.readValue(json, PerPushCounts.class);
        assertNotNull(obj);

        assertEquals(11, obj.getDirectResponses());
        assertEquals(33, obj.getInfluencedResponses());
        assertEquals(22, obj.getSends());
    }


}
