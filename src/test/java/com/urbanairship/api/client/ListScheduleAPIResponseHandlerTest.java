package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ListScheduleAPIResponseHandlerTest {

    /* Header keys, values */
    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_JSON_RESPONSE =
            "application/vnd.urbanairship+json; version=3; charset=utf8;";

    @Test
    public void testHandleSuccess() {

        String listscheduleresponse = "{\"schedule\":{\"scheduled_time\":\"2015-08-07T22:10:44\"},\"name\":\"Special Scheduled Push 20\",\"push\":{\"audience\":\"ALL\",\"device_types\":\"all\",\"notification\":{\"alert\":\"Scheduled Push 20\"}},\"push_ids\":[\"274f9aa4-2d00-4911-a043-70129f29adf2\"]}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(listscheduleresponse.getBytes()),
                listscheduleresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<SchedulePayload> handler =
                new APIClientResponseHandler<SchedulePayload>(SchedulePayload.class);

        try {
            APIClientResponse<SchedulePayload> response =
                    handler.handleResponse(httpResponse);
            assertTrue("Name incorrect",
                    response.getApiResponse().getName().get().equals("Special Scheduled Push 20"));
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testAPIV3Error() {
        String errorJson = "{\"ok\" : false,\"operation_id\" : \"OpID\"," +
                "\"error\" : \"Could not parse request body\"," +
                "\"error_code\" : 40000," +
                "\"details\" : {\"error\" : \"Unexpected token '#'\"," +
                "\"location\" : {\"line\" : 10,\"column\" : 3}}}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 400, "Bad Request"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(errorJson.getBytes()),
                errorJson.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY,
                UA_JSON_RESPONSE));

        APIClientResponseHandler<SchedulePayload> handler =
                new APIClientResponseHandler<SchedulePayload>(SchedulePayload.class);

        try {
            handler.handleResponse(httpResponse);
        } catch (APIRequestException ex) {
            APIErrorDetails details = ex.getError().get().getDetails().get();
            assertTrue("Incorrect error details", details.getError().equals("Unexpected token '#'"));
            assertTrue("HttpResponse set incorrectly", ex.getHttpResponse().equals(httpResponse));
            return;
        } catch (Exception ex) {
            fail("Incorrect exception thrown " + ex);
        }
        fail("Test should have succeeded by now");
    }

    @Test
    public void testDeprecatedJSONError() {
        /* Build a BasicHttpResponse */
        String pushJSON = "{\"message\":\"Unauthorized\"}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 400, "Unauthorized"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(pushJSON.getBytes()),
                pushJSON.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_JSON));

        APIClientResponseHandler<SchedulePayload> handler =
                new APIClientResponseHandler<SchedulePayload>(SchedulePayload.class);

        try {
            handler.handleResponse(httpResponse);
        } catch (APIRequestException ex) {

            APIError error = ex.getError().get();
            String errorMessage = error.getError();
            assertTrue("Error message incorrect", errorMessage.equals("Unauthorized"));
            return;
        } catch (Exception ex) {
            fail("Failed with incorrect exception " + ex);
        }
        fail("Test should have succeeded by now");
    }

    /**
     * Test the deprecated API response where only a string is returned.
     */
    @Test
    public void testDeprecatedStringError() {
        // Build a BasicHttpResponse
        String errorString = "Unauthorized";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 400, "Unauthorized"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(errorString.getBytes()),
                errorString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY,
                CONTENT_TYPE_TEXT_HTML));

        APIClientResponseHandler<SchedulePayload> handler =
                new APIClientResponseHandler<SchedulePayload>(SchedulePayload.class);

        try {
            handler.handleResponse(httpResponse);
        } catch (APIRequestException ex) {
            APIError error = ex.getError().get();
            assertTrue("String error message is incorrect",
                    error.getError().equals("Unauthorized"));
            return;
        } catch (Exception ex) {
            fail("Failed with incorrect exception " + ex);
        }
        fail("Test should have succeeded by now");
    }
}
