package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import com.urbanairship.api.client.model.APIListAllSchedulesResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListSingleChannelResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.client.model.APIPushResponse;
import com.urbanairship.api.client.model.APIReportsPushListingResponse;
import com.urbanairship.api.client.model.APIScheduleResponse;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import com.urbanairship.api.reports.model.SinglePushInfoResponse;
import com.urbanairship.api.schedule.model.SchedulePayload;
import com.urbanairship.api.segments.model.AudienceSegment;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class APIClientResponseSuccessHandlerTest {

    @Test
    public void testAppsOpenReportHandleSuccess() {

        String responseString = "{  \n" +
            "  \"opens\":[  \n" +
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

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(responseString.getBytes()),
            responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<ReportsAPIOpensResponse> handler =
            new APIClientResponseHandler<ReportsAPIOpensResponse>(ReportsAPIOpensResponse.class);

        try {
            APIClientResponse<ReportsAPIOpensResponse> response = handler.handleResponse(httpResponse);
            assertTrue("HttpResponse is incorrect",
                response.getHttpResponse().equals(httpResponse));

            assertNotNull(response.getApiResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testAudienceSegmentHandleSuccess() {

        String testresponse = "{  \n" +
            "  \"display_name\":\"2014-11-07T14:26:56.749-08:00\",\n" +
            "  \"criteria\":{  \n" +
            "    \"and\":[  \n" +
            "      {  \n" +
            "        \"location\":{  \n" +
            "          \"us_state\":\"OR\",\n" +
            "          \"date\":{  \n" +
            "            \"days\":{  \n" +
            "              \"start\":\"2014-11-02\",\n" +
            "              \"end\":\"2014-11-07\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {  \n" +
            "        \"location\":{  \n" +
            "          \"us_state\":\"CA\",\n" +
            "          \"date\":{  \n" +
            "            \"recent\":{  \n" +
            "              \"months\":3\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {  \n" +
            "        \"or\":[  \n" +
            "          {  \n" +
            "            \"tag\":\"tag1\"\n" +
            "          },\n" +
            "          {  \n" +
            "            \"tag\":\"tag2\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {  \n" +
            "        \"not\":{  \n" +
            "          \"tag\":\"not-tag\"\n" +
            "        }\n" +
            "      },\n" +
            "      {  \n" +
            "        \"not\":{  \n" +
            "          \"and\":[  \n" +
            "            {  \n" +
            "              \"location\":{  \n" +
            "                \"us_state\":\"WA\",\n" +
            "                \"date\":{  \n" +
            "                  \"months\":{  \n" +
            "                    \"start\":\"2011-05\",\n" +
            "                    \"end\":\"2012-02\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            {  \n" +
            "              \"tag\":\"woot\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(testresponse.getBytes()),
            testresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<AudienceSegment> handler =
            new APIClientResponseHandler<AudienceSegment>(AudienceSegment.class);

        try {
            APIClientResponse<AudienceSegment> response = handler.handleResponse(httpResponse);
            assertNotNull(response);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testListAllChannelsHandleSuccess() {

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
        APIClientResponseHandler<APIListAllChannelsResponse> handler =
            new APIClientResponseHandler<APIListAllChannelsResponse>(APIListAllChannelsResponse.class);

        try {
            APIClientResponse<APIListAllChannelsResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue(response.getApiResponse().getOk());
            assertTrue("Count incorrect",
                response.getApiResponse().getChannelObjects().size() == 5);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testListAllSchedulesHandleSuccess() {

        String listscheduleresponse = "{ \n" +
            "  \"ok\":true, \n" +
            "  \"count\":5, \n" +
            "  \"total_count\":6, \n" +
            "  \"schedules\": [ \n" +
            "    { \n" +
            "      \"url\":\"https://go.urbanairship.com/api/schedules/5a60e0a6-9aa7-449f-a038-6806e572baf3\", \n" +
            "      \"schedule\":{ \n" +
            "        \"scheduled_time\":\"2015-01-01T08:00:00\" \n" +
            "      }, \n" +
            "      \"push\":{\"audience\":\"ALL\", \n" +
            "      \"device_types\":[\"android\",\"ios\"], \n" +
            "      \"notification\":{\"alert\":\"Happy New Year 2015!\",\"android\":{},\"ios\":{}}}, \n" +
            "      \"push_ids\":[\"8430f2e0-ec07-4c1e-adc4-0c7c7978e648\"] \n" +
            "    }, \n" +
            "    { \n" +
            "      \"url\":\"https://go.urbanairship.com/api/schedules/f53aa2bd-018a-4482-8d7d-691d13407973\", \n" +
            "      \"schedule\":{ \n" +
            "        \"scheduled_time\":\"2016-01-01T08:00:00\" \n" +
            "      }, \n" +
            "      \"push\":{\"audience\":\"ALL\", \n" +
            "      \"device_types\":[\"android\",\"ios\"], \n" +
            "      \"notification\":{\"alert\":\"Happy New Year 2016!\",\"android\":{},\"ios\":{}}}, \n" +
            "      \"push_ids\":[\"b217a321-922f-4aee-b239-ca1b58c6b652\"] \n" +
            "    } \n" +
            "  ] \n" +
            "} \n";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(listscheduleresponse.getBytes()),
            listscheduleresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);

        APIClientResponseHandler<APIListAllSchedulesResponse> handler =
            new APIClientResponseHandler<APIListAllSchedulesResponse>(APIListAllSchedulesResponse.class);


        try {
            APIClientResponse<APIListAllSchedulesResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("Count incorrect",
                response.getApiResponse().getCount() == 5);
            assertTrue(response.getApiResponse().getOk());
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testAllSegmentsHandleSuccess() {

        String testresponse = "{\n" +
            "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
            "   \"segments\": [\n" +
            "      {\n" +
            "         \"creation_date\": 1346248822220,\n" +
            "         \"display_name\": \"A segment\",\n" +
            "         \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
            "         \"modification_date\": 1346248822221\n" +
            "      }\n" +
            "   ]\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(testresponse.getBytes()),
            testresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIListAllSegmentsResponse> handler =
            new APIClientResponseHandler<APIListAllSegmentsResponse>(APIListAllSegmentsResponse.class);

        try {
            APIClientResponse<APIListAllSegmentsResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("NextPage incorrect", response.getApiResponse().getNextPage().equals("https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64"));
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testSinglePushInfoHandleSuccess() {

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
                httpResponse.equals(response.getHttpResponse()));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

    @Test
    public void testPerPushDetailHandleSuccess() {

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
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(responseString.getBytes()),
            responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<PerPushDetailResponse> handler =
            new APIClientResponseHandler<PerPushDetailResponse>(PerPushDetailResponse.class);

        try {
            APIClientResponse<PerPushDetailResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("HttpResponse is incorrect",
                httpResponse.equals(response.getHttpResponse()));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testPerPushSeriesHandleSuccess() {

        String responseString = "{  \n" +
            "  \"app_key\":\"some_app_key\",\n" +
            "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
            "  \"start\":\"2013-07-25 23:00:00\",\n" +
            "  \"end\":\"2013-07-26 11:00:00\",\n" +
            "  \"precision\":\"HOURLY\",\n" +
            "  \"counts\":[  \n" +
            "    {  \n" +
            "      \"push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"direct_responses\":1,\n" +
            "          \"influenced_responses\":2,\n" +
            "          \"sends\":58\n" +
            "        },\n" +
            "        \"android\":{  \n" +
            "          \"direct_responses\":3,\n" +
            "          \"influenced_responses\":4,\n" +
            "          \"sends\":22\n" +
            "        },\n" +
            "        \"ios\":{  \n" +
            "          \"direct_responses\":5,\n" +
            "          \"influenced_responses\":6,\n" +
            "          \"sends\":36\n" +
            "        }\n" +
            "      },\n" +
            "      \"rich_push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"responses\":7,\n" +
            "          \"sends\":8\n" +
            "        }\n" +
            "      },\n" +
            "      \"time\":\"2013-07-25 23:00:00\"\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"direct_responses\":9,\n" +
            "          \"influenced_responses\":10,\n" +
            "          \"sends\":11\n" +
            "        },\n" +
            "        \"android\":{  \n" +
            "          \"direct_responses\":12,\n" +
            "          \"influenced_responses\":13,\n" +
            "          \"sends\":14\n" +
            "        },\n" +
            "        \"ios\":{  \n" +
            "          \"direct_responses\":15,\n" +
            "          \"influenced_responses\":16,\n" +
            "          \"sends\":17\n" +
            "        }\n" +
            "      },\n" +
            "      \"rich_push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"responses\":18,\n" +
            "          \"sends\":19\n" +
            "        }\n" +
            "      },\n" +
            "      \"time\":\"2013-07-26 00:00:00\"\n" +
            "    },\n" +
            "    {  \n" +
            "      \"push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"direct_responses\":20,\n" +
            "          \"influenced_responses\":21,\n" +
            "          \"sends\":22\n" +
            "        },\n" +
            "        \"android\":{  \n" +
            "          \"direct_responses\":23,\n" +
            "          \"influenced_responses\":24,\n" +
            "          \"sends\":25\n" +
            "        },\n" +
            "        \"ios\":{  \n" +
            "          \"direct_responses\":26,\n" +
            "          \"influenced_responses\":27,\n" +
            "          \"sends\":28\n" +
            "        }\n" +
            "      },\n" +
            "      \"rich_push_platforms\":{  \n" +
            "        \"all\":{  \n" +
            "          \"responses\":29,\n" +
            "          \"sends\":30\n" +
            "        }\n" +
            "      },\n" +
            "      \"time\":\"2013-07-26 01:00:00\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(responseString.getBytes()),
            responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<PerPushSeriesResponse> handler =
            new APIClientResponseHandler<PerPushSeriesResponse>(PerPushSeriesResponse.class);

        try {
            APIClientResponse<PerPushSeriesResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("HttpResponse is incorrect",
                httpResponse.equals(response.getHttpResponse()));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testReportsPushListingHandleSuccess() {

        String fiveresponse = "{  \n" +
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

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(fiveresponse.getBytes()),
            fiveresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIReportsPushListingResponse> handler =
            new APIClientResponseHandler<APIReportsPushListingResponse>(APIReportsPushListingResponse.class);

        try {
            APIClientResponse<APIReportsPushListingResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("Count incorrect",
                response.getApiResponse().getSinglePushInfoResponseObjects().size() == 5);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testListScheduleHandleSuccess() {

        String listscheduleresponse = "{ \n" +
            "  \"schedule\":{ \n" +
            "    \"scheduled_time\":\"2015-08-07T22:10:44\" \n" +
            "  }, \n" +
            "  \"name\":\"Special Scheduled Push 20\", \n" +
            "  \"push\":{ \n" +
            "    \"audience\":\"ALL\", \n" +
            "    \"device_types\":\"all\", \n" +
            "    \"notification\":{\"alert\":\"Scheduled Push 20\"} \n" +
            "  }, \n" +
            "  \"push_ids\":[\"274f9aa4-2d00-4911-a043-70129f29adf2\"] \n" +
            "}";


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
    public void testListSingleChannelHandleSuccess() {

        String response = "{  \n" +
            "  \"ok\": true, \n" +
            "  \"channel\":{  \n" +
            "    \"channel_id\":\"01234567-890a-bcde-f012-34567890abc0\",\n" +
            "    \"device_type\":\"android\",\n" +
            "    \"installed\":true,\n" +
            "    \"opt_in\":false,\n" +
            "    \"push_address\":null,\n" +
            "    \"created\":\"2014-07-12T00:45:01\",\n" +
            "    \"last_registration\":\"2014-08-06T00:33:25\",\n" +
            "    \"alias\":null,\n" +
            "    \"tags\":[  \n" +
            "\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(response.getBytes()),
            response.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIListSingleChannelResponse> handler =
            new APIClientResponseHandler<APIListSingleChannelResponse>(APIListSingleChannelResponse.class);

        try {
            APIClientResponse<APIListSingleChannelResponse> res =
                handler.handleResponse(httpResponse);
            assertNotNull(res);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
            assertTrue(res.getApiResponse().getOk());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception " + ex);
        }

    }

    @Test
    public void testListTagsHandleSuccess() {

        String listtagresponse = "{\"tags\":[\"Puppies\",\"Kitties\",\"GrumpyCat\"]}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(listtagresponse.getBytes()),
            listtagresponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIListTagsResponse> handler =
            new APIClientResponseHandler<APIListTagsResponse>(APIListTagsResponse.class);

        try {
            APIClientResponse<APIListTagsResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("Tags incorrect",
                response.getApiResponse().getTags().contains("Puppies") &&
                    response.getApiResponse().getTags().contains("Kitties") &&
                    response.getApiResponse().getTags().contains("GrumpyCat"));
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testLocationHandleSuccess() {

        String locationResponse = "{\n" +
            "  \"features\":[\n" +
            "    {\n" +
            "      \"type\":\"Feature\",\n" +
            "      \"id\":\"4oFkxX7RcUdirjtaenEQIV\",\n" +
            "      \"properties\":{\n" +
            "        \"source\":\"tiger.census.gov\",\n" +
            "        \"boundary_type_string\":\"City/Place\",\n" +
            "        \"name\":\"San Francisco\",\n" +
            "        \"context\":{\n" +
            "          \"us_state_name\":\"California\",\n" +
            "          \"us_state\":\"CA\"\n" +
            "        },\n" +
            "        \"boundary_type\":\"city\"\n" +
            "      },\n" +
            "      \"bounds\":[\n" +
            "        37.63983,\n" +
            "        -123.173825,\n" +
            "        37.929824,\n" +
            "        -122.28178\n" +
            "      ],\n" +
            "      \"centroid\":[\n" +
            "        37.759715,\n" +
            "        -122.693976\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(locationResponse.getBytes()),
            locationResponse.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APILocationResponse> handler =
            new APIClientResponseHandler<APILocationResponse>(APILocationResponse.class);

        try {
            APIClientResponse<APILocationResponse> response =
                handler.handleResponse(httpResponse);
            assertNotNull(response);
            assertTrue(httpResponse.getStatusLine().toString().equals("HTTP/1.1 200 OK"));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testPushHandleSuccess() {
        String pushJSON = "{\n" +
            "    \"ok\" : true,\n" +
            "    \"operation_id\" : \"df6a6b50\",\n" +
            "    \"push_ids\": [\n" +
            "        \"id1\",\n" +
            "        \"id2\"\n" +
            "    ],\n" +
            "    \"message_ids\": [],\n" +
            "    \"content_urls\" : []\n" +
            "}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(pushJSON.getBytes()),
            pushJSON.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIPushResponse> handler =
            new APIClientResponseHandler<APIPushResponse>(APIPushResponse.class);
        try {
            APIClientResponse<APIPushResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("HttpResponse incorrect",
                httpResponse.equals(response.getHttpResponse()));
            assertTrue(response.getApiResponse().getOk());
            String operationId = response.getApiResponse().getOperationId().get();
            assertTrue("APIPushResponse incorrectly configured",
                "df6a6b50".equals(operationId));

        } catch (Exception ex) {
            fail("Failed with exception " + ex.getMessage());
        }
    }

    @Test
    public void testScheduleHandleSuccess() {
        String successJSON = "{\"ok\" : true, \"operation_id\" : \"OpID\", " +
            "\"schedule_urls\" : [\"ScheduleURL\"], " +
            "\"schedule_ids\" : [\"ScheduleID\"], " +
            "\"schedules\" : [\n" +
            "      {\n" +
            "         \"url\" : \"http://go.urbanairship/api/schedules/2d69320c-3c91-5241-fac4-248269eed109\",\n" +
            "         \"schedule\" : { \"scheduled_time\": \"2013-04-01T18:45:00\" },\n" +
            "         \"push\" : { \"audience\":{ \"tag\": \"spoaaaarts\" },\n" +
            "            \"notification\": { \"alert\": \"Booyah!\" },\n" +
            "            \"device_types\": \"all\" },\n" +
            "         \"push_ids\" : [ \"8f18fcb5-e2aa-4b61-b190-43852eadb5ef\" ]\n" +
            "      }\n" +
            "   ]}";
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(successJSON.getBytes()),
            successJSON.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<APIScheduleResponse> handler =
            new APIClientResponseHandler<APIScheduleResponse>(APIScheduleResponse.class);

        try {
            APIClientResponse<APIScheduleResponse> response =
                handler.handleResponse(httpResponse);
            assertTrue("Operation ID incorrect",
                response.getApiResponse().getOperationId().equals("OpID"));
            assertTrue("HttpResponse is incorrect",
                httpResponse.equals(response.getHttpResponse()));
        } catch (Exception ex) {
            fail("Exception " + ex);
        }

    }

    @Test
    public void testTimeInAppHandleSuccess() {

        String responseString = "{  \n" +
            "  \"timeinapp\":[  \n" +
            "    {  \n" +
            "      \"date\":\"2013-07-01 00:00:00\",\n" +
            "      \"ios\":145436.44,\n" +
            "      \"android\":193246.86\n" +
            "    },\n" +
            "    {  \n" +
            "      \"date\":\"2013-08-01 00:00:00\",\n" +
            "      \"ios\":45608.027,\n" +
            "      \"android\":100203.02\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
            new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        InputStreamEntity inputStreamEntity = new InputStreamEntity(
            new ByteArrayInputStream(responseString.getBytes()),
            responseString.getBytes().length);
        httpResponse.setEntity(inputStreamEntity);
        APIClientResponseHandler<ReportsAPITimeInAppResponse> handler =
            new APIClientResponseHandler<ReportsAPITimeInAppResponse>(ReportsAPITimeInAppResponse.class);

        try {

            APIClientResponse<ReportsAPITimeInAppResponse> response = handler.handleResponse(httpResponse);

            assertTrue("HttpResponse is incorrect",
                response.getHttpResponse().equals(httpResponse));

            assertNotNull(response.getApiResponse());

        } catch (Exception ex) {
            fail("Exception " + ex);
        }
    }

}
