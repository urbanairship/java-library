package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.android.Category;

import java.io.IOException;
import java.util.Optional;

public final class CategoryDeserializer extends JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String categoryString = jp.getText();
        Optional<Category> category = Category.find(categoryString);

        if (!category.isPresent()) {
            throw new APIParsingException("Unrecognized category " + categoryString);
        }

        return category.get();
    }
}
