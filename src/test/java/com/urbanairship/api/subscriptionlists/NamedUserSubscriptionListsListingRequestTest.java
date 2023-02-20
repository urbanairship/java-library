package com.urbanairship.api.subscriptionlists;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.subscriptionlists.model.ContactSubscriptionListView;
import com.urbanairship.api.subscriptionlists.model.NamedUserSubscriptionListsListingResponse;
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

public class NamedUserSubscriptionListsListingRequestTest {

    NamedUserSubscriptionListsListingRequest namedUserSubscriptionListsListingRequest;

    String SubscriptionListsListingUri = "https://go.urbanairship.com/api/subscription_lists/named_users/nameduser";

    @Before
    public void setupCreate() {
        namedUserSubscriptionListsListingRequest = NamedUserSubscriptionListsListingRequest.newRequest("nameduser");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(namedUserSubscriptionListsListingRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(namedUserSubscriptionListsListingRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(namedUserSubscriptionListsListingRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        System.out.println(headers);
        assertEquals(namedUserSubscriptionListsListingRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedSubscriptionListsListingUri = URI.create(SubscriptionListsListingUri);
        assertEquals(namedUserSubscriptionListsListingRequest.getUri(baseURI), expectedSubscriptionListsListingUri);
    }

    @Test
    public void testParser() throws Exception {

        List<String> listIds1 = new ArrayList<>();
        listIds1.add("example_listId-2");
        ImmutableList<String> immutableListIds1 = ImmutableList.copyOf(listIds1);
        ContactSubscriptionListView contactSubscriptionListView1 = new ContactSubscriptionListView(immutableListIds1, "web");

        List<String> listIds2 = new ArrayList<>();
        listIds2.add("example_listId-2");
        listIds2.add("example_listId-4");
        ImmutableList<String> immutableListIds2 = ImmutableList.copyOf(listIds2);
        ContactSubscriptionListView contactSubscriptionListView2 = new ContactSubscriptionListView(immutableListIds2, "app");

        List<ContactSubscriptionListView> contactSubscriptionListsView = new ArrayList<>();
        contactSubscriptionListsView.add(contactSubscriptionListView1);
        contactSubscriptionListsView.add(contactSubscriptionListView2);

        ImmutableList<ContactSubscriptionListView> immutableContactSubscriptionListsView = ImmutableList.copyOf(contactSubscriptionListsView);

        NamedUserSubscriptionListsListingResponse namedUserSubscriptionListsListingResponse =
                new NamedUserSubscriptionListsListingResponse(true, immutableContactSubscriptionListsView, null,null, null, null, null);

        String response = "{\n" +
                "   \"ok\" : true,\n" +
                "   \"subscription_lists\": [\n" +
                "      {\n" +
                "         \"list_ids\": [\"example_listId-2\"],\n" +
                "         \"scope\": \"web\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"list_ids\": [\"example_listId-2\", \"example_listId-4\"],\n" +
                "         \"scope\": \"app\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        assertEquals(namedUserSubscriptionListsListingRequest.getResponseParser().parse(response), namedUserSubscriptionListsListingResponse);
    }
}
