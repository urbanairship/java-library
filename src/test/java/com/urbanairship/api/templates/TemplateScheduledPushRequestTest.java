package com.urbanairship.api.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import com.urbanairship.api.templates.model.TemplateScheduledPushPayload;
import com.urbanairship.api.templates.model.TemplateSelector;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TemplateScheduledPushRequestTest {

    private final static ObjectMapper mapper = TemplatesObjectMapper.getInstance();
    private final static String PATH_NAME = "/api/templates/schedules/";

    TemplateScheduledPushRequest request;
    TemplateScheduledPushRequest requestValidateOnly;

    TemplateScheduledPushPayload payload = TemplateScheduledPushPayload.newBuilder()
            .setAudience(Selectors.namedUser("named_user"))
            .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
            .setMergeData(TemplateSelector.newBuilder()
                    .setTemplateId("template-id")
                    .addSubstitution("FIRST_NAME", "Firsty")
                    .addSubstitution("LAST_NAME", "Lasty")
                    .addSubstitution("TITLE", "Dr.")
                    .build())
            .setSchedule(Schedule.newBuilder().setScheduledTimestamp(DateTime.parse("2013-04-01T18:45:00Z")).build())
            .build();


    @Before
    public void setup() {
        request = TemplateScheduledPushRequest.newRequest()
                .addTemplateScheduledPushPayload(payload);
        requestValidateOnly = TemplateScheduledPushRequest.newRequest()
                .addTemplateScheduledPushPayload(payload)
                .setValidateOnly(true);
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
        Assert.assertEquals(requestValidateOnly.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
        Assert.assertEquals(requestValidateOnly.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(request.getRequestBody(), mapper.writeValueAsString(payload));
        Assert.assertEquals(requestValidateOnly.getRequestBody(), mapper.writeValueAsString(payload));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        Assert.assertEquals(request.getRequestHeaders(), headers);
        Assert.assertEquals(requestValidateOnly.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + PATH_NAME);
        Assert.assertEquals(request.getUri(baseURI), expectedUri);

        expectedUri = URI.create("https://go.urbanairship.com" + PATH_NAME + "validate/");
        Assert.assertEquals(requestValidateOnly.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {

        String responseJson ="{\"ok\" : true, \"operation_id\" : \"OpID\", " +
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

        final ResponseParser<ScheduleResponse> responseParser = response -> ScheduleObjectMapper.getInstance().readValue(response, ScheduleResponse.class);

        Assert.assertEquals(request.getResponseParser().parse(responseJson), responseParser.parse(responseJson));
        Assert.assertEquals(requestValidateOnly.getResponseParser().parse(responseJson), responseParser.parse(responseJson));
    }

}