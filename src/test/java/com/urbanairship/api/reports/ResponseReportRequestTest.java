package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.ResponseReport;
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

public class ResponseReportRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));

    ResponseReportRequest responseReportRequest;
    ResponseReportRequest nextPageRequest;

    String responseReportUri = "https://go.urbanairship.com/api/reports/responses/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&precision=DAILY";
    String nextPageUri = "https://go.urbanairship.com/api/reports/opens/?start=2014-05-05T03:00:00.000Z&end=2014-11-29T00:00:00.000Z&precision=HOURLY";

    @Before
    public void setupCreate() {
        responseReportRequest = ResponseReportRequest.newRequest(start, end, Precision.DAILY);

        nextPageRequest = ResponseReportRequest.newRequest(start, end, Precision.DAILY, URI.create(nextPageUri));
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(responseReportRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(nextPageRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(responseReportRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(nextPageRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(responseReportRequest.getRequestBody(), null);
        assertEquals(nextPageRequest.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(responseReportRequest.getRequestHeaders(), headers);
        assertEquals(nextPageRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseReportUri = URI.create(responseReportUri);
        assertEquals(responseReportRequest.getUri(baseURI), expectedResponseReportUri);
        assertEquals(nextPageRequest.getUri(baseURI), URI.create(nextPageUri));
    }

    @Test
    public void testReportParser() throws Exception {
        ResponseParser<ResponseReport> responseParser = new ResponseParser<ResponseReport>() {
            @Override
            public ResponseReport parse(String response) throws IOException {
               return mapper.readValue(response, ResponseReport.class);
            }
        };

        String response =
                "{\n" +
                "   \"next_page\":\"Another Page, What Up!\",\n" +
                "   \"responses\":[\n" +
                "     {\n" +
                "       \"date\":\"2013-07-01 00:00:00\",\n" +
                "       \"ios\": {\n" +
                "           \"direct\":1337,\n" +
                "           \"influenced\":9999\n" +
                "       },\n" +
                "       \"android\": {\n" +
                "           \"direct\":7331,\n" +
                "           \"influenced\":8888\n" +
                "       }\n" +
                "     },\n" +
                "     {\n" +
                "       \"android\": {\n" +
                "           \"direct\":1996,\n" +
                "           \"influenced\":1234\n" +
                "       },\n" +
                "       \"date\":\"2015-10-15 11:22:33\",\n" +
                "       \"ios\": {\n" +
                "           \"direct\":5813,\n" +
                "           \"influenced\":1123\n" +
                "       }\n" +
                "     }\n" +
                "   ]\n" +
                "}";

        assertEquals(responseReportRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
