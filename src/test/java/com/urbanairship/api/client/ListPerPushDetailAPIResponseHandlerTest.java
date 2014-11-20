/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
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

public class ListPerPushDetailAPIResponseHandlerTest {

    /* Header keys, values */
    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_JSON_RESPONSE =
            "application/vnd.urbanairship+json; version=3; charset=utf8;";

    @Test
    public void testHandleSuccess(){

        String responseString = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"created\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_body\":\"PEJhc2U2NC1lbmNvZGVkIHN0cmluZz4=\",\n" +
                "  \"rich_deletions\":1,\n" +
                "  \"rich_responses\":2,\n" +
                "  \"rich_sends\":3,\n" +
                "  \"sends\":58,\n" +
                "  \"direct_responses\":4,\n" +
                "  \"influenced_responses\":5,\n" +
                "  \"platforms\":{  \n" +
                "    \"android\":{  \n" +
                "      \"direct_responses\":6,\n" +
                "      \"influenced_responses\":7,\n" +
                "      \"sends\":22\n" +
                "    },\n" +
                "    \"ios\":{  \n" +
                "      \"direct_responses\":8,\n" +
                "      \"influenced_responses\":9,\n" +
                "      \"sends\":36\n" +
                "    }\n" +
                "  }\n" +
                "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(responseString.getBytes()),
                responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        ListPerPushDetailAPIResponseHandler handler = new ListPerPushDetailAPIResponseHandler();

        try {
            APIClientResponse<PerPushDetailResponse> response =
                    handler.handleResponse(httpResponse);
            assertTrue("HttpResponse is incorrect",
                    httpResponse.equals(httpResponse));
        }
        catch (Exception ex){
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
    public void testAPIV3Error(){
        String errorJson = "{\"ok\" : false,\"operation_id\" : \"OpID\"," +
                "\"error\" : \"Could not parse request body\"," +
                "\"error_code\" : 40000," +
                "\"details\" : {\"error\" : \"Unexpected token '#'\"," +
                "\"location\" : {\"line\" : 10,\"column\" : 3}}}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 400, "Bad Request"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(errorJson.getBytes()),
                errorJson.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY,
                UA_JSON_RESPONSE));

        ListPerPushDetailAPIResponseHandler handler = new ListPerPushDetailAPIResponseHandler();

        try{
            handler.handleResponse(httpResponse);
        }
        catch (APIRequestException ex){
            APIErrorDetails details = ex.getError().get().getDetails().get();
            assertTrue("Incorrect error details", details.getError().equals("Unexpected token '#'"));
            assertTrue("HttpResponse set incorrectly", ex.getHttpResponse().equals(httpResponse));
            return;
        }
        catch (Exception ex){
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
    public void testDeprecatedJSONError(){
        /* Build a BasicHttpResponse */
        String pushJSON = "{\"message\":\"Unauthorized\"}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 400, "Unauthorized"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(pushJSON.getBytes()),
                pushJSON.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_JSON));

        ListPerPushDetailAPIResponseHandler handler = new ListPerPushDetailAPIResponseHandler();

        try {
            handler.handleResponse(httpResponse);
        }
        catch (APIRequestException ex){

            APIError error = ex.getError().get();
            String errorMessage = error.getError();
            assertTrue("Error message incorrect", errorMessage.equals("Unauthorized"));
            return;
        }
        catch (Exception ex){
            fail("Failed with incorrect exception " + ex);
        }
        fail("Test should have succeeded by now");
    }

    /**
     Test the deprecated API response where only a string is returned.
     */
    @Test
    public void testDeprecatedStringError(){
        // Build a BasicHttpResponse
        String errorString = "Unauthorized";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 400, "Unauthorized"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(errorString.getBytes()),
                errorString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        httpResponse.setHeader(new BasicHeader(CONTENT_TYPE_KEY,
                CONTENT_TYPE_TEXT_HTML));

        ListPerPushDetailAPIResponseHandler handler = new ListPerPushDetailAPIResponseHandler();

        try{
            handler.handleResponse(httpResponse);
        }
        catch (APIRequestException ex){
            APIError error = ex.getError().get();
            assertTrue("String error message is incorrect",
                    error.getError().equals("Unauthorized"));
            return;
        }
        catch (Exception ex){
            fail("Failed with incorrect exception " + ex);
        }
        fail("Test should have succeeded by now");
    }
}
