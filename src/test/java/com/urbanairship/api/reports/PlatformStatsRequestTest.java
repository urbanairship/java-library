package com.urbanairship.api.reports;


import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PlatformStatsRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0);
    DateTime end = start.plus(Period.hours(48));

    String appOpensUri = "https://go.urbanairship.com/api/reports/opens/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=DAILY";
    String timeInAppUri = "https://go.urbanairship.com/api/reports/timeinapp/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=DAILY";
    String optInsUri = "https://go.urbanairship.com/api/reports/optins/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=DAILY";
    String optOutsUri = "https://go.urbanairship.com/api/reports/optouts/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=DAILY";
    String pushSendsUri = "https://go.urbanairship.com/api/reports/sends/?start=2014-10-01T12%3A00%3A00.000-07%3A00&end=2014-10-03T12%3A00%3A00.000-07%3A00&precision=DAILY";

    private PlatformStatsRequest setupTimeInAppRequest() {
        PlatformStatsRequest timeInAppRequest = PlatformStatsRequest.newTimeInAppRequest()
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        return timeInAppRequest;
    }

    private PlatformStatsRequest setupAppOpensRequest() {
        PlatformStatsRequest appOpensRequest = PlatformStatsRequest.newAppOpensRequest()
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        return appOpensRequest;
    }

    private PlatformStatsRequest setupOptInsRequest() {
        PlatformStatsRequest optInsRequest = PlatformStatsRequest.newOptInsRequest()
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        return optInsRequest;
    }

    private PlatformStatsRequest setupOptOutsRequest() {
        PlatformStatsRequest optOutsRequest = PlatformStatsRequest.newOptOutsRequest()
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        return optOutsRequest;
    }

    private PlatformStatsRequest setupPushSendsRequest() {
        PlatformStatsRequest pushSendsRequest = PlatformStatsRequest.newPushSendsRequest()
                .start(start)
                .end(end)
                .precision(Precision.DAILY);

        return pushSendsRequest;
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(setupTimeInAppRequest().getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(setupAppOpensRequest().getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(setupOptInsRequest().getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(setupOptOutsRequest().getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(setupPushSendsRequest().getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(setupTimeInAppRequest().getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(setupAppOpensRequest().getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(setupOptInsRequest().getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(setupOptOutsRequest().getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(setupPushSendsRequest().getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(setupTimeInAppRequest().getRequestBody(), null);
        assertEquals(setupAppOpensRequest().getRequestBody(), null);
        assertEquals(setupOptInsRequest().getRequestBody(), null);
        assertEquals(setupOptOutsRequest().getRequestBody(), null);
        assertEquals(setupPushSendsRequest().getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(setupTimeInAppRequest().getRequestHeaders(), headers);
        assertEquals(setupAppOpensRequest().getRequestHeaders(), headers);
        assertEquals(setupOptInsRequest().getRequestHeaders(), headers);
        assertEquals(setupOptOutsRequest().getRequestHeaders(), headers);
        assertEquals(setupPushSendsRequest().getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedTimeInAppURI = URI.create(timeInAppUri);
        assertEquals(setupTimeInAppRequest().getUri(baseURI), expectedTimeInAppURI);
        URI expectedAppOpensUri = URI.create(appOpensUri);
        assertEquals(setupAppOpensRequest().getUri(baseURI), expectedAppOpensUri);
        URI expectedOptInsUri = URI.create(optInsUri);
        assertEquals(setupOptInsRequest().getUri(baseURI), expectedOptInsUri);
        URI expectedOptOutsUri = URI.create(optOutsUri);
        assertEquals(setupOptOutsRequest().getUri(baseURI), expectedOptOutsUri);
        URI expectedPushSendsUri = URI.create(pushSendsUri);
        assertEquals(setupPushSendsRequest().getUri(baseURI), expectedPushSendsUri);

    }

    @Test
    public void testPlatformStatsParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<PlatformStatsResponse>() {
            @Override
            public PlatformStatsResponse parse(String response) throws IOException {
                return mapper.readValue(response, PlatformStatsResponse.class);
            }
        };

        String response = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "  \"timeinapp\":[  \n" +
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

        assertEquals(setupTimeInAppRequest().getResponseParser().parse(response), responseParser.parse(response));
    }

}
