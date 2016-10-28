package com.urbanairship.api.reports.parse;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.StatisticsResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsResponseDeserializerTest {

    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testStatisticsResponseDeserializer() throws Exception {

        String json = "{\n" +
                "        \"c2dm_messages\": 1,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 4,\n" +
                "        \"start\": \"2014-06-22 00:00:00\",\n" +
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    }";

        StatisticsResponse obj = mapper.readValue(json, StatisticsResponse.class);
        assertNotNull(obj);

        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2014-06-22 00:00:00"), obj.getStart());
        assertEquals(1, obj.getC2dmCount());
        assertEquals(2, obj.getGcmCount());
        assertEquals(3, obj.getIosCount());
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
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 8,\n" +
                "        \"gcm_messages\": 9,\n" +
                "        \"messages\": 10,\n" +
                "        \"wns_messages\": 11,\n" +
                "        \"start\": \"2014-06-22 01:00:00\",\n" +
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
                "        \"bb_messages\": 20\n" +
                "    },\n" +
                "    {\n" +
                "        \"c2dm_messages\": 21,\n" +
                "        \"gcm_messages\": 22,\n" +
                "        \"messages\": 23,\n" +
                "        \"wns_messages\": 24,\n" +
                "        \"start\": \"2014-06-22 03:00:00\",\n" +
                "        \"mpns_messages\": 26,\n" +
                "        \"bb_messages\": 27\n" +
                "    }\n" +
                "]";

        List<StatisticsResponse> listOfAppStats = mapper.readValue(json, new TypeReference<List<StatisticsResponse>>() {});

        assertNotNull(listOfAppStats);
        assertEquals(4, listOfAppStats.size());
        StatisticsResponse one = listOfAppStats.get(0);
        assertEquals(3, one.getIosCount());
        StatisticsResponse two = listOfAppStats.get(1);
        assertEquals(10, two.getIosCount());
        StatisticsResponse three = listOfAppStats.get(2);
        assertEquals(15, three.getIosCount());
        StatisticsResponse four = listOfAppStats.get(3);
        assertEquals(23, four.getIosCount());
    }

    @Test(expected = APIParsingException.class)
    public void testMissingStartDate() throws Exception {
        String json = "{\n" +
                "        \"c2dm_messages\": 1,\n" +
                "        \"gcm_messages\": 2,\n" +
                "        \"messages\": 3,\n" +
                "        \"wns_messages\": 4,\n" +
                "        \"mpns_messages\": 6,\n" +
                "        \"bb_messages\": 7\n" +
                "    }";

        mapper.readValue(json, StatisticsResponse.class);
    }
}
