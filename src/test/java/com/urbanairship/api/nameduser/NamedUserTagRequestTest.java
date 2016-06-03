package com.urbanairship.api.nameduser;

import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class NamedUserTagRequestTest {

    private String namedUserId1;
    private String namedUserId2;
    private Set<String> namedUserIds;
    private NamedUserTagRequest defaultRequest;

    @Before
    public void setup() {
        namedUserId1 = RandomStringUtils.random(10);
        namedUserId2 = RandomStringUtils.random(10);
        namedUserIds = ImmutableSet.of(namedUserId1, namedUserId2);
        defaultRequest = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserIds)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));
    }

    @Test
    public void testAddTagsBody() throws Exception {
        String expected = "{" +
            "\"audience\":{" +
            "\"named_user_id\":[\"" + namedUserId1+ "\",\"" + namedUserId2 +"\"]" +
            "}," +
            "\"add\":{" +
            "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
            "}" +
            "}";

        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserIds)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        assertEquals(expected, request.getRequestBody());
    }

    @Test
    public void testRemoveTags() throws Exception {
        String expected = "{" +
            "\"audience\":{" +
            "\"named_user_id\":[\"" + namedUserId1+ "\",\"" + namedUserId2 +"\"]" +
            "}," +
            "\"remove\":{" +
            "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
            "}" +
            "}";

        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserIds)
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        assertEquals(expected, request.getRequestBody());
    }

    @Test
    public void testSetTags() throws Exception {
        String expected = "{" +
            "\"audience\":{" +
            "\"named_user_id\":[\"" + namedUserId1+ "\",\"" + namedUserId2 +"\"]" +
            "}," +
            "\"set\":{" +
            "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
            "}" +
            "}";

        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserIds)
            .setTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        assertEquals(expected, request.getRequestBody());
    }

    @Test
    public void testAddAndRemoveTags() throws Exception {
        String expected = "{" +
            "\"audience\":{" +
            "\"named_user_id\":[\"" + namedUserId1+ "\",\"" + namedUserId2 +"\"]" +
            "}," +
            "\"remove\":{" +
            "\"tag_group1\":[\"tag4\",\"tag5\",\"tag6\"]," +
            "\"tag_group3\":[\"tag4\",\"tag5\",\"tag6\"]," +
            "\"tag_group2\":[\"tag4\",\"tag5\",\"tag6\"]" +
            "}," +
            "\"add\":{" +
            "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
            "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
            "}" +
            "}";

        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserIds)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group2", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group3", ImmutableSet.of("tag4", "tag5", "tag6"));

        assertEquals(expected, request.getRequestBody());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddAndSetTags() throws Exception {
        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserId1)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));
        request.getRequestBody();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveAndSetTags() throws Exception {
        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserId1)
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));
        request.getRequestBody();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNoTagMutations() throws Exception {
        NamedUserTagRequest request = NamedUserTagRequest.newRequest()
            .addNamedUsers(namedUserId1);
        request.getRequestBody();
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(defaultRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(defaultRequest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(defaultRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/named_users/tags/");
        assertEquals(defaultRequest.getUri(baseURI), expextedURI);
    }

    @Test
    public void testParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, defaultRequest.getResponseParser().parse(response));
    }
}
