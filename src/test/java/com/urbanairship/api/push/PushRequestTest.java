package com.urbanairship.api.push;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PushRequestTest {

    PushPayload payload = PushPayload.newBuilder()
        .setAudience(Selectors.all())
        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
        .setNotification(Notifications.alert("Foo"))
        .build();

    PushRequest pushRequest = PushRequest.newRequest(payload);
    PushRequest validateRequest = PushRequest.newRequest(payload).setValidateOnly(true);

    @Test
    public void testContentType() throws Exception {
        assertEquals(pushRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(validateRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(pushRequest.getHTTPMethod(), Request.HTTPMethod.POST);
        assertEquals(validateRequest.getHTTPMethod(), Request.HTTPMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(pushRequest.getRequestBody(), payload.toJSON());
        assertEquals(validateRequest.getRequestBody(), payload.toJSON());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION);

        assertEquals(pushRequest.getRequestHeaders(), headers);
        assertEquals(validateRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/push/");
        assertEquals(pushRequest.getUri(baseURI), expextedURI);

        expextedURI = URI.create("https://go.urbanairship.com/api/push/validate/");
        assertEquals(validateRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<PushResponse>() {
            @Override
            public PushResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, PushResponse.class);
            }
        };

        String response = "{\"ok\" : true,\"operation_id\" : \"df6a6b50\", \"push_ids\":[\"PushID\"]}";
        assertEquals(pushRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

    @Test
    public void testPushValidationParser() throws Exception {
        ResponseParser responseParser = new ResponseParser<PushResponse>() {
            @Override
            public PushResponse parse(String response) throws IOException {
                return PushObjectMapper.getInstance().readValue(response, PushResponse.class);
            }
        };
        
        String response = "{\"ok\" : true}";
        assertEquals(validateRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
