package com.urbanairship.api.channel.parse.subscriptionlist;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionList;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListPayload;

import java.io.IOException;

public class SubscriptionListPayloadSerializer extends JsonSerializer<SubscriptionListPayload> {
    @Override
    public void serialize(SubscriptionListPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());

        jgen.writeArrayFieldStart("subscription_lists");

        for (SubscriptionList subscriptionList : payload.getSubscriptionList()) {
            jgen.writeStartObject();
            jgen.writeStringField("action", subscriptionList.getAction().getIdentifier());
            jgen.writeStringField("list_id", subscriptionList.getListId());
            jgen.writeEndObject();
        }

        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}
