/*
 * Copyright 2013 Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.actions;

import com.urbanairship.api.push.model.PushModelObject;

/**
 * Content
 *
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class Content extends PushModelObject {

    //\\//\\//\\//\\//\\//\\//\\//
    // contstants               //
    //\\//\\//\\//\\//\\//\\//\\//

    private final String body;
    private final String contentType;
    private final String contentEncoding;

    //\\//\\//\\//\\//\\//\\//\\//
    // fields                   //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // constructors             //
    //\\//\\//\\//\\//\\//\\//\\//

    public Content(String body, String contentType, String contentEncoding) {
        this.body = body;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
    }

    //\\//\\//\\//\\//\\//\\//\\//
    // public methods           //
    //\\//\\//\\//\\//\\//\\//\\//

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public static Content getContent(String body, String contentType, String contentEncoding) {
        Content.Builder builder = Content.newBuilder();
        builder.setBody(body);
        builder.setContentType(contentType);
        builder.setContentEncoding(contentEncoding);
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        if (body != null ? !body.equals(content.body) : content.body != null) return false;
        if (contentEncoding != null ? !contentEncoding.equals(content.contentEncoding) : content.contentEncoding != null)
            return false;
        if (contentType != null ? !contentType.equals(content.contentType) : content.contentType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = body != null ? body.hashCode() : 0;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (contentEncoding != null ? contentEncoding.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Content{");
        sb.append("body='").append(body).append('\'');
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append(", contentEncoding='").append(contentEncoding).append('\'');
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
     * Content Builder
     */
    public static class Builder {
        private String body;
        private String contentType;
        private String contentEncoding;


        public Builder() { }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setContentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
            return this;
        }

        public Content build() {
            return new Content(body, contentType, contentEncoding);
        }

    }



}
