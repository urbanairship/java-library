package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * ContentData
 *
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class ContentData extends PushModelObject {

    //\\//\\//\\//\\//\\//\\//\\//
    // contstants               //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // fields                   //
    //\\//\\//\\//\\//\\//\\//\\//

    private final Optional<String> content;
    private final Optional<Content> contentObject;

    //\\//\\//\\//\\//\\//\\//\\//
    // constructors             //
    //\\//\\//\\//\\//\\//\\//\\//

    private ContentData(Optional<String> content, Optional<Content> contentObject) {
        this.content = content;
        this.contentObject = contentObject;
    }

    //\\//\\//\\//\\//\\//\\//\\//
    // public methods           //
    //\\//\\//\\//\\//\\//\\//\\//

    public Optional<String> getContent() {
        return content;
    }

    public Optional<Content> getContentObject() {
        return contentObject;
    }

    /**
     * New ContentData builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }


    public static ContentData content(String content) {
        ContentData.Builder builder = ContentData.newBuilder();
        builder.setContent(content);
        return builder.build();
    }

    public static ContentData content(String body, String contentType, String contentEncoding) {
        ContentData.Builder builder = ContentData.newBuilder();
        builder.setContent(Content.getContent(body, contentType, contentEncoding));
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentData that = (ContentData) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (contentObject != null ? !contentObject.equals(that.contentObject) : that.contentObject != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (contentObject != null ? contentObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentData{");
        sb.append("content=").append(content);
        sb.append(", contentObject=").append(contentObject);
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
     * ContentData Builder
     */
    public static class Builder {

        private String content;
        private Content contentObject;

        private Builder() {
        }

        public Builder setContent(String content) {
            this.contentObject = null;
            this.content = content;
            return this;
        }

        public Builder setContent(Content contentObject) {
            this.contentObject = contentObject;
            this.content = null;
            return this;
        }

        public ContentData build() {
            return new ContentData(Optional.fromNullable(content), Optional.fromNullable(contentObject));
        }
    }

}