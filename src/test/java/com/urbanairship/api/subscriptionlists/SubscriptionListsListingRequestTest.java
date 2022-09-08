package com.urbanairship.api.subscriptionlists;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.push.model.notification.email.MessageType;
import com.urbanairship.api.subscriptionlists.model.SubscriptionListListingResponse;
import com.urbanairship.api.subscriptionlists.model.SubscriptionListView;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SubscriptionListsListingRequestTest {

    SubscriptionListListingRequest subscriptionListsListingRequestTest;

    String SubscriptionListsListingUri = "https://go.urbanairship.com/api/subscription_lists/";

    @Before
    public void setupCreate() {
        subscriptionListsListingRequestTest = SubscriptionListListingRequest.newRequest();
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(subscriptionListsListingRequestTest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(subscriptionListsListingRequestTest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(subscriptionListsListingRequestTest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        System.out.println(headers);
        assertEquals(subscriptionListsListingRequestTest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedSubscriptionListsListingUri = URI.create(SubscriptionListsListingUri);
        assertEquals(subscriptionListsListingRequestTest.getUri(baseURI), expectedSubscriptionListsListingUri);
    }

    @Test
    public void testReportParser() throws Exception {

        List<String> scopes1 = new ArrayList<>();
        scopes1.add("email");
        ImmutableList<String> immutableScopes1 = ImmutableList.copyOf(scopes1);
        SubscriptionListView subscriptionListView1 = new SubscriptionListView("example_listId-1","A nice readable name 1",null, immutableScopes1, true, null);

        List<String> scopes2 = new ArrayList<>();
        scopes2.add("app");
        scopes2.add("web");
        ImmutableList<String> immutableScopes2 = ImmutableList.copyOf(scopes2);
        SubscriptionListView subscriptionListView2 = new SubscriptionListView("example_listId-2","A nice readable name 2","A very nice description for you.", immutableScopes2, null, "commercial");

        List<SubscriptionListView> subscriptionListViews = new ArrayList<>();
        subscriptionListViews.add(subscriptionListView1);
        subscriptionListViews.add(subscriptionListView2);

        ImmutableList<SubscriptionListView> immutableSubscriptionListViews = ImmutableList.copyOf(subscriptionListViews);

        SubscriptionListListingResponse subscriptionListListingResponse = new SubscriptionListListingResponse(true, immutableSubscriptionListViews, null, null);

        String response = "{\n" +
                "   \"ok\" : true,\n" +
                "   \"subscription_lists\": [\n" +
                "       {\n" +
                "           \"list_id\": \"example_listId-1\",\n" +
                "           \"name\": \"A nice readable name 1\",\n" +
                "           \"default_opted_in\": true,\n" +
                "           \"scopes\": [\"email\"]\n" +
                "       },\n" +
                "       {\n" +
                "           \"list_id\": \"example_listId-2\",\n" +
                "           \"name\": \"A nice readable name 2\",\n" +
                "           \"description\": \"A very nice description for you.\",\n" +
                "           \"scopes\": [\"app\", \"web\"],\n" +
                "           \"messaging_type\": \"commercial\"\n" +
                "       }\n" +
                "   ]\n" +
                "}";
        assertEquals(subscriptionListsListingRequestTest.getResponseParser().parse(response), subscriptionListListingResponse);
    }
}
