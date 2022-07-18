package com.urbanairship.api.schedule;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;

import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ScheduleStatusRequestTest {

    ScheduleStatusRequest ScheduleStatusRequestPause = ScheduleStatusRequest.pauseScheduleRequest("id");
    ScheduleStatusRequest ScheduleStatusRequestResume = ScheduleStatusRequest.resumeScheduleRequest("id");

    @Test
    public void testContentType() throws Exception {
        assertNull(ScheduleStatusRequestPause.getContentType());
        assertNull(ScheduleStatusRequestResume.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(ScheduleStatusRequestPause.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(ScheduleStatusRequestResume.getHttpMethod(), Request.HttpMethod.POST);

    }

    @Test
    public void testBody() throws Exception {
        assertNull(ScheduleStatusRequestPause.getRequestBody());
        assertNull(ScheduleStatusRequestResume.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(ScheduleStatusRequestPause.getRequestHeaders(), headers);
        assertEquals(ScheduleStatusRequestResume.getRequestHeaders(), headers);

    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedPauseURI = URI.create("https://go.urbanairship.com/api/schedules/id/pause");
        assertEquals(ScheduleStatusRequestPause.getUri(baseURI), expectedPauseURI);

        URI expectedResumeURI = URI.create("https://go.urbanairship.com/api/schedules/id/resume");
        assertEquals(ScheduleStatusRequestResume.getUri(baseURI), expectedResumeURI);

    }

    @Test
    public void testScheduleParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(ScheduleStatusRequestResume.getResponseParser().parse(responseJson), genericResponse);

    }
}
