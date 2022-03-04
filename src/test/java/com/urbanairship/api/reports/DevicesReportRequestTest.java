package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.DevicesReport;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DevicesReportRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime date = new DateTime(2019, 03, 1, 12, 0, 0, 0, DateTimeZone.UTC);

    DevicesReportRequest devicesReportRequest;
    DevicesReportRequest devicesReportRequestWithDate;

    String responseReportUri = "https://go.urbanairship.com/api/reports/devices/";
    String responseReportUriWithDate = "https://go.urbanairship.com/api/reports/devices/?date=2019-03-01T12%3A00%3A00";

    @Before
    public void setupCreate() {
        devicesReportRequest = DevicesReportRequest.newRequest();

        devicesReportRequestWithDate = DevicesReportRequest.newRequest().setDate(date);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(devicesReportRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(devicesReportRequestWithDate.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(devicesReportRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(devicesReportRequestWithDate.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(devicesReportRequest.getRequestBody(), null);
        assertEquals(devicesReportRequestWithDate.getRequestBody(), null);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(devicesReportRequest.getRequestHeaders(), headers);
        assertEquals(devicesReportRequestWithDate.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedResponseReportUri = URI.create(responseReportUri);
        assertEquals(devicesReportRequest.getUri(baseURI), expectedResponseReportUri);
        assertEquals(devicesReportRequestWithDate.getUri(baseURI), URI.create(responseReportUriWithDate));
    }

    @Test
    public void testReportParser() throws Exception {
        ResponseParser<DevicesReport> responseParser = new ResponseParser<DevicesReport>() {
            @Override
            public DevicesReport parse(String response) throws IOException {
                return mapper.readValue(response, DevicesReport.class);
            }
        };

        String response =
                "{\n" +
                        "   \"total_unique_devices\": 13186,\n" +
                        "   \"date_closed\": \"2018-08-28 00:00:00\",\n" +
                        "   \"date_computed\": \"2018-08-29 13:30:45\",\n" +
                        "   \"counts\": {\n" +
                        "       \"ios\": {\n" +
                        "           \"unique_devices\": 231,\n" +
                        "           \"opted_in\": 142,\n" +
                        "           \"opted_out\": 89,\n" +
                        "           \"uninstalled\": 2096\n" +
                        "       },\n" +
                        "       \"android\": { \n" +
                        "           \"unique_devices\": 11795, \n" +
                        "           \"opted_in\": 226, \n" +
                        "           \"opted_out\": 11569, \n" +
                        "           \"uninstalled\": 1069 \n" +
                        "       }, \n" +
                        "       \"amazon\": { \n" +
                        "           \"unique_devices\": 29, \n" +
                        "           \"opted_in\": 22, \n" +
                        "           \"opted_out\": 7, \n" +
                        "           \"uninstalled\": 9 \n" +
                        "       }, \n" +
                        "       \"sms\": { \n" +
                        "           \"unique_devices\": 26, \n" +
                        "           \"opted_in\": 23, \n" +
                        "           \"opted_out\": 3, \n" +
                        "           \"uninstalled\": 17 \n" +
                        "       } \n" +
                        "   } \n" +
                        "}";

        assertEquals(devicesReportRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
