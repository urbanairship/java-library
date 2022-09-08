package com.urbanairship.api.sms;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.sms.model.CustomSmsResponseMmsPayload;
import com.urbanairship.api.sms.model.CustomSmsResponseResponse;
import com.urbanairship.api.sms.model.CustomSmsResponseSmsPayload;
import com.urbanairship.api.sms.parse.SmsObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Request to be used for using custom sms response.
 */
public class CustomSmsResponseRequest implements Request<CustomSmsResponseResponse> {

    private final static String API_CUSTOM_SMS_RESPONSE = "/api/sms/custom-response";
    private CustomSmsResponseSmsPayload smsPayload = null;
    private CustomSmsResponseMmsPayload mmsPayload = null;

    private CustomSmsResponseRequest(CustomSmsResponseSmsPayload smsPayload) {
        Preconditions.checkNotNull(smsPayload, "Payload must not be null");
        this.smsPayload = smsPayload;
    }

    private CustomSmsResponseRequest(CustomSmsResponseMmsPayload mmsPayload) {
        Preconditions.checkNotNull(mmsPayload, "Payload must not be null");
        this.mmsPayload = mmsPayload;
    }

    public static CustomSmsResponseRequest newRequest(CustomSmsResponseSmsPayload smsPayload) {
        return new CustomSmsResponseRequest(smsPayload);
    }

    public static CustomSmsResponseRequest newRequest(CustomSmsResponseMmsPayload mmsPayload) {
        return new CustomSmsResponseRequest(mmsPayload);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {;
        if (smsPayload != null) {
            return smsPayload.toJSON();
        } 
        if (mmsPayload != null) {
            return mmsPayload.toJSON();
        }
        return null;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, API_CUSTOM_SMS_RESPONSE);
    }

    @Override
    public ResponseParser<CustomSmsResponseResponse> getResponseParser() {
        return new ResponseParser<CustomSmsResponseResponse>() {
            @Override
            public CustomSmsResponseResponse parse(String response) throws IOException {
                return SmsObjectMapper.getInstance().readValue(response, CustomSmsResponseResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return true;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}
