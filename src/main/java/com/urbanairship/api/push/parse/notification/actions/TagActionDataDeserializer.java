/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public final class TagActionDataDeserializer extends JsonDeserializer<TagActionData> {

    public TagActionDataDeserializer() {
    }

    @Override
    public TagActionData deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = parser.readValueAsTree();
        if(!jsonNode.isArray() && !jsonNode.isTextual()) {
            throw new APIParsingException("Tag must be a string or an array.");
        }

        if(jsonNode.isTextual()) {
            return TagActionData.single(jsonNode.getTextValue());
        }

        Iterator<JsonNode> items = jsonNode.getElements();
        List<String> tags = Lists.newArrayList();
        while(items.hasNext()) {
            JsonNode item = items.next();
            if(item.isNull() || !item.isTextual()) {
                throw new APIParsingException("Null or non-string tags are not allowed.");
            }

            tags.add(item.getTextValue());
        }

        if(tags.size() == 0) {
            throw new APIParsingException("Tag list must contain at least one tag.");
        }

        return TagActionData.set(Sets.newHashSet(tags));
    }
}
