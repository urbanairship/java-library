package com.urbanairship.api.client;

import com.urbanairship.api.client.model.APIClientResponse;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.APIListTagsResponse;
import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.reports.model.PerPushDetailResponse;
import com.urbanairship.api.reports.model.PerPushSeriesResponse;
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

}
