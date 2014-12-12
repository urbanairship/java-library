package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
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

public class ListAllChannelsAPIResponseHandlerTest {

    /* Header keys, values */
    public final static String CONTENT_TYPE_KEY = "Content-type";
    public final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_JSON_RESPONSE =
            "application/vnd.urbanairship+json; version=3; charset=utf8;";

    @Test
    public void testHandleSuccess() {

        String fiveresponse = "{\n" +
                "  \"ok\": true,\n" +
                "  \"channels\": [\n" +
                "    {\n" +
                "      \"channel_id\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2012-06-05T20:37:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"test01\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00662346-9e39-4f5f-80e7-3f8fae58863c\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw\",\n" +
                "      \"created\": \"2014-03-06T18:52:59\",\n" +
                "      \"last_registration\": \"2014-10-07T21:28:35\",\n" +
                "      \"alias\": \"aaron-device\",\n" +
                "      \"tags\": [\n" +
                "        \"aaron-tag\",\n" +
                "        \"rhtgeg\",\n" +
                "        \"tnrvrg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d174cd-0a31-427e-95c9-52d5785bcd50\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614\",\n" +
                "      \"created\": \"2014-07-09T18:08:37\",\n" +
                "      \"last_registration\": \"2014-10-02T01:41:42\",\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"version_1.5.0\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": \"17:00\",\n" +
                "          \"end\": \"9:00\"\n" +
                "        },\n" +
                "        \"tz\": \"America\\/Los_Angeles\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d8cb94-eac9-49fb-bad0-29298a06730e\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": \"21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653\",\n" +
                "      \"created\": \"2014-02-22T22:48:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": \"iPhone 7,1\",\n" +
                "      \"tags\": [\n" +
                "        \"kablam\",\n" +
                "        \"version_1.3\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": null,\n" +
                "          \"end\": null\n" +
                "        },\n" +
                "        \"tz\": null\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"01257ecd-8182-41fe-a741-9fed91b993cb\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2013-01-25T00:55:05\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
                "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
                new ByteArrayInputStream(fiveresponse.getBytes()),
                fiveresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        ListAllChannelsAPIResponseHandler handler = new ListAllChannelsAPIResponseHandler();

        try {
            APIClientResponse<APIListAllChannelsResponse> response =
                    handler.handleResponse(httpResponse);
            assertTrue("Count incorrect",
                    response.getApiResponse().getChannelObjects().size() == 5);
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

        ListAllChannelsAPIResponseHandler handler = new ListAllChannelsAPIResponseHandler();

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

        ListAllChannelsAPIResponseHandler handler = new ListAllChannelsAPIResponseHandler();

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

        ListAllChannelsAPIResponseHandler handler = new ListAllChannelsAPIResponseHandler();

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
