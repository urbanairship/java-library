/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.android;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

/**
 * Represents an Android style with a type of "inbox".
 */
public class InboxStyle implements Style<ImmutableList<String>> {
    private final ImmutableList<String> content;
    private final Optional<String> title;
    private final Optional<String> summary;

    private InboxStyle(Builder builder) {
        this.content = builder.content.build();
        this.title = Optional.of(builder.title);
        this.summary = Optional.of(builder.summary);
    }

    /**
     * Return a new InboxStyle builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return the content associated with the "lines" key.
     *
     * @return String path to image
     */
    @Override
    public ImmutableList<String> getContent() {
        return content;
    }

    /**
     * Return the Type.INBOX style type.
     *
     * @return Type.INBOX
     */
    @Override
    public Style.Type getType() {
        return Type.INBOX;
    }

    /**
     * Return an optional title string.
     *
     * @return Optional String representing the style title
     */
    @Override
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Return an optional summary string.
     *
     * @return Optional String representing the style summary.
     */
    @Override
    public Optional<String> getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InboxStyle that = (InboxStyle) o;

        if (!content.equals(that.content)) return false;
        if (!summary.equals(that.summary)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }
    @Override
    public String toString() {
        return "InboxStyle{" +
                "content=" + content +
                ", title=" + title +
                ", summary=" + summary +
                '}';
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + summary.hashCode();
        return result;
    }


    public static class Builder {
        private ImmutableList.Builder<String> content = ImmutableList.builder();
        private String title = null;
        private String summary = null;

        /**
         * Add a line to the "inbox" key.
         *
         * @param line String
         * @return Builder
         */
        public Builder addLine(String line) {
            this.content.add(line);
            return this;
        }

        /**
         * Add lines to the "inbox" key.
         *
         * @param lines Iterable of Strings
         * @return Builder
         */
        public Builder addLines(Iterable<? extends String> lines) {
            this.content.addAll(lines);
            return this;
        }

        /**
         * Set the title String.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the summary String.
         *
         * @param summary String
         * @return Builder
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Build an InboxStyle object.
         *
         * <pre>
         *     1. The content field cannot be null.
         * </pre>
         *
         * @return InboxStyle
         */
        public InboxStyle build() {
            Preconditions.checkNotNull(!content.build().isEmpty(), "The content field cannot be null.");
            return new InboxStyle(this);
        }
    }
}
