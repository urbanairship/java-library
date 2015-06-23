/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.parse;

import com.urbanairship.api.segments.model.TagPredicate;
import com.urbanairship.api.segments.model.TagPredicateBuilder;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class TagPredicateDeserializer extends JsonDeserializer<TagPredicate> {

    public static final TagPredicateDeserializer INSTANCE = new TagPredicateDeserializer();

    private static final String INVALID_TAG_PREDICATE = "Invalid tag predicate";

    private TagPredicateDeserializer() {
    }

    @Override
    public TagPredicate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.VALUE_STRING || StringUtils.isBlank(jp.getText())) {
            throw new InvalidAudienceSegmentException(INVALID_TAG_PREDICATE);
        }
        String tag = null;
        String tagClass = null;
        String tagGroup = null;
        if (jp.getCurrentName().equals("tag")) {
            tag = jp.getText();
        } else if (jp.getCurrentName().equals("tag_class")) {
            tagClass = jp.getText();
        } else if (jp.getCurrentName().equals("group")) {
            tagGroup = jp.getText();
        }
        if (!jp.nextToken().equals(JsonToken.END_OBJECT)) {
            if (jp.getCurrentName().equals("tag")) {
                jp.nextToken();
                tag = jp.getText();
            } else if (jp.getCurrentName().equals("tag_class")) {
                jp.nextToken();
                tagClass = jp.getText();
            } else if (jp.getCurrentName().equals("group")) {
                jp.nextToken();
                tagGroup = jp.getText();
            }
        }
        return TagPredicateBuilder.newInstance()
            .setTag(tag)
            .setTagClass(tagClass)
            .setTagGroup(tagGroup)
            .build();
    }
}
