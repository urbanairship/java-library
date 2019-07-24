/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.android;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Optional;

/**
 * Base Interface for the Android Style objects.
 *
 * @param <T> Style Type
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BigPictureStyle.class, name = "big_picture"),
        @JsonSubTypes.Type(value = BigTextStyle.class, name = "big_text"),
        @JsonSubTypes.Type(value = InboxStyle.class, name = "inbox")
})
public interface Style<T> {

    public static enum Type {
        BIG_PICTURE("big_picture"),
        BIG_TEXT("big_text"),
        INBOX("inbox");

        private final String styleType;

        private Type(String styleType) {
            this.styleType = styleType;
        }

        public String getStyleType() {
            return styleType;
        }
    }

    public T getContent();

    public Type getType();

    public Optional<String> getTitle();

    public Optional<String> getSummary();
}
