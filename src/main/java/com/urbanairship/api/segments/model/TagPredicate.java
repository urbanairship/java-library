/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import org.apache.commons.lang.StringUtils;

public final class TagPredicate implements Predicate {

    public static final String DEFAULT_TAG_CLASS = "device";
    private final String tag;
    private final String tagClass;

    TagPredicate(String tag, String tagClass) {
        this.tag = tag;
        this.tagClass = tagClass;
    }

    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getIdentifier() {
        return isDefaultClass() ? getName() : getName() + "/" + getTagClass();
    }

    public String getTag() {
        return tag;
    }

    public String getTagClass() {
        return tagClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagPredicate that = (TagPredicate) o;

        if (tag != null ? !tag.equals(that.tag) : that.tag != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TagPredicate{" +
                "tag='" + tag + '\'' +
                ", tagClass='" + tagClass + '\'' +
                '}';
    }

    public boolean isDefaultClass() {
        return StringUtils.isEmpty(tagClass) || DEFAULT_TAG_CLASS.equals(tagClass);
    }

}
