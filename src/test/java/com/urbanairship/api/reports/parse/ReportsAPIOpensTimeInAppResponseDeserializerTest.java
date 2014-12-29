/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.Opens;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.TimeInApp;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReportsAPIOpensTimeInAppResponseDeserializerTest {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testReportsAPIOpensResponseDeserializer() throws IOException {

        String json = "{  \n" +
                "  \"opens\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":1470,\n" +
                "      \"android\":458\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":1662,\n" +
                "      \"android\":523\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ReportsAPIOpensResponse obj = mapper.readValue(json, ReportsAPIOpensResponse.class);
        assertNotNull(obj);

        assertEquals(2, obj.getObject().size());

        Opens opens1 = obj.getObject().get(0);

        assertEquals(458, opens1.getAndroid());
        assertEquals(1470, opens1.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), opens1.getDate());

        Opens opens2 = obj.getObject().get(1);

        assertEquals(523, opens2.getAndroid());
        assertEquals(1662, opens2.getIos());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), opens2.getDate());
    }

    @Test
    public void testReportsAPITimeInAppResponseDeserializer() throws IOException {

        String json = "{  \n" +
                "  \"timeinapp\":[  \n" +
                "    {  \n" +
                "      \"date\":\"2013-07-01 00:00:00\",\n" +
                "      \"ios\":145436.44,\n" +
                "      \"android\":193246.86\n" +
                "    },\n" +
                "    {  \n" +
                "      \"date\":\"2013-08-01 00:00:00\",\n" +
                "      \"ios\":45608.027,\n" +
                "      \"android\":100203.02\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ReportsAPITimeInAppResponse obj = mapper.readValue(json, ReportsAPITimeInAppResponse.class);
        assertNotNull(obj);

        System.out.println(obj);

        TimeInApp tia1 = obj.getObject().get(0);
        assertEquals(193246.86, tia1.getAndroid(), 0.01);
        assertEquals(145436.4375, tia1.getIos(), 0.01);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-01 00:00:00"), tia1.getDate());

        TimeInApp tia2 = obj.getObject().get(1);
        assertEquals(100203.02, tia2.getAndroid(), 0.01);
        assertEquals(45608.027, tia2.getIos(), 0.01);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-08-01 00:00:00"), tia2.getDate());
    }
}
