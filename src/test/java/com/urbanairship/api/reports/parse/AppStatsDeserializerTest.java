package com.urbanairship.api.reports.parse;


import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.reports.model.AppStats;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppStatsDeserializerTest {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();
    private static final Logger log = LogManager.getLogger(AppStatsDeserializerTest.class);

    @Test
    public void testAppStatsJsonDeSerialize() throws Exception {

        String json = "{\n" +
                "        \"c2dm_messages\": 1,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 4,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"android_messages\": 5,\n" +
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    }";

        AppStats obj = mapper.readValue(json, AppStats.class);
        assertNotNull(obj);

        assertEquals(new DateTime(2014, 6, 22, 0, 0, 0, 0), obj.getStart());
        assertEquals(1, obj.getC2DMCount());
        assertEquals(2, obj.getGCMCount());
        assertEquals(3, obj.getiOSCount());
        assertEquals(4, obj.getWindows8Count());
        assertEquals(6, obj.getWindowsPhone8Count());
        assertEquals(7, obj.getBlackBerryCount());
    }

    @Test
    public void testArrayOfAppStatsDeserializerTest() throws Exception {

        String json = "[\n" +
                "    {\n" +
                "        \"c2dm_messages\": 1,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 4,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"android_messages\": 5,\n" +
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 8,\n" +
                "        \"gcm_messages\": 9,\n" +
                "        \"messages\": 10,\n" +
                "        \"wns_messages\": 11,\n" +
                "        \"start\": \"2014-06-22 01:00:00\",\n" +
                "        \"android_messages\": 12,\n" +
                "        \"mpns_messages\": 13,\n" +
                "        \"bb_messages\": 14\n" +
                "    },\n" +
                "    {\n" +
                "        \"messages\": 15,\n" +
                "        \"mpns_messages\": 16,\n" +
                "        \"gcm_messages\": 17,\n" +
                "        \"c2dm_messages\": 0,\n" +
                "        \"wns_messages\": 18,\n" +
                "        \"start\": \"2014-06-22 02:00:00\",\n" +
                "        \"android_messages\": 19,\n" +
                "        \"bb_messages\": 20\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 21,\n" +
                "        \"gcm_messages\": 22,\n" +
                "        \"messages\": 23,\n" +
                "        \"wns_messages\": 24,\n" +
                "        \"start\": \"2014-06-22 03:00:00\",\n" +
                "        \"android_messages\": 25,\n" +
                "        \"mpns_messages\": 26,\n" +
                "        \"bb_messages\": 27\n" +
                "    }\n" +
                "]";

        List<AppStats> listOfAppStats = mapper.readValue(json, new TypeReference<List<AppStats>>() {
        });
        assertNotNull(listOfAppStats);

        assertEquals(4, listOfAppStats.size());
        AppStats one = listOfAppStats.get(0);

        assertEquals(3, one.getiOSCount());

        AppStats two = listOfAppStats.get(1);

        assertEquals(10, two.getiOSCount());

        AppStats three = listOfAppStats.get(2);

        assertEquals(15, three.getiOSCount());

        AppStats four = listOfAppStats.get(3);

        assertEquals(23, four.getiOSCount());
    }

    @Test(expected = APIParsingException.class)
    public void testMissingStartDate() throws Exception {
        String json = "{\n" +
                "        \"c2dm_messages\": 1,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 4,\n" +
                "        \"android_messages\": 5,\n" +
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    }";

        mapper.readValue(json, AppStats.class);
    }
}
