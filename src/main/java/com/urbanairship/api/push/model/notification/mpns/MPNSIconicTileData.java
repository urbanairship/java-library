/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;

public class MPNSIconicTileData extends MPNSTileData
{
    private final Optional<String> iconImage;
    private final Optional<String> smallIconImage;
    private final Optional<String> backgroundColor;
    private final Optional<String> wideContent1;
    private final Optional<String> wideContent2;
    private final Optional<String> wideContent3;

    private MPNSIconicTileData(Optional<String> id,
                               Optional<String> title,
                               Optional<Integer> count,
                               Optional<String> iconImage,
                               Optional<String> smallIconImage,
                               Optional<String> backgroundColor,
                               Optional<String> wideContent1,
                               Optional<String> wideContent2,
                               Optional<String> wideContent3)
    {
        super(id, title, count);
        this.iconImage = iconImage;
        this.smallIconImage = smallIconImage;
        this.backgroundColor = backgroundColor;
        this.wideContent1 = wideContent1;
        this.wideContent2 = wideContent2;
        this.wideContent3 = wideContent3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String getTemplate() {
        return "IconicTile";
    }

    public Optional<String> getIconImage() {
        return this.iconImage;
    }

    public Optional<String> getSmallIconImage() {
        return this.smallIconImage;
    }

    public Optional<String> getBackgroundColor() {
        return this.backgroundColor;
    }

    public Optional<String> getWideContent1() {
        return this.wideContent1;
    }

    public Optional<String> getWideContent2() {
        return this.wideContent2;
    }

    public Optional<String> getWideContent3() {
        return this.wideContent3;
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

        MPNSIconicTileData that = (MPNSIconicTileData)o;
        if (iconImage != null ? !iconImage.equals(that.iconImage) : that.iconImage != null) {
            return false;
        }
        if (smallIconImage != null ? !smallIconImage.equals(that.smallIconImage) : that.smallIconImage != null) {
            return false;
        }
        if (backgroundColor != null ? !backgroundColor.equals(that.backgroundColor) : that.backgroundColor != null) {
            return false;
        }
        if (wideContent1 != null ? !wideContent1.equals(that.wideContent1) : that.wideContent1 != null) {
            return false;
        }
        if (wideContent2 != null ? !wideContent2.equals(that.wideContent2) : that.wideContent2 != null) {
            return false;
        }
        if (wideContent3 != null ? !wideContent3.equals(that.wideContent3) : that.wideContent3 != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (iconImage != null ? iconImage.hashCode() : 0);
        result = 31 * result + (smallIconImage != null ? smallIconImage.hashCode() : 0);
        result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
        result = 31 * result + (wideContent1 != null ? wideContent1.hashCode() : 0);
        result = 31 * result + (wideContent2 != null ? wideContent2.hashCode() : 0);
        result = 31 * result + (wideContent3 != null ? wideContent3.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private String id;
        private String title;
        private Integer count;
        private String iconImage;
        private String smallIconImage;
        private String backgroundColor;
        private String wideContent1;
        private String wideContent2;
        private String wideContent3;

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
            this.count = value;
            return this;
        }

        public Builder setIconImage(String value) {
            this.iconImage = value;
            return this;
        }

        public Builder setSmallIconImage(String value) {
            this.smallIconImage = value;
            return this;
        }

        public Builder setBackgroundColor(String value) {
            this.backgroundColor = value;
            return this;
        }

        public Builder setWideContent1(String value) {
            this.wideContent1 = value;
            return this;
        }

        public Builder setWideContent2(String value) {
            this.wideContent2 = value;
            return this;
        }

        public Builder setWideContent3(String value) {
            this.wideContent3 = value;
            return this;
        }

        public MPNSIconicTileData build() {
            checkArgument(title != null || iconImage != null || smallIconImage != null
                          || backgroundColor != null || wideContent1 != null
                          || wideContent2 != null || wideContent3 != null,
                          "tile must not be empty");

            Validation.validatePath(id, "id");
            Validation.validateStringValue(title, "title");
            Validation.validateUriValue(iconImage, "icon_image");
            Validation.validateUriValue(smallIconImage, "small_icon_image");
            Validation.validateColorValue(backgroundColor, "background_color");
            Validation.validateStringValue(wideContent1, "wide_content_1");
            Validation.validateStringValue(wideContent2, "wide_content_2");
            Validation.validateStringValue(wideContent3, "wide_content_3");

            return new MPNSIconicTileData(Optional.fromNullable(id),
                                      Optional.fromNullable(title),
                                      Optional.fromNullable(count),
                                      Optional.fromNullable(iconImage),
                                      Optional.fromNullable(smallIconImage),
                                      Optional.fromNullable(backgroundColor),
                                      Optional.fromNullable(wideContent1),
                                      Optional.fromNullable(wideContent2),
                                      Optional.fromNullable(wideContent3));
        }
    }
}
