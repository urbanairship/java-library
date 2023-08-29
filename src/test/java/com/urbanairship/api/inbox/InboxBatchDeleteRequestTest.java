package com.urbanairship.api.inbox;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.inbox.model.InboxBatchDeleteResponse;
import com.urbanairship.api.inbox.model.MessageIdError;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InboxBatchDeleteRequestTest {

    private final static ObjectMapper MAPPER = CommonObjectMapper.getInstance();
    private ArrayList<String> messageIds = new ArrayList<>();

    @Test
    public void testContentType() throws Exception {
        messageIds.add("test1");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);
        assertEquals(ContentType.APPLICATION_JSON, inboxBatchDeleteRequestTest.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        messageIds.add("test1");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);
        assertEquals(inboxBatchDeleteRequestTest.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        messageIds.add("test");
        messageIds.add("test2");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);
        String expected = "{\"message_ids\":[\"test\",\"test2\"]}";

        JsonNode jsonFromObject = MAPPER.readTree(inboxBatchDeleteRequestTest.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test
    public void testHeaders() throws Exception {
        messageIds.add("test1");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(inboxBatchDeleteRequestTest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        messageIds.add("test1");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/user/messages/batch-delete/");
        assertEquals(inboxBatchDeleteRequestTest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testResponseParser() throws Exception {
        messageIds.add("test1");
        InboxBatchDeleteRequest inboxBatchDeleteRequestTest =
                InboxBatchDeleteRequest.newRequest(messageIds);

        ArrayList<MessageIdError> messageIdErrorList = new ArrayList<>();
        messageIdErrorList.add(new MessageIdError("test3", "Message not found.", 40404));

        ArrayList<String> messageIds = new ArrayList<>();
        messageIds.add("test");
        messageIds.add("test1");

        InboxBatchDeleteResponse inboxBatchDeleteResponse = new InboxBatchDeleteResponse(messageIds, messageIdErrorList);

        String responseJson = "{\n" +
                "    \"deleted_message_ids\": [\"test\", \"test1\"],\n" +
                "    \"errors\": [\n" +
                "        {\n" +
                "            \"message_id\": \"test3\",\n" +
                "            \"error_message\": \"Message not found.\",\n" +
                "            \"error_code\": 40404\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        assertEquals(inboxBatchDeleteRequestTest.getResponseParser().parse(responseJson).getDeletedMessageIds(),
                inboxBatchDeleteResponse.getDeletedMessageIds());
        assertEquals(inboxBatchDeleteRequestTest.getResponseParser().parse(responseJson).getMessageIdErrors().get(0).getErrorMessage(),
                inboxBatchDeleteResponse.getMessageIdErrors().get(0).getErrorMessage());
        assertEquals(inboxBatchDeleteRequestTest.getResponseParser().parse(responseJson).getMessageIdErrors().get(0).getMessageId(),
                inboxBatchDeleteResponse.getMessageIdErrors().get(0).getMessageId());
        assertEquals(inboxBatchDeleteRequestTest.getResponseParser().parse(responseJson).getMessageIdErrors().get(0).getErrorCode(),
                inboxBatchDeleteResponse.getMessageIdErrors().get(0).getErrorCode());
    }
}
