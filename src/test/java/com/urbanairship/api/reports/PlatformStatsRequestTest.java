package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PlatformStatsResponse;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PlatformStatsRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));

    PlatformStatsRequest timeInAppRequest;
    PlatformStatsRequest appOpensRequest;
    PlatformStatsRequest optInsRequest;
    PlatformStatsRequest optOutsRequest;
    PlatformStatsRequest pushSendsRequest;
    PlatformStatsRequest nextPageRequest;

    String appOpensUri = "https://go.urbanairship.com/api/reports/opens/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String timeInAppUri = "https://go.urbanairship.com/api/reports/timeinapp/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String optInsUri = "https://go.urbanairship.com/api/reports/optins/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String optOutsUri = "https://go.urbanairship.com/api/reports/optouts/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String pushSendsUri = "https://go.urbanairship.com/api/reports/sends/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String nextPageUri = "https://go.urbanairship.com/api/reports/opens/?start=2014-05-05T03:00:00.000Z&end=2014-11-29T00:00:00.000Z&precision=HOURLY";

    @Before
    public void setupCreate() {
        timeInAppRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.TIME_IN_APP)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY);

        appOpensRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.APP_OPENS)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY);

        optInsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.OPT_INS)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY);

        optOutsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.OPT_OUTS)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY);

        pushSendsRequest = PlatformStatsRequest.newRequest(PlatformStatsRequestType.SENDS)
                .setStart(start)
                .setEnd(end)
                .setPrecision(Precision.DAILY);

        nextPageRequest = PlatformStatsRequest.newRequest(URI.create(nextPageUri));
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(timeInAppRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(appOpensRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(optInsRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(optOutsRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(pushSendsRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(nextPageRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(timeInAppRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(appOpensRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(optInsRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(optOutsRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(pushSendsRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(nextPageRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(timeInAppRequest.getRequestBody(), null);
        assertEquals(appOpensRequest.getRequestBody(), null);
        assertEquals(optInsRequest.getRequestBody(), null);
        assertEquals(optOutsRequest.getRequestBody(), null);
        assertEquals(pushSendsRequest.getRequestBody(), null);
        assertEquals(nextPageRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(timeInAppRequest.getRequestHeaders(), headers);
        assertEquals(appOpensRequest.getRequestHeaders(), headers);
        assertEquals(optInsRequest.getRequestHeaders(), headers);
        assertEquals(optOutsRequest.getRequestHeaders(), headers);
        assertEquals(pushSendsRequest.getRequestHeaders(), headers);
        assertEquals(nextPageRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedTimeInAppURI = URI.create(timeInAppUri);
        assertEquals(timeInAppRequest.getUri(baseURI), expectedTimeInAppURI);
        URI expectedAppOpensUri = URI.create(appOpensUri);
        assertEquals(appOpensRequest.getUri(baseURI), expectedAppOpensUri);
        URI expectedOptInsUri = URI.create(optInsUri);
        assertEquals(optInsRequest.getUri(baseURI), expectedOptInsUri);
        URI expectedOptOutsUri = URI.create(optOutsUri);
        assertEquals(optOutsRequest.getUri(baseURI), expectedOptOutsUri);
        URI expectedPushSendsUri = URI.create(pushSendsUri);
        assertEquals(pushSendsRequest.getUri(baseURI), expectedPushSendsUri);
        assertEquals(nextPageRequest.getUri(baseURI), URI.create(nextPageUri));
    }

    @Test
    public void testPlatformStatsParser() throws Exception {
        ResponseParser<PlatformStatsResponse> responseParser = new ResponseParser<PlatformStatsResponse>() {
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

        assertEquals(timeInAppRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

}
