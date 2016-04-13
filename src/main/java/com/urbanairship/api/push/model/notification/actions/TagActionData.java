
/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Set;

/**
 * Represents tags specified in an 'add tags' or 'remove tags'
 * action. Modeled after the 'Optional' type, this class
 * represents either a single tag (a string) or a set of tags.
 */
public abstract class TagActionData extends PushModelObject {

    /**
     * Represents a single tag.
     * @param tag String tag
     * @return TagActionData
     */
    public static TagActionData single(String tag) {
        Preconditions.checkNotNull(tag, "tag should not be null.");
        return new SingleTag(tag);
    }

    /**
     * Represents a set of tags.
     * @param tags Set of tags
     * @return TagActionData
     */
    public static TagActionData set(Set<String> tags) {
        Preconditions.checkNotNull(tags, "tags should not be null.");
        return new TagList(tags);
    }

    /**
     * When true, this object represents a single tag (and calling {@link TagActionData.getSingleTag} will succeed).
     * Otherwise, this represents a set of tags (and calling {@link TagActionData.getTagSet} will succeed).
     * @return boolean
     */
    public abstract boolean isSingle();

    /**
     * Returns the tag represented, if this object was constructed
     * with {@link TagActionData.single}, otherwise throw an exception.
     * @return String
     */
    public abstract String getSingleTag();

    /**
     * Returns the tags represented, if this object was constructed
     * with {@link TagActionData.set}, otherwise throw an exception.
     * @return Set of tags
     */
    public abstract Set<String> getTagSet();

    private static final class SingleTag extends TagActionData {
        private final String tag;

        private SingleTag(String tag) {
            this.tag = tag;
        }

        public boolean isSingle() {
            return true;
        }

        @Override
        public String getSingleTag() {
            return tag;
        }

        @Override
        public Set<String> getTagSet() {
            throw new IllegalStateException("Can't call 'getTags' on single tag.");
        }

        @Override
        public String toString() {
            return "SingleTag{" +
                    "tag='" + tag + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(tag);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final SingleTag other = (SingleTag) obj;
            return Objects.equal(this.tag, other.tag);
        }
    }

    private static final class TagList extends TagActionData {
        private final Set<String> tags;

        private TagList(Set<String> tags) {
            this.tags = ImmutableSet.copyOf(tags);
        }

        @Override
        public boolean isSingle() {
            return false;
        }

        @Override
        public String toString() {
            return "TagList{" +
                    "tags=" + tags +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(tags);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final TagList other = (TagList) obj;
            return Objects.equal(this.tags, other.tags);
        }

        @Override
        public String getSingleTag() {
            throw new IllegalStateException("Can't call 'getTag' on a tag list.");
        }

        @Override
        public Set<String> getTagSet() {
            return tags;
        }
    }

}
