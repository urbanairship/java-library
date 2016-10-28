package com.urbanairship.api.push.parse.notification.android;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.android.Category;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

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
