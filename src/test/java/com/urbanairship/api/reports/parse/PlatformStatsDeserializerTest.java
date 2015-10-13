package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.PlatformStats;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PlatformStatsDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testStats() throws Exception {

        String json =
                "{  \n" +
                    "\"date\":\"2013-07-01 00:00:00\",\n" +
                    "\"ios\":1470,\n" +
                    "\"android\":458\n" +
                    "}";

        PlatformStats obj = mapper.readValue(json, PlatformStats.class);
        assertNotNull(obj);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), obj.getDate());
        assertEquals(1470, obj.getIos());
        assertEquals(458, obj.getAndroid());
    }
}
