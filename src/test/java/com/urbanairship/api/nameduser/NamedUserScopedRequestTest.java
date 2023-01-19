package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.attributelists.AttributeListsCreateRequest;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.AttributeAction;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.nameduser.model.NamedUserScope;
import com.urbanairship.api.nameduser.model.NamedUserScopeType;
import com.urbanairship.api.nameduser.model.NamedUserScopedPayload;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannel;
import com.urbanairship.api.nameduser.model.NamedUserUpdateChannelAction;
import com.urbanairship.api.nameduser.model.NamedUserUpdateDeviceType;
import com.urbanairship.api.nameduser.model.NamedUserUpdatePayload;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class NamedUserScopedRequestTest {

    private static final ObjectMapper MAPPER = NamedUserObjectMapper.getInstance();
    NamedUserScopedRequest request;


    @Before
    public void setupCreate() {
        Set<NamedUserScopeType> scopeTypes1 = new HashSet<>(Arrays.asList(NamedUserScopeType.APP));
        Set<String> subscribeLists1 = new HashSet<>(Arrays.asList("stickers", "gifs"));
        Set<String> unsubscribeLists1 = new HashSet<>(Arrays.asList("cookies"));

        Set<NamedUserScopeType> scopeTypes2 = new HashSet<>(Arrays.asList(NamedUserScopeType.WEB));
        Set<String> subscribeLists2 = new HashSet<>(Arrays.asList("daily_snacks", "brunch"));
        Set<String> unsubscribeLists2 = new HashSet<>(Arrays.asList("promotions"));

        NamedUserScope namedUserScope1 = NamedUserScope.newBuilder()
                .setScopes(scopeTypes1)
                .setSubscribeLists(subscribeLists1)
                .setUnsubscribeLists(unsubscribeLists1)
                .build();

        NamedUserScope namedUserScope2 = NamedUserScope.newBuilder()
                .setScopes(scopeTypes2)
                .setSubscribeLists(subscribeLists2)
                .setUnsubscribeLists(unsubscribeLists2)
                .build();

        NamedUserScopedPayload namedUserScopedPayload = NamedUserScopedPayload.newBuilder()
                .addNamedUserScope(namedUserScope1)
                .addNamedUserScope(namedUserScope2)
                .build();

        request = NamedUserScopedRequest.newRequest("named_user_id", namedUserScopedPayload);
    }

    @Test
    public void testNamedUserScoped() throws Exception {
        String expected = "{\n" +
                "   \"scoped\": [\n" +
                "      {\n" +
                "          \"scope\": [\"app\"],\n" +
                "          \"subscription_lists\": {\n" +
                "              \"subscribe\": [\"stickers\", \"gifs\"],\n" +
                "              \"unsubscribe\": [\"cookies\"]\n" +
                "          }\n" +
                "      },\n" +
                "      {\n" +
                "          \"scope\": [\"web\"],\n" +
                "          \"subscription_lists\": {\n" +
                "              \"subscribe\": [\"brunch\", \"daily_snacks\"],\n" +
                "              \"unsubscribe\": [\"promotions\"]\n" +
                "          }\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/named_users/scoped/named_user_id");
        assertEquals(request.getUri(baseURI), expectedURI);
    }

    @Test
    public void testResponseParser() throws Exception {

        GenericResponse genericResponse = new GenericResponse(true,null,null,null, null, null);

        String responseJson = "{\"ok\": true}";

        assertEquals(request.getResponseParser().parse(responseJson), genericResponse);

    }
}
