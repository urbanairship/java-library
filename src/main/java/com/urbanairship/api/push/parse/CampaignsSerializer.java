package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.urbanairship.api.push.model.Campaigns;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;

public class CampaignsSerializer extends JsonSerializer<Campaigns> {
       @Override
    public void serialize(Campaigns campaigns, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException{
                jsonGenerator.writeStartObject();

                        jsonGenerator.writeObjectField("categories", campaigns.getCategories());

                        jsonGenerator.writeEndObject();
       }
}
