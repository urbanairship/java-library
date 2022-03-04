package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertEquals;


public class StatisticsCsvRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));
    String queryPathString = "/api/push/stats/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&format=csv";

    private StatisticsCsvRequest setup() {
        return StatisticsCsvRequest.newRequest(start, end);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(this.setup().getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(this.setup().getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(this.setup().getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        assertEquals(this.setup().getRequestHeaders(), null);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com" + queryPathString);
        assertEquals(this.setup().getUri(baseURI), expectedURI);
    }

    @Test
    public void testStatisticsParser() throws Exception {
        ResponseParser<String> responseParser = new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };

        String response = "2014-10-03 15:00:00,115,0,0,0,130,0,0\n" +
            "2014-10-03 16:00:00,124,0,0,0,132,0,0\n" +
            "2014-10-03 17:00:00,7,0,0,0,76,0,0\n" +
            "2014-10-03 18:00:00,19,0,0,0,70,0,0\n" +
            "2014-10-03 19:00:00,0,0,0,0,60,0,0\n" +
            "2014-10-03 20:00:00,241,0,0,0,202,0,0\n" +
            "2014-10-03 21:00:00,609,0,0,0,422,0,0\n" +
            "2014-10-03 22:00:00,976,0,0,0,629,0,0\n" +
            "2014-10-03 23:00:00,122,0,0,0,60,0,0\n" +
            "2014-10-04 00:00:00,2,0,0,0,61,0,0";

        assertEquals(this.setup().getResponseParser().parse(response), responseParser.parse(response));
    }


}
