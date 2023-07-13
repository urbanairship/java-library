/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class IOSAlertData extends PushModelObject {

    private final Optional<String> body;
    private final Optional<String> actionLocKey;
    private final Optional<String> locKey;
    private final Optional<List<String>> locArgs;
    private final Optional<String> launchImage;
    private final Optional<String> summaryArg;
    private final Optional<Integer> summaryArgCount;
    private final Optional<String> title;
    private final Optional<List<String>> titleLocArgs;
    private final Optional<String> titleLocKey;
    private final Optional<List<String>> subtitleLocArgs;
    private final Optional<String> subtitleLocKey;

    private IOSAlertData(Optional<String> body,
                         Optional<String> actionLocKey,
                         Optional<String> locKey,
                         Optional<List<String>> locArgs,
                         Optional<String> launchImage,
                         Optional<String> summaryArg,
                         Optional<Integer> summaryArgCount,
                         Optional<String> title,
                         Optional<List<String>> titleLocArgs,
                         Optional<String> titleLocKey,
                         Optional<List<String>> subtitleLocArgs,
                         Optional<String> subtitleLocKey) {
        this.body = body;
        this.actionLocKey = actionLocKey;
        this.locKey = locKey;
        this.locArgs = locArgs;
        this.launchImage = launchImage;
        this.summaryArg = summaryArg;
        this.summaryArgCount = summaryArgCount;
        this.title = title;
        this.titleLocArgs = titleLocArgs;
        this.titleLocKey = titleLocKey;
        this.subtitleLocArgs = subtitleLocArgs;
        this.subtitleLocKey = subtitleLocKey;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isCompound() {
        return actionLocKey.isPresent() ||
                locKey.isPresent() ||
                locArgs.isPresent() ||
                launchImage.isPresent() ||
                summaryArg.isPresent() ||
                summaryArgCount.isPresent() ||
                title.isPresent() ||
                titleLocArgs.isPresent() ||
                titleLocKey.isPresent() ||
                subtitleLocArgs.isPresent() ||
                subtitleLocKey.isPresent();
    }

    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getActionLocKey() {
        return actionLocKey;
    }

    public Optional<String> getLocKey() {
        return locKey;
    }

    public Optional<List<String>> getLocArgs() {
        return locArgs;
    }

    public Optional<String> getLaunchImage() {
        return launchImage;
    }

    public Optional<String> getSummaryArg() {
        return summaryArg;
    }

    public Optional<Integer> getSummaryArgCount() {
        return summaryArgCount;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<List<String>> getTitleLocArgs() {
        return titleLocArgs;
    }

    public Optional<String> getTitleLocKey() {
        return titleLocKey;
    }
    public Optional<List<String>> getSubtitleLocArgs() {
        return subtitleLocArgs;
    }

    public Optional<String> getSubtitleLocKey() {
        return subtitleLocKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSAlertData that = (IOSAlertData) o;
        return Objects.equals(body, that.body) &&
                Objects.equals(actionLocKey, that.actionLocKey) &&
                Objects.equals(locKey, that.locKey) &&
                Objects.equals(locArgs, that.locArgs) &&
                Objects.equals(launchImage, that.launchImage) &&
                Objects.equals(summaryArg, that.summaryArg) &&
                Objects.equals(summaryArgCount, that.summaryArgCount) &&
                Objects.equals(title, that.title) &&
                Objects.equals(titleLocArgs, that.titleLocArgs) &&
                Objects.equals(titleLocKey, that.titleLocKey) &&
                Objects.equals(subtitleLocArgs, that.subtitleLocArgs) &&
                Objects.equals(subtitleLocKey, that.subtitleLocKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, actionLocKey, locKey, locArgs, launchImage, summaryArg, summaryArgCount, title, titleLocArgs, titleLocKey, subtitleLocArgs, subtitleLocKey);
    }

    @Override
    public String toString() {
        return "IOSAlertData{" +
                "body=" + body +
                ", actionLocKey=" + actionLocKey +
                ", locKey=" + locKey +
                ", locArgs=" + locArgs +
                ", launchImage=" + launchImage +
                ", summaryArg=" + summaryArg +
                ", summaryArgCount=" + summaryArgCount +
                ", title=" + title +
                ", titleLocArgs=" + titleLocArgs +
                ", titleLocKey=" + titleLocKey +
                ", subtitleLocArgs=" + subtitleLocArgs +
                ", subtitleLocKey=" + subtitleLocKey +
                '}';
    }

    public static class Builder {
        private String body = null;
        private String actionLocKey = null;
        private String locKey = null;
        private List<String> locArgs = null;
        private String launchImage = null;
        private String summaryArg = null;
        private Integer summaryArgCount = null;
        private String title = null;
        private List<String> titleLocArgs = null;
        private String titleLocKey = null;
        private List<String> subtitleLocArgs = null;
        private String subtitleLocKey = null;

        private Builder() { }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setActionLocKey(String value) {
            this.actionLocKey = value;
            return this;
        }

        public Builder setLocKey(String value) {
            this.locKey = value;
            return this;
        }

        public Builder setLocArgs(List<String> value) {
            this.locArgs = value;
            return this;
        }

        public Builder setLaunchImage(String value) {
            this.launchImage = value;
            return this;
        }

        public Builder setSummaryArg(String value) {
            this.summaryArg = value;
            return this;
        }

        public Builder setSummaryArgCount(Integer value) {
            this.summaryArgCount = value;
            return this;
        }

        public Builder setTitle(String value) {
            this.title = value;
            return this;
        }

        public Builder setTitleLocArgs(List<String> value) {
            this.titleLocArgs = value;
            return this;
        }

        public Builder setTitleLocKey(String value) {
            this.titleLocKey = value;
            return this;
        }
        public Builder setSubtitleLocArgs(List<String> value) {
            this.subtitleLocArgs = value;
            return this;
        }

        public Builder setSubtitleLocKey(String value) {
            this.subtitleLocKey = value;
            return this;
        }

        public IOSAlertData build() {
            return new IOSAlertData(Optional.ofNullable(body),
                    Optional.ofNullable(actionLocKey),
                    Optional.ofNullable(locKey),
                    Optional.ofNullable(locArgs),
                    Optional.ofNullable(launchImage),
                    Optional.ofNullable(summaryArg),
                    Optional.ofNullable(summaryArgCount),
                    Optional.ofNullable(title),
                    Optional.ofNullable(titleLocArgs),
                    Optional.ofNullable(titleLocKey),
                    Optional.ofNullable(subtitleLocArgs),
                    Optional.ofNullable(subtitleLocKey));
        }
    }
}
