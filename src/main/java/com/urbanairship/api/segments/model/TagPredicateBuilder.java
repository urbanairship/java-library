/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
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

    private TagPredicateBuilder() {
        this.tagClass = TagPredicate.DEFAULT_TAG_CLASS;
        this.tag = null;
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

    public TagPredicate build() {
        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Tag is required");
        }
        return new TagPredicate(tag, tagClass);
    }
}
