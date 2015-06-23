/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;

public final class TagPredicate implements Predicate {

    public static final String DEFAULT_TAG_CLASS = "device";
    private final String tag;
    private final Optional<String> tagClass;
    private final Optional<String> tagGroup;

    TagPredicate(String tag, String tagClass, String tagGroup) {
        this.tag = tag;
        this.tagClass = Optional.fromNullable(tagClass);
        this.tagGroup = Optional.fromNullable(tagGroup);
    }

    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getIdentifier() {
        return isDefaultClass() ? getName() : getName() + "/" + getTagClass().get();
    }

    public String getTag() {
        return tag;
    }

    public Optional<String> getTagClass() {
        return tagClass;
    }

    public Optional<String> getTagGroup() {
        return tagGroup;
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

        return Objects.equal(this.tag, that.tag) && Objects.equal(this.tagClass, that.tagClass) && Objects.equal(this.tagGroup, that.tagGroup);

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
                ", tagGroup='" + tagGroup + '\'' +
                '}';
    }

    public boolean isDefaultClass() {
        return tagClass.isPresent() && (StringUtils.isEmpty(tagClass.get()) || DEFAULT_TAG_CLASS.equals(tagClass.get()));
    }

}
