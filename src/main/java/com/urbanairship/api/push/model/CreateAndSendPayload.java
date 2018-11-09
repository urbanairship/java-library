package com.urbanairship.api.push.model;

public class CreateAndSendPayload extends PushModelObject {

    private String payloadString = "{\n" +
            "  \"audience\": {\n" +
            "    \"create_and_send\" : [\n" +
            "      {\n" +
            "        \"ua_address\": \"new@email.com\",\n" +
            "        \"ua_email_opt_in_level\": \"commercial\",\n" +
            "        \"name\": \"New Person, Esq.\",\n" +
            "        \"address\": \"1001 New Street #400 City State Zip\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"ua_address\" : \"ben@icetown.com\",\n" +
            "        \"ua_email_opt_in_level\": \"commercial\",\n" +
            "        \"name\": \"Ben Wyatt\",\n" +
            "        \"address\": \"1234 Main Street Pawnee IN 46001\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"device_types\" : [ \"email\" ],\n" +
            "  \"notification\" : {\n" +
            "    \"email\": {\n" +
            "      \"message_type\": \"commercial\",\n" +
            "      \"sender_name\": \"Urban Airship\",\n" +
            "      \"sender_address\": \"team@urbanairship.com\",\n" +
            "      \"reply_to\": \"no-reply@urbanairship.com\",\n" +
            "      \"template\": {\n" +
            "        \"template_id\" : \"09641749-f288-46e6-8dc6-fae592e8c092\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public String getPayloadString() {
        return payloadString;
    }

    public void setPayloadString(String payloadString) {
        this.payloadString = payloadString;
    }
}
