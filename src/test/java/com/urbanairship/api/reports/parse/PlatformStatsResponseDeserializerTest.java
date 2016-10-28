package com.urbanairship.api.reports.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.PlatformStats;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PlatformStatsResponseDeserializerTest {
    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testTimeInAppResponseDeserializer() throws IOException {

        String tiaJson = "{\n" +
                "  \"next_page\": \"Hey Next Page\",\n" +
                "  \"timeinapp\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436,\n" +
                "      \"android\":193246\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608,\n" +
                "      \"android\":100203\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        PlatformStatsResponse timeInAppObj = mapper.readValue(tiaJson, PlatformStatsResponse.class);
        assertNotNull(timeInAppObj);

        System.out.println(timeInAppObj);

        PlatformStats platformStats1 = timeInAppObj.getPlatformStatsObjects().get().get(0);
        assertEquals(193246, platformStats1.getAndroid());
        assertEquals(145436, platformStats1.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), platformStats1.getDate());

        PlatformStats platformStats2 = timeInAppObj.getPlatformStatsObjects().get().get(1);
        assertEquals(100203, platformStats2.getAndroid());
        assertEquals(45608, platformStats2.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), platformStats2.getDate());
    }

    @Test
    public void testAppOpensDeserializer() throws IOException {

        String appOpensJson = "{\n" +
                "  \"next_page\": \"Hey Next Page\",\n" +
                "  \"opens\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436,\n" +
                "      \"android\":193246\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608,\n" +
                "      \"android\":100203\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        PlatformStatsResponse appOpensObj = mapper.readValue(appOpensJson, PlatformStatsResponse.class);
        assertNotNull(appOpensObj);

        System.out.println(appOpensObj);

        PlatformStats platformStats1 = appOpensObj.getPlatformStatsObjects().get().get(0);
        assertEquals(193246, platformStats1.getAndroid());
        assertEquals(145436, platformStats1.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), platformStats1.getDate());

        PlatformStats platformStats2 = appOpensObj.getPlatformStatsObjects().get().get(1);
        assertEquals(100203, platformStats2.getAndroid());
        assertEquals(45608, platformStats2.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), platformStats2.getDate());
    }

    @Test
    public void testOptInsDeserializer() throws IOException {

        String optInsJson = "{\n" +
                "  \"next_page\": \"Hey Next Page\",\n" +
                "  \"optins\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436,\n" +
                "      \"android\":193246\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608,\n" +
                "      \"android\":100203\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        PlatformStatsResponse optInsObj = mapper.readValue(optInsJson, PlatformStatsResponse.class);
        assertNotNull(optInsObj);

        System.out.println(optInsObj);

        PlatformStats platformStats1 = optInsObj.getPlatformStatsObjects().get().get(0);
        assertEquals(193246, platformStats1.getAndroid());
        assertEquals(145436, platformStats1.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), platformStats1.getDate());

        PlatformStats platformStats2 = optInsObj.getPlatformStatsObjects().get().get(1);
        assertEquals(100203, platformStats2.getAndroid());
        assertEquals(45608, platformStats2.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), platformStats2.getDate());
    }

    @Test
    public void testOptOutsDeserializer() throws IOException {

        String optOutsJson = "{\n" +
                "  \"next_page\": \"Hey Next Page\",\n" +
                "  \"optouts\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436,\n" +
                "      \"android\":193246\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608,\n" +
                "      \"android\":100203\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        PlatformStatsResponse optOutsObj = mapper.readValue(optOutsJson, PlatformStatsResponse.class);
        assertNotNull(optOutsObj);

        System.out.println(optOutsObj);

        PlatformStats platformStats1 = optOutsObj.getPlatformStatsObjects().get().get(0);
        assertEquals(193246, platformStats1.getAndroid());
        assertEquals(145436, platformStats1.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), platformStats1.getDate());

        PlatformStats platformStats2 = optOutsObj.getPlatformStatsObjects().get().get(1);
        assertEquals(100203, platformStats2.getAndroid());
        assertEquals(45608, platformStats2.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), platformStats2.getDate());
    }

}
