package com.urbanairship.api.sms;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload;
import com.urbanairship.api.sms.model.CustomSmsResponseResponse;
import com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload;
import com.urbanairship.api.sms.model.MmsSlides;
import com.urbanairship.api.sms.parse.SmsObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CustomSmsResponseRequestTest {

    CustomSmsResponseSmsPayload CustomSmsResponseSmsPayload = com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload.newBuilder()
            .setAlert("Your balance is $1234.56")
            .setMobileOriginatedId("28883743-4868-4083-ab5d-77ac4542531a")
            .setShortenLinks(true)
            .build();

    CustomSmsResponseRequest request = CustomSmsResponseRequest.newRequest(CustomSmsResponseSmsPayload);

    CustomSmsResponseMmsPayload CustomSmsResponseMmsPayload = com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload.newBuilder()
            .setFallbackText("Your balance is $1234.56")
            .setMobileOriginatedId("28883743-4868-4083-ab5d-77ac4542531a")
            .setSlides(MmsSlides.newBuilder()
                    .setMediaUrl("https://example.com/cat/pics/12345678.gif")
                    .setText("test")
                    .setMediaContentLength(12345)
                    .setMediaContentType("image/gif")
                    .build())
            .build();

    CustomSmsResponseRequest request2 = CustomSmsResponseRequest.newRequest(CustomSmsResponseMmsPayload);

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(request2.getContentType(), ContentType.APPLICATION_JSON);

    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(request2.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), "{\"mobile_originated_id\":\"28883743-4868-4083-ab5d-77ac4542531a\",\"sms\":{\"alert\":\"Your balance is $1234.56\",\"shorten_links\":true}}");

        assertEquals(request2.getRequestBody(), "{\"mobile_originated_id\":\"28883743-4868-4083-ab5d-77ac4542531a\",\"mms\":{\"fallback_text\":\"Your balance is $1234.56\",\"slides\":[{\"text\":\"test\",\"media\":{\"url\":\"https://example.com/cat/pics/12345678.gif\",\"content_type\":\"image/gif\",\"content_length\":12345}}]}}");

    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
        assertEquals(request2.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/sms/custom-response");
        assertEquals(request.getUri(baseURI), expectedURI);
        assertEquals(request2.getUri(baseURI), expectedURI);
    }

    @Test
    public void testCustomSmsResponseParser() throws Exception {
        ResponseParser<CustomSmsResponseResponse> responseParser = response -> SmsObjectMapper.getInstance().readValue(response, CustomSmsResponseResponse.class);

        String response = "{\"ok\" : true,\"push_id\" : \"df6a6b50\",\"operation_id\" : \"df6a6b50\"}";
        assertEquals(request.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(request2.getResponseParser().parse(response), responseParser.parse(response));
    }
}