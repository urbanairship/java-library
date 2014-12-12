/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;

public class MPNSFlipTileData extends MPNSTileData
{
    private final Optional<String> backBackgroundImage;
    private final Optional<String> backContent;
    private final Optional<String> backgroundImage;
    private final Optional<String> backTitle;
    private final Optional<String> smallBackgroundImage;
    private final Optional<String> wideBackBackgroundImage;
    private final Optional<String> wideBackContent;
    private final Optional<String> wideBackgroundImage;

    private MPNSFlipTileData(Optional<String> id,
                             Optional<String> title,
                             Optional<Integer> count,
                             Optional<String> backBackgroundImage,
                             Optional<String> backContent,
                             Optional<String> backgroundImage,
                             Optional<String> backTitle,
                             Optional<String> smallBackgroundImage,
                             Optional<String> wideBackBackgroundImage,
                             Optional<String> wideBackContent,
                             Optional<String> wideBackgroundImage)
    {
        super(id, title, count);
        this.backBackgroundImage = backBackgroundImage;
        this.backContent = backContent;
        this.backgroundImage = backgroundImage;
        this.backTitle = backTitle;
        this.smallBackgroundImage = smallBackgroundImage;
        this.wideBackBackgroundImage = wideBackBackgroundImage;
        this.wideBackContent = wideBackContent;
        this.wideBackgroundImage = wideBackgroundImage;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String getTemplate() {
        return "FlipTile";
    }

    public Optional<String> getBackBackgroundImage() {
        return backBackgroundImage;
    }

    public Optional<String> getBackContent() {
        return backContent;
    }

    public Optional<String> getBackgroundImage() {
        return backgroundImage;
    }

    public Optional<String> getBackTitle() {
        return backTitle;
    }

    public Optional<String> getSmallBackgroundImage() {
        return smallBackgroundImage;
    }

    public Optional<String> getWideBackBackgroundImage() {
        return wideBackBackgroundImage;
    }

    public Optional<String> getWideBackContent() {
        return wideBackContent;
    }

    public Optional<String> getWideBackgroundImage() {
        return wideBackgroundImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MPNSFlipTileData that = (MPNSFlipTileData)o;
        if (backBackgroundImage != null ? !backBackgroundImage.equals(that.backBackgroundImage) : that.backBackgroundImage != null) {
            return false;
        }
        if (backContent != null ? !backContent.equals(that.backContent) : that.backContent != null) {
            return false;
        }
        if (backgroundImage != null ? !backgroundImage.equals(that.backgroundImage) : that.backgroundImage != null) {
            return false;
        }
        if (backTitle != null ? !backTitle.equals(that.backTitle) : that.backTitle != null) {
            return false;
        }
        if (smallBackgroundImage != null ? !smallBackgroundImage.equals(that.smallBackgroundImage) : that.smallBackgroundImage != null) {
            return false;
        }
        if (wideBackBackgroundImage != null ? !wideBackBackgroundImage.equals(that.wideBackBackgroundImage) : that.wideBackBackgroundImage != null) {
            return false;
        }
        if (wideBackContent != null ? !wideBackContent.equals(that.wideBackContent) : that.wideBackContent != null) {
            return false;
        }
        if (wideBackgroundImage != null ? !wideBackgroundImage.equals(that.wideBackgroundImage) : that.wideBackgroundImage != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (backBackgroundImage != null ? backBackgroundImage.hashCode() : 0);
        result = 31 * result + (backContent != null ? backContent.hashCode() : 0);
        result = 31 * result + (backgroundImage != null ? backgroundImage.hashCode() : 0);
        result = 31 * result + (backTitle != null ? backTitle.hashCode() : 0);
        result = 31 * result + (smallBackgroundImage != null ? smallBackgroundImage.hashCode() : 0);
        result = 31 * result + (wideBackBackgroundImage != null ? wideBackBackgroundImage.hashCode() : 0);
        result = 31 * result + (wideBackContent != null ? wideBackContent.hashCode() : 0);
        result = 31 * result + (wideBackgroundImage != null ? wideBackgroundImage.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private String id;
        private String title;
        private Integer count;
        private String backBackgroundImage;
        private String backContent;
        private String backgroundImage;
        private String backTitle;
        private String smallBackgroundImage;
        private String wideBackgroundImage;
        private String wideBackBackgroundImage;
        private String wideBackContent;

        private Builder() { }

        public Builder setId(String value) {
            this.id = value;
            return this;
        }

        public Builder setTitle(String value) {
            this.title = value;
            return this;
        }

        public Builder setCount(int value) {
            this.count = new Integer(value);
            return this;
        }

        public Builder setBackBackgroundImage(String value) {
            this.backBackgroundImage = value;
            return this;
        }

        public Builder setBackContent(String value) {
            this.backContent = value;
            return this;
        }

        public Builder setBackgroundImage(String value) {
            this.backgroundImage = value;
            return this;
        }

        public Builder setBackTitle(String value) {
            this.backTitle = value;
            return this;
        }

        public Builder setSmallBackgroundImage(String value) {
            this.smallBackgroundImage = value;
            return this;
        }

        public Builder setWideBackBackgroundImage(String value) {
            this.wideBackBackgroundImage = value;
            return this;
        }

        public Builder setWideBackContent(String value) {
            this.wideBackContent = value;
            return this;
        }

        public Builder setWideBackgroundImage(String value) {
            this.wideBackgroundImage = value;
            return this;
        }

        public MPNSFlipTileData build() {
            checkArgument(id != null || title != null || count != null || backBackgroundImage !=null
                          || backContent != null || backgroundImage != null || backTitle != null
                          || smallBackgroundImage != null || wideBackBackgroundImage != null || wideBackContent != null
                          || wideBackgroundImage != null,
                          "tile must not be empty");
            Validation.validateStringValue(title, "title");
            Validation.validateUriValue(backBackgroundImage, "back_background_image");
            Validation.validateStringValue(backContent, "back_content");
            Validation.validateUriValue(backgroundImage, "background_image");
            Validation.validateStringValue(backTitle, "back_title");
            Validation.validateUriValue(smallBackgroundImage, "small_background_image");
            Validation.validateUriValue(wideBackBackgroundImage, "wide_back_background_image");
            Validation.validateStringValue(wideBackContent, "wide_back_content");
            Validation.validateUriValue(wideBackgroundImage, "wide_background_image");

            return new MPNSFlipTileData(Optional.fromNullable(id),
                                    Optional.fromNullable(title),
                                    Optional.fromNullable(count),
                                    Optional.fromNullable(backBackgroundImage),
                                    Optional.fromNullable(backContent),
                                    Optional.fromNullable(backgroundImage),
                                    Optional.fromNullable(backTitle),
                                    Optional.fromNullable(smallBackgroundImage),
                                    Optional.fromNullable(wideBackBackgroundImage),
                                    Optional.fromNullable(wideBackContent),
                                    Optional.fromNullable(wideBackgroundImage));
        }
    }
}
