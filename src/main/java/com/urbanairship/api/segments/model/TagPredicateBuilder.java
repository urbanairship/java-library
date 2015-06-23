/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import org.apache.commons.lang.StringUtils;

/**
 * Date: 4/24/13
 * Time: 10:51 AM
 */
public class TagPredicateBuilder {
    private String tag;
    private String tagClass;
    private String tagGroup;

    private TagPredicateBuilder() {
        this.tagClass = null;
        this.tag = null;
        this.tagGroup = null;
    }

    public static final TagPredicateBuilder newInstance() {
        return new TagPredicateBuilder();
    }

    public TagPredicateBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public TagPredicateBuilder setTagClass(String tagClass) {
        this.tagClass = tagClass;
        return this;
    }

    public TagPredicateBuilder setTagGroup(String tagGroup) {
        this.tagGroup = tagGroup;
        return this;
    }

    public TagPredicate build() {
        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Tag is required");
        }
        if (tagGroup != null && tagClass != null) {
            throw new IllegalArgumentException(String.format("tag_class and group cannot both be specified for tag '%s'", tag));
        }
        if (tagGroup == null && tagClass == null) {
            this.tagClass = TagPredicate.DEFAULT_TAG_CLASS;
        }

        return new TagPredicate(tag, tagClass, tagGroup);
    }
}
