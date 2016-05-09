package com.urbanairship.api.reports;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class PushListingRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));
    
    private PushListingRequest setup() {
        PushListingRequest listRequest = PushListingRequest.newRequest()
                .setStart(start)
                .setEnd(end)
                .setLimit(2)
                .setPushIdStart("start");

        return listRequest;
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(setup().getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(setup().getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(setup().getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(setup().getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/reports/responses/list/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&limit=2&push_id_start=start");
        assertEquals(setup().getUri(baseURI), expectedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<PushListingResponse>() {
            @Override
            public PushListingResponse parse(String response) throws IOException {
                return mapper.readValue(response, PushListingResponse.class);
            }
        };

        String response = "{  \n" +
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
                "      \"sends\":1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        assertEquals(setup().getResponseParser().parse(response), responseParser.parse(response));
    }
}
