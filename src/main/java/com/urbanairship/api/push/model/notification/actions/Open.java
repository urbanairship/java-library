/*
 * Copyright 2013 Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Open
 *
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class Open extends PushModelObject {

    //\\//\\//\\//\\//\\//\\//\\//
    // contstants               //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // fields                   //
    //\\//\\//\\//\\//\\//\\//\\//

    private final Optional<OpenType> type;
    private final Optional<ContentData> contentData;

    //\\//\\//\\//\\//\\//\\//\\//
    // constructors             //
    //\\//\\//\\//\\//\\//\\//\\//

    private Open(Optional<OpenType> type,
                 Optional<ContentData> contentData) {
        this.type = type;
        this.contentData = contentData;
    }

    //\\//\\//\\//\\//\\//\\//\\//
    // public methods           //
    //\\//\\//\\//\\//\\//\\//\\//


    public Optional<OpenType> getType() {
        return type;
    }

    public Optional<ContentData> getContentData() {
        return contentData;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Open deepLink(String content) {
        Open.Builder builder = Open.newBuilder();
        builder.setType(OpenType.DEEP_LINK);
        builder.setContentData(ContentData.content(content));
        return builder.build();
    }

    public static Open url(String url) {
        Open.Builder builder = Open.newBuilder();
        builder.setType(OpenType.URL);
        builder.setContentData(ContentData.content(url));
        return builder.build();
    }

    public static Open landingPage(String body, String contentType, String contentEncoding) {
        Open.Builder builder = Open.newBuilder();
        builder.setType(OpenType.LANDING_PAGE);
        builder.setContentData(ContentData.content(body, contentType, contentEncoding));
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Open open = (Open) o;

        if (contentData != null ? !contentData.equals(open.contentData) : open.contentData != null) return false;
        if (type != null ? !type.equals(open.type) : open.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (contentData != null ? contentData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Open{");
        sb.append("type=").append(type);
        sb.append(", contentData=").append(contentData);
        sb.append('}');
        return sb.toString();
    }

    //\\//\\//\\//\\//\\//\\//\\//
    // protected methods        //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // package-private methods  //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // private methods          //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // inner classes            //
    //\\//\\//\\//\\//\\//\\//\\//


    /**
     * Open Builder
     */
    public static class Builder {

        private OpenType type;
        private ContentData contentData;

        public Builder setType(OpenType type) {
            this.type = type;
            return this;
        }

        public Builder setContentData(ContentData contentData) {
            this.contentData = contentData;
            return this;
        }

        public Open build() {
            return new Open(Optional.fromNullable(type), Optional.fromNullable(contentData));
        }

    }

}
