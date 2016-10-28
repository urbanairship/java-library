/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class MPNSCycleTileData extends MPNSTileData
{
    private final Optional<String> smallBackgroundImage;
    private final Optional<ImmutableList<String>> images;

    private MPNSCycleTileData(Optional<String> id,
                              Optional<String> title,
                              Optional<Integer> count,
                              Optional<String> smallBackgroundImage,
                              Optional<ImmutableList<String>> images)
    {
        super(id, title, count);
        this.smallBackgroundImage = smallBackgroundImage;
        this.images = images;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String getTemplate() {
        return "CycleTile";
    }

    public Optional<String> getSmallBackgroundImage() {
        return smallBackgroundImage;
    }

    public Optional<ImmutableList<String>> getImages() {
        return images;
    }

    public int getImageCount() {
        return images.isPresent() ? images.get().size() : 0;
    }

    public String getImage(int i) {
        return images.isPresent() ? images.get().get(i) : null;
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

        MPNSCycleTileData that = (MPNSCycleTileData)o;
        if (smallBackgroundImage != null ? !smallBackgroundImage.equals(that.smallBackgroundImage) : that.smallBackgroundImage != null) {
            return false;
        }
        if (images != null ? !images.equals(that.images) : that.images != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (smallBackgroundImage != null ? smallBackgroundImage.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }


    public static class Builder {

        private String id;
        private String title;
        private Integer count;
        private String smallBackgroundImage;
        private ImmutableList.Builder<String> imagesBuilder = ImmutableList.builder();

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

        public Builder setSmallBackgroundImage(String value) {
            this.smallBackgroundImage = value;
            return this;
        }

        public Builder addImage(String value) {
            if (imagesBuilder == null) { imagesBuilder = ImmutableList.builder(); }
            this.imagesBuilder.add(value);
            return this;
        }

        public Builder addAllImages(Iterable<String> values) {
            if (imagesBuilder == null) { imagesBuilder = ImmutableList.builder(); }
            this.imagesBuilder.addAll(values);
            return this;
        }

        public Builder clearImages() {
            this.imagesBuilder = null;
            return this;
        }

        public MPNSCycleTileData build() {
            Validation.validateStringValue(title, "title");

            ImmutableList<String> images = null;
            if (imagesBuilder != null) {
                images = imagesBuilder.build();
                for (String image : images) {
                    Validation.validatePath(image, "cycle_image");
                }
            }

            return new MPNSCycleTileData(Optional.fromNullable(id),
                                     Optional.fromNullable(title),
                                     Optional.fromNullable(count),
                                     Optional.fromNullable(smallBackgroundImage),
                                     Optional.fromNullable(images));
        }
    }
}
