/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ListIndividualPushAPIResponseHandlerTest {

    /* Header keys, values */
    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_JSON_RESPONSE =
            "application/vnd.urbanairship+json; version=3; charset=utf8;";

    @Test
    public void testHandleSuccess() {

        String responseString = "{  \n" +
                "  \"push_uuid\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\",\n" +
                "  \"push_time\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_type\":\"BROADCAST_PUSH\",\n" +
                "  \"direct_responses\":4,\n" +
                "  \"sends\":176,\n" +
                "  \"group_id\":\"5e42ddfc-fa2d-11e2-9ca2-90e2ba025cd0\"\n" +
                "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(responseString.getBytes()),
                responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);

        APIClientResponseHandler<SinglePushInfoResponse> handler =
                new APIClientResponseHandler<SinglePushInfoResponse>(SinglePushInfoResponse.class);

        try {
            APIClientResponse<SinglePushInfoResponse> response = handler.handleResponse(httpResponse);
            assertNotNull(response.getApiResponse());
            assertTrue("HttpResponse is incorrect",
                    httpResponse.equals(httpResponse));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    /*
    Test that a failed message generates the proper exception, with the
    appropriate data. The APIError, APIErrorDetails, and Location objects
    are tested in their respective test classes, this test only verifies
    that they were properly setup during the process of building the
    exception
     */
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

        APIClientResponseHandler<SinglePushInfoResponse> handler =
                new APIClientResponseHandler<SinglePushInfoResponse>(SinglePushInfoResponse.class);

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

    /*
     Test for error where the default JSON error message is returned instead
     of the v3 error message is returned. Default message json is in the form
     {"message":"description"}
     */
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

        APIClientResponseHandler<SinglePushInfoResponse> handler =
                new APIClientResponseHandler<SinglePushInfoResponse>(SinglePushInfoResponse.class);

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

        APIClientResponseHandler<SinglePushInfoResponse> handler =
                new APIClientResponseHandler<SinglePushInfoResponse>(SinglePushInfoResponse.class);

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
